package me.underlow.advent2024

import ResonantCollinearity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {
    @Test
    fun testPart1() {
        val result = ResonantCollinearity.part1(input.split("\n"))
        assertEquals(14, result)
    }

    @Test
    fun testPart2() {
        val result = ResonantCollinearity.part2(input.split("\n"))
        assertEquals(34, result)
    }

    @Test
    fun testPart21() {
        val result = ResonantCollinearity.part2(input21.split("\n"))
        assertEquals(5, result)
    }

    @Test
    fun testPart22() {
        val result = ResonantCollinearity.part2(input22.split("\n"))
        assertEquals(6, result)
    }

    @Test
    fun testPart23() {
        val result = ResonantCollinearity.part2(input23.split("\n"))
        assertEquals(5, result)
    }

    @Test
    fun testPart24() {
        val result = ResonantCollinearity.part2(input24.split("\n"))
        assertEquals(2, result)
    }

    @Test
    fun testPart25() {
        val result = ResonantCollinearity.part2(input25.split("\n"))
        assertEquals(0, result)
    }

    @Test
    fun testPart26() {
        val result = ResonantCollinearity.part2(input26.split("\n"))
        assertEquals(5, result)
    }

    @Test
    fun testPart27() {
        val result = ResonantCollinearity.part2(input27.split("\n"))
        assertEquals(16, result)
    }
}

private val input = """
............
........0...
.....0......
.......0....
....0.......
......A.....
............
............
........A...
.........A..
............
............
""".trimIndent()

private val input21 = """
    A....
    .A...
    ..A..
    ...A.
    ....A
""".trimIndent()
private val input22 = """
    b.....a
    .......
    .......
    c.....c
    .......
    .......
    a.....b
""".trimIndent()
private val input23 = """
    .....
    .....
    ..A..
    ...A.
    .....
""".trimIndent()
private val input24 = """
    .....
    .A...
    .....
    ...A.
    .....
""".trimIndent()
private val input25 = """
    .....
    .....
    .....
    ...A.
    .....
""".trimIndent()
private val input26 = """
    .....
    .....
    .....
    ..AA.
    .....
""".trimIndent()
private val input27 = """
    .......
    .......
    a......
    aa.....
    .......
    .......
    .......
""".trimIndent()
