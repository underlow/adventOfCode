package me.underlow.advent2022.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RegolithReservoirKtTest {

    @Test
    fun solution1() {
        val res = solution1(input.split("\n"))
        assertEquals(24, res)
    }

    @Test
    fun solution2() {
        val res = solution2(input.split("\n"))
        assertEquals(93, res)
    }
}

private val input = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
""".trimIndent()
