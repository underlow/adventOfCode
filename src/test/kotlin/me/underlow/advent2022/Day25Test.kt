package me.underlow.advent2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day25Test {
    @Test
    fun testPart1() {
        val result = FullOfHotAir.part1(input.split("\n"))
        Assertions.assertEquals("2=-1=0", result)
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

