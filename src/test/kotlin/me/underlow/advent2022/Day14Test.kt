package me.underlow.advent2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun solution1() {
        val res = RegolithReservoirInput.solution1(input.split("\n"))
        Assertions.assertEquals(24, res)
    }

    @Test
    fun solution2() {
        val res = RegolithReservoirInput.solution2(input.split("\n"))
        Assertions.assertEquals(93, res)
    }
}

private val input = """
    498,4 -> 498,6 -> 496,6
    503,4 -> 502,4 -> 502,9 -> 494,9
""".trimIndent()
