package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun testPart1() {
        val result = PipeMaze.part1(input.split("\n"))
        assertEquals(4, result)
    }

    @Test
    fun testPart12() {
        val result = PipeMaze.part1(input2.split("\n"))
        assertEquals(8, result)
    }

    @Test
    fun testPart2() {
        val result = PipeMaze.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    .....
    .S-7.
    .|.|.
    .L-J.
    .....
""".trimIndent()

private val input2 = """
    ..F7.
    .FJ|.
    SJ.L7
    |F--J
    LJ...
""".trimIndent()
