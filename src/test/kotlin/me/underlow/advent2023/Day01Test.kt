package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun testPart1() {
        val result = Trebuchet.part1(input1.split("\n"))
        assertEquals(142, result)
    }

    @Test
    fun testPart2() {
        val result = Trebuchet.part2(input2.split("\n"))
        assertEquals(281, result)
    }
}

private val input1 = """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
""".trimIndent()


private val input2 = """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
""".trimIndent()
