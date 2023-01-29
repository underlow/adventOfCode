package me.underlow.advent2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatchsticksTest {
    @Test
    fun testPart1() {
        val result = Matchsticks.part1(input.split("\n"))
        assertEquals(12, result)
    }

    @Test
    fun testPart2() {
        val result = Matchsticks.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    ""
    "abc"
    "aaa\"aaa"
    "\x27"
""".trimIndent()
