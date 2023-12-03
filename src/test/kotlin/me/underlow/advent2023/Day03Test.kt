package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class GearRatiosTest {
    @Test
    fun testPart1() {
        val result = GearRatios.part1(input.split("\n"))
        assertEquals(4361, result)
    }

    @Test
    fun testPart11() {
        val result = GearRatios.part1(input11.split("\n"))
        assertEquals(0, result)
    }

    @Test
    fun testPart12() {
        val result = GearRatios.part1(input12.split("\n"))
        assertEquals(111, result)
    }

    @Test
    fun testPart13() {
        val result = GearRatios.part1(input13.split("\n"))
        assertEquals(0, result)
    }

    @Test
    fun testPart14() {
        val result = GearRatios.part1(input14.split("\n"))
        assertEquals(413, result)
    }

    @Test
    fun testPart2() {
        val result = GearRatios.part2(input.split("\n"))
        assertEquals(467835, result)
    }
}

private val input = """
    467..114..
    ...*......
    ..35..633.
    ......#...
    617*......
    .....+.58.
    ..592.....
    ......755.
    ...${'$'}.*....
    .664.598..
""".trimIndent()

private val input11 = """
    ..........
    ...111....
    ..........
""".trimIndent()

private val input12 = """
    ..........
    ...111....
    .aa.......
""".trimIndent()
private val input13 = """
    .aaaaaaa..
    .a.....a..
    .a.111.a..
    .a.....a..
    .aaaaaaa..
""".trimIndent()
private val input14 = """
12.......*..
+.........34
.......-12..
..78........
..*....60...
78..........
.......23...
....90*12...
............
2.2......12.
.*.........*
1.1.......56
""".trimIndent()
