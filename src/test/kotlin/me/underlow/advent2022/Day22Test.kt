package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MonkeyMapTest {
    @Test
    fun testPart1() {
        val result = MonkeyMap.part1(input.split("\n"))
        assertEquals(6032, result)
    }

    @Test
    fun testPart12() {
        val result = MonkeyMap.part1(input2.split("\n"))
        assertEquals(3048, result)
    }

    @Test
    fun testPart13() {
        val result = MonkeyMap.part1(input3.split("\n"))
        assertEquals(12049, result)
    }

//    @Disabled
    @Test
    fun testSolvePart2() {
    val result = MonkeyMap.part2(input.split("\n"), MonkeyMap.TestTeleport())
        assertEquals(5031, result)
    }
}

private val input = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5    
""".trimIndent()
private val input2 = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R2L10    
""".trimIndent()

private val input3 = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R2L10LL1L4R1L5L2R10
""".trimIndent()


class TeleportDataTest {

    @Test
    fun testCubeTransform() {
        val points = listOf(
            Point(102, 1) to Point(101, 47),
            Point(102, 47) to Point(147, 47),
            Point(100, 0) to Point(100, 49),
//            Point(101, 0) to Point(48, 50),
        )
        val cRear = MonkeyMap.Teleport.Cube("right", Point(2, 0), 50)
        // hardcode for speed
        points.forEach { p ->
            assertEquals(p.second, cRear.rotateCW(p.first))
        }


        points.forEach { p ->
            var rotated = p.first
            repeat(4) { rotated = cRear.rotateCW(rotated) }
            assertEquals(p.first, rotated)
        }


    }

    @Test
    fun testCubeTransformOnTestData() {
        val points = listOf(
            Point(4, 4) to Point(4, 7),
            Point(4, 7) to Point(7, 7),
            Point(7, 7) to Point(7, 4),
            Point(7, 4) to Point(4, 4),

            Point(5, 5) to Point(5, 6),
            Point(5, 6) to Point(6, 6),
            Point(6, 6) to Point(6, 5),
            Point(6, 5) to Point(5, 5),

            Point(5, 4) to Point(4, 6),
            Point(4, 6) to Point(6, 7),

            Point(4, 7) to Point(7, 7),
        )
        val cRear = MonkeyMap.Teleport.Cube("front", Point(1, 1), 4)
        // hardcode for speed
        points.forEach { p ->
            assertEquals(p.second, cRear.rotateCW(p.first), "Fail ${p.first} -> ${p.second}")
        }


        points.forEach { p ->
            var rotated = p.first
            repeat(4) { rotated = cRear.rotateCW(rotated) }
            assertEquals(p.first, rotated)
        }
    }

    @Test
    fun testTeleportOnTestRunData() {
        val pointsMove = listOf(
            Point(5, 11) to Point(7, 14),
        )
        val pointsRotate = listOf(
            Point(5, 11) to Point(7, 10),
        )

        val ctBottom = MonkeyMap.Teleport.Cube("bottom", Point(1, 2), 4)

        pointsRotate.forEach { p ->
            assertEquals(p.second, ctBottom.rotateCW(p.first), "Fail ${p.first} -> ${p.second}")
        }

        val teleport = MonkeyMap.TestTeleport()

        pointsMove.forEach { (from, to) ->
            val tp = teleport.findTeleport(from, MonkeyMap.Direction.Name.Right)
            assertEquals(to, tp.op(from), "Fail $from -> $to")
        }
    }

}
