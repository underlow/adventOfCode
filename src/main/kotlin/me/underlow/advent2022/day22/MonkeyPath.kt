package me.underlow.advent2022.day22

import me.underlow.advent2022.Point

sealed interface MonkeyOp
object Clockwise : MonkeyOp
object CounterClockwise : MonkeyOp
data class Move(val steps: Int) : MonkeyOp

class Direction(start: Int = 1) {
    private val directions = listOf(
        Point(-1, 0),  // go up
        Point(0, +1),  // go right
        Point(+1, 0),  // go down
        Point(0, -1),  // go left
    )

    private var currentDirection = 1

    fun rotateCounterClockwise() {
        currentDirection = (directions.size + currentDirection - 1) % (directions.size)
    }

    fun rotateClockwise() {
        currentDirection = (currentDirection + 1) % (directions.size)
    }

    val current
        get() = directions[currentDirection]

    val code
        get() = (directions.size + currentDirection - 1) % directions.size
}

class Monkey(private val field: MonkeyField, val isCube: Boolean = false) {
    private var position = field.findStart()
    private val direction = Direction()

    private fun applyOp(op: MonkeyOp) {
        when (op) {
            Clockwise -> direction.rotateClockwise()
            CounterClockwise -> direction.rotateCounterClockwise()
            is Move -> move(op)
        }
    }


    private fun move(move: Move) {
        repeat(move.steps) {
            position = if (isCube) {
                val next = nextPossibleStepOnCube(position, direction)
                if (next != null) {
                    next.second.forEach { applyOp(it) }
                    next.first
                } else position
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
    fun nextPossibleStep(point: Point, direction: Point): Point? {

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
    fun nextPossibleStepOnCube(point: Point, direction: Direction): Pair<Point, List<MonkeyOp>>? {


        var nextPoint = field.step(point, direction.current)

        if (field.get(nextPoint) == FieldCell.OuterWall) {
            val teleportData = Teleport.findTeleport(point)
            val retPoint = teleportData.op(point)

            require(field.get(retPoint) != FieldCell.OuterWall) {
                "We got outside of the field"
            }
            return retPoint to teleportData.rotation
        }

        require(field.get(nextPoint) != FieldCell.OuterWall) {
            "We got outside of the field"
        }

        if (field.get(nextPoint) == FieldCell.Wall)
            return null // cannot move
        // can move
        return nextPoint to emptyList()
    }

    data class Position(val x: Int, val y: Int, val facingCode: Int)

    private fun finalPosition(): Position {
        return Position(
            position.x,
            position.y,
            direction.code
        )
    }
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
