package me.underlow.advent2022.day22

import me.underlow.advent2022.Point

data class TeleportData(
    val id: Int,
    val xRange: IntRange,
    val yRange: IntRange,
    val op: (Point) -> Point,
    val rotation: List<MonkeyOp> /*only rotation*/
)


object Teleport {

    fun findTeleport(nextPoint: Point): TeleportData =
        teleportForPart2.find { nextPoint.x in it.xRange && nextPoint.y in it.yRange }
            ?: error("Bad teleport")



    /**
     * this is hardcoded data for task2. won't work on other cube shapes
     */
    val cubeSize = 50
    private val teleportForPart2 = listOf(
        // top side
        TeleportData(
            1,
            3 * cubeSize  until 4 * cubeSize,
            0..0,
            { Point(0, it.x - 2 * cubeSize) },
            listOf(CounterClockwise)
        ),
        TeleportData(
            2,
            4 * cubeSize - 1..4 * cubeSize - 1,
            0 until cubeSize,
            { Point(0, it.y + 2 * cubeSize) },
            emptyList()
        ),
        TeleportData(
            3,
            3 * cubeSize until 4 * cubeSize ,
            cubeSize - 1..cubeSize - 1,
            { Point(3 * cubeSize - 1, it.x - 2 * cubeSize) },
            listOf(Clockwise)
        ),
        // rear side
        TeleportData(
            4,
            2 * cubeSize  until 3 * cubeSize,
            0..0,
            { Point(cubeSize -  (it.x - 2 * cubeSize), cubeSize) },
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
