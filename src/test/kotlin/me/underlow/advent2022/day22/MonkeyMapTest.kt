package me.underlow.advent2022.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
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

    @Disabled
    @Test
    fun testSolvePart2() {
        val result = MonkeyMap.part2(input.split("\n"))
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
