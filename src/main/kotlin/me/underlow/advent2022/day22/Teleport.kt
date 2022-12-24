package me.underlow.advent2022.day22

import me.underlow.advent2022.Point

data class TeleportData(
    val id: Int,
    val op: (Point) -> Point,
    val rotation: List<MonkeyOp> /*only rotation*/,
    val checkPoint: (Point) -> Boolean
)


object Teleport {

    fun findTeleport(nextPoint: Point): TeleportData =
        teleportForPart2.find { it.checkPoint(nextPoint) }
            ?: error("Bad teleport for $nextPoint")

    fun call(vararg func: (Point) -> Point): (Point) -> Point = {
        var p = it
        for (f in func) {
            p = f(p)
        }
        p
    }

    // coord - coordinates in plane filled with square tiles.
    // cube at (50, 50) should have coords (1,1)
    data class Cube(val id: String, val coord: Point, val size: Int = cubeSize) {
        // rotate clockwise mapping point to a new location
//        fun rotate(): (Point) -> Point {
//            return { Point(coord.x * size + it.y - coord.y * size, (coord.y + 1) * size - it.x + coord.x * size) }
//        }
        fun rotateCW(p: Point): Point {
            val deltaX = p.x - (coord.x * size)
            val deltaY = p.y - (coord.y * size)

            val nX = coord.x * size + deltaY
            val nY = (coord.y + 1) * size -1 - deltaX

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
                    p.y in ((coord.y + 1) * size until (coord.y + 1) * size)

    }

    private val cTop = Cube("top", Point(3, 0))
    private val cRear = Cube("right", Point(2, 0))
    private val cFront = Cube("front", Point(2, 1))
    private val cBottom = Cube("bottom", Point(1, 1))
    private val cBack = Cube("back", Point(0, 1))
    private val cLeft = Cube("left", Point(0, 2))

    /**
     * this is hardcoded data for task2. won't work on other cube shapes
     */
    private const val cubeSize = 50
    private val teleportForPart2 = listOf(
        // top side
        TeleportData(
            1,
            call(cTop::rotateCounterCW, cTop.shiftY(1), cTop.shiftX(-4)),
            listOf(CounterClockwise),
            cTop::left
        ),
        TeleportData(
            2,
            call(cTop.shiftY(2), cTop.shiftX(-4)),
            emptyList(),
            cTop::bottom
        ),
        TeleportData(
            3,
            call(cTop::rotateCounterCW, cTop.shiftY(1)),
            listOf(Clockwise),
            cTop::right
        ),
        // rear side
        TeleportData(
            4,
            call(cRear::rotate180, cRear.shiftX(-2)),
            listOf(CounterClockwise, CounterClockwise),
            cRear::left

        ),
        TeleportData(
            5,
            call(cRear::rotateCW, cRear.shiftX(-1)),
            listOf(Clockwise),
            cRear::top
        ),
        // bottom side
        TeleportData(
            6,
            call(cBottom::rotateCounterCW, cBottom.shiftY(-1)),
            listOf(CounterClockwise),
            cBottom::left
        ),
        TeleportData(
            7,
            call(cBottom::rotateCounterCW, cBottom.shiftY(+1)),
            listOf(CounterClockwise),
            cBottom::right
        ),
        // front side
        TeleportData(
            8,
            call(cFront::rotateCW, cFront.shiftX(1)),
            listOf(CounterClockwise, CounterClockwise),
            cFront::bottom
        ),
        TeleportData(
            9,
            call(cFront::rotate180, cFront.shiftX(-2), cFront.shiftY(1)),
            listOf(Clockwise),
            cFront::right
        ),
        // back side
        TeleportData(
            10,
            call(cBack::rotate180, cBack.shiftX(2), cBack.shiftY(-2)),
            listOf(CounterClockwise, CounterClockwise),
            cBack::left
        ),
        TeleportData(
            11,
            call(cBack::rotateCW, cBack.shiftX(3), cBack.shiftY(-2)),
            listOf(Clockwise),
            cBack::top
        ),

        // left side
        TeleportData(
            12,
            call(cLeft.shiftX(4), cLeft.shiftY(-2)),
            emptyList(),
            cLeft::top
        ),
        TeleportData(
            13,
            call(cLeft::rotate180, cLeft.shiftX(2), cLeft.shiftY(-1)),
            listOf(Clockwise, Clockwise),
            cLeft::right
        ),
        TeleportData(
            14,
            call(cLeft::rotateCW, cLeft.shiftX(1)),
            listOf(CounterClockwise),
            cLeft::bottom
        ),
    )
}
