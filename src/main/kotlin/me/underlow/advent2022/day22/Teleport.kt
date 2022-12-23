package me.underlow.advent2022.day22

import me.underlow.advent2022.Point

data class TeleportData(
    val id: Int,
    val xRange: IntRange,
    val yRange: IntRange,
    val op: (Point) -> Point,
    val rotation: List<MonkeyOp> /*only rotation*/,
    val checkContaind: (Point) -> Boolean = { false }
)


object Teleport {

    fun findTeleport(nextPoint: Point): TeleportData =
        teleportForPart2.find { nextPoint.x in it.xRange && nextPoint.y in it.yRange }
            ?: error("Bad teleport")

    // coord - coordinates in plane filled with square tiles.
    // cube at (50, 50) should have coords (1,1)
    data class Cube(val id: String, val coord: Point, val size: Int = cubeSize) {
        // rotate clockwise mapping point to a new location
        fun rotate(): (Point) -> Point {
            return { Point(coord.x * size + it.y - coord.y * size, coord.y * size + it.x - coord.x * size) }
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
                    p.y in ((coord.y + 1) * size until (coord.y + 2) * size)

    }

    private val cTop = Cube("top", Point(3, 0))
    private val cRight = Cube("right", Point(2, 0))
    private val cFront = Cube("front", Point(2, 1))
    private val cBottom = Cube("bottom", Point(1, 1))
    private val cBack = Cube("back", Point(0, 1))
    private val cLeft = Cube("left", Point(0, 2))

    /**
     * this is hardcoded data for task2. won't work on other cube shapes
     */
    private const val cubeSize = 50
    private fun Int.toRange() = this..this
    private val teleportForPart2 = listOf(
        // top side
        TeleportData(
            1,
            3 * cubeSize until 4 * cubeSize,
            0.toRange(),
            { Point(0, it.x - 2 * cubeSize) },
            listOf(CounterClockwise)

        ),
        TeleportData(
            2,
            (4 * cubeSize - 1).toRange(),
            0 until cubeSize,
            { Point(0, it.y + 2 * cubeSize) },
            emptyList()
        ),
        TeleportData(
            3,
            3 * cubeSize until 4 * cubeSize,
            (cubeSize - 1).toRange(),
            { Point(3 * cubeSize - 1, it.x - 2 * cubeSize) },
            listOf(Clockwise)
        ),
        // rear side
        TeleportData(
            4,
            2 * cubeSize until 3 * cubeSize,
            0.toRange(),
            { Point(cubeSize - (it.x - 2 * cubeSize), cubeSize) },
            listOf(CounterClockwise, CounterClockwise)
        ),
        TeleportData(
            5,
            2 * cubeSize..2 * cubeSize,
            0 until cubeSize,
            { Point(it.y + cubeSize, cubeSize) },
            listOf(Clockwise)
        ),
        // bottom side
        TeleportData(
            6,
            cubeSize until 2 * cubeSize,
            cubeSize..cubeSize,
            { Point(2 * cubeSize, it.y - cubeSize) },
            listOf(CounterClockwise)
        ),
        TeleportData(
            7,
            cubeSize until 2 * cubeSize,
            2 * cubeSize - 1..2 * cubeSize - 1,
            { Point(cubeSize, it.x) },
            listOf(CounterClockwise)
        ),
        // front side
        TeleportData(
            8,
            2 * cubeSize until 3 * cubeSize,
            2 * cubeSize - 1..2 * cubeSize - 1,
            { Point(it.x - 2 * cubeSize, 3 * cubeSize - 1) },
            listOf(CounterClockwise, CounterClockwise)
        ),
        TeleportData(
            9,
            3 * cubeSize - 1..3 * cubeSize - 1,
            cubeSize until 2 * cubeSize,
            { Point(2 * cubeSize + it.y, cubeSize - 1) },
            listOf(Clockwise)
        ),
        // back side
        TeleportData(
            10,
            0 until cubeSize,
            cubeSize..cubeSize,
            { Point(it.x + 2 * cubeSize, 0) },
            listOf(CounterClockwise, CounterClockwise)
        ),
        TeleportData(
            11,
            0..0,
            cubeSize until 2 * cubeSize,
            { Point(2 * cubeSize + it.y, 0) },
            listOf(Clockwise)
        ),

        // left side
        TeleportData(
            12,
            0..0,
            2 * cubeSize until 3 * cubeSize,
            { Point(4 * cubeSize - 1, it.y - 2 * cubeSize) },
            emptyList()
        ),
        TeleportData(
            13,
            0 until cubeSize,
            3 * cubeSize - 1..3 * cubeSize - 1,
            { Point(3 * cubeSize - it.x, 2 * cubeSize - 1) },
            listOf(Clockwise, Clockwise)
        ),
        TeleportData(
            14,
            cubeSize - 1..cubeSize - 1,
            2 * cubeSize until 3 * cubeSize,
            { Point(it.y - cubeSize, 2 * cubeSize - 1) },
            listOf(CounterClockwise)
        ),


        )
}
