package me.underlow.advent2022.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FullOfHotAirTest {
    @Test
    fun testPart1() {
        val result = FullOfHotAir.part1(input.split("\n"))
        assertEquals(0, result)
    }

    @Test
    fun testPart2() {
        val result = FullOfHotAir.part1(input.split("\n"))
        assertEquals(0, result)
    }

}

private val input = """
    1=-0-2
    12111
    2=0=
    21
    2=01
    111
    20012
    112
    1=-1=
    1-12
    12
    1=
    122
""".trimIndent()

