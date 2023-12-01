package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun testPart1() {
        val result = Trebuchet.part1(input.split("\n"))
        assertEquals(142, result)
    }

    @Test
    fun testPart2() {
        val result = Trebuchet.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
""".trimIndent()
