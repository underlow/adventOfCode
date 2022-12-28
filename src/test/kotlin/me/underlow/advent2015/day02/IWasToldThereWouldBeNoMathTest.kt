package me.underlow.advent2015.day02

import me.underlow.advent2015.day03.PerfectlySphericalHousesInAVacuum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IWasToldThereWouldBeNoMathTest {
    @Test
    fun testPart1() {
        val result = PerfectlySphericalHousesInAVacuum.part1(input)
        assertEquals(2, result)
    }

    @Test
    fun testPart2() {
        val result = PerfectlySphericalHousesInAVacuum.part2(input)
        assertEquals(11, result)
    }
}

private val input = """
    ^v^v^v^v^v
""".trimIndent()

