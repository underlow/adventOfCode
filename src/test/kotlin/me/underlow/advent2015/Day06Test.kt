package me.underlow.advent2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {
    @Test
    fun testPart1() {
        val result = ProbablyAFireHazard.part1(input.split("\n"))
        assertEquals(998996, result)
    }
}
private val input = """
    turn on 0,0 through 999,999
    toggle 0,0 through 999,0
    turn off 499,499 through 500,500
""".trimIndent()
