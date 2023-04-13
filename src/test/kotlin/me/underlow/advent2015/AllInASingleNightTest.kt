package me.underlow.advent2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AllInASingleNightTest {
    @Test
    fun testPart1() {
        val result = AllInASingleNight.part1(input.split("\n"))
        assertEquals(605, result)
    }

    @Test
    fun testPart2() {
        val result = AllInASingleNight.part2(input.split("\n"))
        assertEquals(982, result)
    }
}

private val input = """
    London to Dublin = 464
    London to Belfast = 518
    Dublin to Belfast = 141
""".trimIndent()
