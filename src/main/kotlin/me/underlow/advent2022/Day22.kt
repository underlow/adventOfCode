package me.underlow.advent2022

// https://adventofcode.com/2022/day/22

object MonkeyMap {

    fun part1(list: List<String>): Int {
        val input = list
            .subList(0, list.size - 1)
            .filter { it.isNotEmpty() }
            .map { it.toCharArray().toTypedArray() }.toTypedArray()

        val field = MonkeyField(input)
        val path = MonkeyPath(list.last().trim())
        val monkey = Monkey(field, isCube = false, Task2Teleport())
        val (x, y, facingCode) = monkey.followPath(path)
        return (x + 1) * 1000 + (y + 1) * 4 + facingCode
    }

    fun part2(list: List<String>, task2Teleport: Teleport): Int {
        val input = list
            .subList(0, list.size - 1)
            .filter { it.isNotEmpty() }
            .map { it.toCharArray().toTypedArray() }.toTypedArray()

        val field = MonkeyField(input)
        val path = MonkeyPath(list.last().trim())
        val monkey = Monkey(field, isCube = true, task2Teleport)
        val (x, y, facingCode) = monkey.followPath(path)
        return (x + 1) * 1000 + (y + 1) * 4 + facingCode
    }


    enum class FieldCell(val c: Char) {
        OuterWall(' '), Wall('#'), Empty('.');

        companion object {
            fun fromString(c: Char) = values().find { it.c == c } ?: error("Incorrect input")
        }

    }

    sealed interface MonkeyOp
    object Clockwise : MonkeyOp
    object CounterClockwise : MonkeyOp
    data class Move(val steps: Int) : MonkeyOp

    class Direction(start: Int = 1) {
        enum class Name { Up, Right, Down, Left }

        private val directions = listOf(
            Point(-1, 0),  // go up
            Point(0, +1),  // go right
            Point(+1, 0),  // go down
            Point(0, -1),  // go left
        )
        private val names = listOf("up", "right", "down", "left")
        private var currentDirection = start

        fun rotateCounterClockwise() {
            currentDirection = (directions.size + currentDirection - 1) % (directions.size)
        }

        fun rotateClockwise() {
            currentDirection = (currentDirection + 1) % (directions.size)
        }

        val name
            get() = Name.values()[currentDirection]

        val current
            get() = directions[currentDirection]

        val code
            get() = (directions.size + currentDirection - 1) % directions.size

        fun clone() = Direction(currentDirection)

        override fun toString(): String = "Direction[${names[currentDirection]}]"
    }

    class Monkey(private val field: MonkeyField, val isCube: Boolean = false, val teleport: Teleport) {
        private var position = field.findStart()
        private val direction = Direction()

        private fun applyOp(op: MonkeyOp) {
            when (op) {
                Clockwise -> direction.rotateClockwise()
                CounterClockwise -> direction.rotateCounterClockwise()
                is Move -> move(op)
                else -> error("Something wrong")
            }
        }


        private fun move(move: Move) {
            repeat(move.steps) {
                position = if (isCube) {
                    nextPossibleStepOnCube(position, direction) ?: position
                } else
                    nextPossibleStep(position, direction.current) ?: position
            }
        }

        fun followPath(path: MonkeyPath): Position {
            var step = path.nextMove()

            while (step != null) {
                applyOp(step)
                step = path.nextMove()
            }
            return finalPosition()
        }


        // find next point (either requested step or wrap around tha map) or null if move is impossble
        private fun nextPossibleStep(point: Point, direction: Point): Point? {

            var nextPoint = field.step(point, direction)

            while (field.get(nextPoint) == FieldCell.OuterWall)
                nextPoint = field.step(nextPoint, direction)

            require(field.get(nextPoint) != FieldCell.OuterWall) {
                "We got outside of the field"
            }

            if (field.get(nextPoint) == FieldCell.Wall)
                return null // cannot move
            // can move
            return nextPoint
        }

        // part 2
        private fun nextPossibleStepOnCube(point: Point, direction: Direction): Point? {

            var nextPoint = field.step2(point, direction.current)

            if (field.get(nextPoint) == FieldCell.OuterWall) {

                val teleportData = teleport.findTeleport(point, direction.name)
                nextPoint = teleportData.op(point)

                require(field.get(nextPoint) == FieldCell.OuterWall) {
                    "We have not got outside of the field"
                }

                val newDirection = this.direction.clone() //todo: apply ops

                teleportData.rotation.forEach {
                    when (it) {
                        Clockwise -> newDirection.rotateClockwise()
                        CounterClockwise -> newDirection.rotateCounterClockwise()
                        else -> error("Not a rotation")
                    }
                }

                while (field[nextPoint] == FieldCell.OuterWall) {
                    nextPoint = field.step2(nextPoint, newDirection.current)
                }

                require(field[nextPoint] != FieldCell.OuterWall) {
                    "We got outside of the field"
                }

                if (field[nextPoint] == FieldCell.Wall)
                    return null // cannot move

                // can move
                teleportData.rotation.forEach { r -> applyOp(r) }
                return nextPoint
            }

            require(field.get(nextPoint) != FieldCell.OuterWall) {
                "We got outside of the field"
            }

            if (field.get(nextPoint) == FieldCell.Wall)
                return null // cannot move
            // can move
            return nextPoint
        }

        data class Position(val x: Int, val y: Int, val facingCode: Int)

        private fun finalPosition(): Position {
            return Position(
                position.x,
                position.y,
                direction.code
            )
        }

        override fun toString(): String {
            return "Monkey($position, $direction)"
        }
    }


    data class TeleportData(
        val id: Int,
        val op: (Point) -> Point,
        val rotation: List<MonkeyOp> /*only rotation*/,
        val checkPoint: (Point) -> Boolean,
        val direction: Direction.Name
    )


    interface Teleport {
        val teleportData: List<TeleportData>
        val cubeSize: Int

        fun findTeleport(nextPoint: Point, name: Direction.Name): TeleportData =
            teleportData.find { it.checkPoint(nextPoint) && it.direction == name }
                ?: error("Bad teleport for $nextPoint")


        // coord - coordinates in plane filled with square tiles.
        // cube at (50, 50) should have coords (1,1)
        data class Cube(val id: String, val coord: Point, val size: Int) {
            // rotate clockwise mapping point to a new location
//        fun rotate(): (Point) -> Point {
//            return { Point(coord.x * size + it.y - coord.y * size, (coord.y + 1) * size - it.x + coord.x * size) }
//        }
            fun rotateCW(p: Point): Point {
                val deltaX = p.x - (coord.x * size)
                val deltaY = p.y - (coord.y * size)

                val nX = coord.x * size + deltaY
                val nY = (coord.y + 1) * size - 1 - deltaX

                return Point(nX, nY)
//            return Point(coord.x * size + p.y - coord.y * size, (coord.y + 1) * size - p.x + coord.x * size)
            }

            fun rotateCounterCW(p: Point): Point {
                return rotateCW(rotateCW(rotateCW(p)))
            }

            fun rotate180(p: Point): Point {
                return rotateCW(rotateCW(p))
            }


            // moves to x sizes of cube left or right
            fun shiftY(steps: Int): (Point) -> Point = { it.copy(y = it.y + size * steps) }

            // moves to x sizes of cube up or down
            fun shiftX(steps: Int): (Point) -> Point = { it.copy(x = it.x + size * steps) }

            //point is on xxx side
            fun left(p: Point): Boolean =
                p.x in (coord.x * size until (coord.x + 1) * size) &&
                        p.y == coord.y * size

            fun right(p: Point): Boolean =
                p.x in (coord.x * size until (coord.x + 1) * size) &&
                        p.y == (coord.y + 1) * size - 1

            fun top(p: Point): Boolean =
                p.x == coord.x * size &&
                        p.y in (coord.y * size until (coord.y + 1) * size)

            fun bottom(p: Point): Boolean =
                p.x == (coord.x + 1) * size - 1 &&
                        p.y in (coord.y * size until (coord.y + 1) * size)

        }
    }

    class TestTeleport : Teleport {
        override val cubeSize = 4

        /**
         * hardcoded for test
         */
        private val ctTop = Teleport.Cube("top", Point(1, 0), cubeSize)
        private val ctRear = Teleport.Cube("rear", Point(0, 2), cubeSize)
        private val ctFront = Teleport.Cube("front", Point(1, 1), cubeSize)
        private val ctBottom = Teleport.Cube("bottom", Point(1, 2), cubeSize)
        private val ctBack = Teleport.Cube("back", Point(2, 3), cubeSize)
        private val ctLeft = Teleport.Cube("left", Point(2, 2), cubeSize)


        override val teleportData = listOf(
            TeleportData(
                1,
                call(ctRear::rotate180, ctRear.shiftX(-2)),
                listOf(CounterClockwise, CounterClockwise),
                ctRear::top,
                Direction.Name.Up
            ),
            TeleportData(
                2,
                call(ctRear::rotateCounterCW, ctRear.shiftX(-1)),
                listOf(CounterClockwise),
                ctRear::left,
                Direction.Name.Left
            ),
            TeleportData(
                3,
                call(ctRear::rotate180, ctRear.shiftX(2), ctRear.shiftY(2)),
                listOf(CounterClockwise, CounterClockwise),
                ctRear::right,
                Direction.Name.Right
            ),
            TeleportData(
                4,
                call(ctBottom::rotateCW, ctBottom.shiftY(1)),
                listOf(Clockwise),
                ctBottom::right,
                Direction.Name.Right
            ),
            TeleportData(
                5,
                call(ctBack::rotateCounterCW, ctBack.shiftX(-1)),
                listOf(CounterClockwise),
                ctBack::top,
                Direction.Name.Up
            ),
            TeleportData(
                6,
                call(ctBack::rotate180, ctBack.shiftX(-2)),
                listOf(CounterClockwise, CounterClockwise),
                ctBack::right,
                Direction.Name.Right
            ),
            TeleportData(
                7,
                call(ctBack::rotateCounterCW, ctBack.shiftX(-4), ctBack.shiftY(-1)),
                listOf(CounterClockwise),
                ctBack::bottom,
                Direction.Name.Down
            ),
            TeleportData(
                8,
                call(ctLeft::rotate180, ctLeft.shiftY(-2)),
                listOf(CounterClockwise, CounterClockwise),
                ctLeft::bottom,
                Direction.Name.Down
            ),
            TeleportData(
                9,
                call(ctLeft::rotateCW, ctLeft.shiftY(-1)),
                listOf(Clockwise),
                ctLeft::left,
                Direction.Name.Left
            ),
            TeleportData(
                10,
                call(ctFront::rotateCounterCW, ctBack.shiftX(1)),
                listOf(CounterClockwise),
                ctBack::bottom,
                Direction.Name.Down
            ),
            TeleportData(
                11,
                call(ctFront::rotateCW, ctBack.shiftX(-1)),
                listOf(Clockwise),
                ctFront::top,
                Direction.Name.Up
            ),
            TeleportData(
                12,
                call(ctTop::rotate180, ctTop.shiftX(+2), ctTop.shiftY(+2)),
                listOf(Clockwise, Clockwise),
                ctBack::bottom,
                Direction.Name.Down
            ),
            TeleportData(
                13,
                call(ctTop::rotateCW, ctTop.shiftX(+2), ctTop.shiftY(+4)),
                listOf(Clockwise),
                ctBack::left,
                Direction.Name.Left
            ),
            TeleportData(
                14,
                call(ctTop::rotate180, ctTop.shiftX(-2), ctTop.shiftY(+3)),
                listOf(Clockwise, Clockwise),
                ctBack::top,
                Direction.Name.Up
            ),
        )

    }

    private fun call(vararg func: (Point) -> Point): (Point) -> Point = {
        var p = it
        for (f in func) {
            p = f(p)
        }
        p
    }


    class Task2Teleport : Teleport {

        override val cubeSize = 50

        private val cTop = Teleport.Cube("top", Point(3, 0), cubeSize)
        private val cRear = Teleport.Cube("rear", Point(2, 0), cubeSize)
        private val cFront = Teleport.Cube("front", Point(2, 1), cubeSize)
        private val cBottom = Teleport.Cube("bottom", Point(1, 1), cubeSize)
        private val cBack = Teleport.Cube("back", Point(0, 1), cubeSize)
        private val cLeft = Teleport.Cube("left", Point(0, 2), cubeSize)

        /**
         * this is hardcoded data for task2. won't work on other cube shapes
         */
//    private const val cubeSize = 50
        override val teleportData = listOf(
            // top side
            TeleportData(
                1,
                call(cTop::rotateCounterCW, cTop.shiftY(1), cTop.shiftX(-4)),
                listOf(CounterClockwise),
                cTop::left,
                Direction.Name.Left
            ),
            TeleportData(
                2,
                call(cTop.shiftY(2), cTop.shiftX(-4)),
                emptyList(),
                cTop::bottom,
                Direction.Name.Down
            ),
            TeleportData(
                3,
                call(cTop::rotateCounterCW, cTop.shiftY(1)),
                listOf(CounterClockwise),
                cTop::right,
                Direction.Name.Right
            ),
            // rear side
            TeleportData(
                4,
                call(cRear::rotate180, cRear.shiftX(-2)),
                listOf(CounterClockwise, CounterClockwise),
                cRear::left,
                Direction.Name.Left
            ),
            TeleportData(
                5,
                call(cRear::rotateCW, cRear.shiftX(-1)),
                listOf(Clockwise),
                cRear::top,
                Direction.Name.Up
            ),
            // bottom side
            TeleportData(
                6,
                call(cBottom::rotateCounterCW, cBottom.shiftY(-1)),
                listOf(CounterClockwise),
                cBottom::left,
                Direction.Name.Left
            ),
            TeleportData(
                7,
                call(cBottom::rotateCounterCW, cBottom.shiftY(+1)),
                listOf(CounterClockwise),
                cBottom::right,
                Direction.Name.Right
            ),
            // front side
            TeleportData(
                8,
                call(cFront::rotateCW, cFront.shiftX(1)),
                listOf(Clockwise),
                cFront::bottom,
                Direction.Name.Down
            ),
            TeleportData(
                9,
                call(cFront::rotate180, cFront.shiftX(-2), cFront.shiftY(2)),
                listOf(Clockwise, Clockwise),
                cFront::right,
                Direction.Name.Right
            ),
            // back side
            TeleportData(
                10,
                call(cBack::rotate180, cBack.shiftX(2), cBack.shiftY(-2)),
                listOf(CounterClockwise, CounterClockwise),
                cBack::left,
                Direction.Name.Left
            ),
            TeleportData(
                11,
                call(cBack::rotateCW, cBack.shiftX(3), cBack.shiftY(-2)),
                listOf(Clockwise),
                cBack::top,
                Direction.Name.Up
            ),

            // left side
            TeleportData(
                12,
                call(cLeft.shiftX(4), cLeft.shiftY(-2)),
                emptyList(),
                cLeft::top,
                Direction.Name.Up
            ),
            TeleportData(
                13,
                call(cLeft::rotate180, cLeft.shiftX(2)),
                listOf(Clockwise, Clockwise),
                cLeft::right,
                Direction.Name.Right
            ),
            TeleportData(
                14,
                call(cLeft::rotateCW, cLeft.shiftX(1)),
                listOf(Clockwise),
                cLeft::bottom,
                Direction.Name.Down
            ),
        )
    }


    class MonkeyPath(input: String) {
        private val ops = mutableListOf<MonkeyOp>()
        private var idx: Int = 0

        init {
            var i = 0
            while (i < input.length) {
                if (input[i] == 'R') {
                    ops.add(Clockwise)
                    i++
                    continue
                }
                if (input[i] == 'L') {
                    ops.add(CounterClockwise)
                    i++
                    continue
                }
                // assume input always correct
                var j = i
                while (j < input.length && input[j].isDigit()) j++
                ops.add(Move(input.substring(i, j).toInt()))
                i = j
            }
        }

        fun nextMove() = if (idx < ops.size) ops[idx++] else null
    }


    class MonkeyField(input: Array<Array<Char>>) {
        private val map: Array<Array<FieldCell>>
        private val sizeX: Int
        private val sizeY: Int

        init {
            sizeX = input.size
            sizeY = input.maxOf { it.size }
            map = Array(sizeX) { Array(sizeY) { FieldCell.OuterWall } }

            for (x in 0 until sizeX)
                for (y in 0 until sizeY) {
                    val c = if (y < input[x].size) input[x][y] else ' '
                    map[x][y] = FieldCell.fromString(c)
                }
        }

        operator fun get(x: Int, y: Int) = map[x][y]
        operator fun get(p: Point): FieldCell {
            if (p.x !in 0 until sizeX || p.y !in 0 until sizeY)
                return FieldCell.OuterWall
            return get(p.x, p.y)
        }

        fun findStart(): Point {
            for (y in 0 until sizeY)
                if (get(0, y) == FieldCell.Empty) return Point(0, y)

            error("Cannot find start")
        }

        fun step(p: Point, d: Point) =
            Point((sizeX + p.x + d.x) % sizeX, (sizeY + p.y + d.y) % sizeY)

        fun step2(p: Point, d: Point) =
            Point(p.x + d.x, p.y + d.y)

    }
}

fun main() {
    val input = readInput("${pathPrefix}/day22.txt")
    val res1 = MonkeyMap.part1(input)
    val res2 = MonkeyMap.part2(input, MonkeyMap.Task2Teleport())

    checkResult(res1, 65368)
    checkResult(res2, 156166)

    println(res1)
    println(res2)

}


