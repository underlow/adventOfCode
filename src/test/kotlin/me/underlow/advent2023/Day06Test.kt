package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {
    @Test
    fun testPart1() {
        val result = WaitForIt.part1(input.split("\n"))
        assertEquals(288, result)
    }

    @Test
    fun testPart2() {
        val result = WaitForIt.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    Time:      7  15   30
    Distance:  9  40  200
""".trimIndent()
