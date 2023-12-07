package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CamelCardsTest {
    @Test
    fun testPart1() {
        val result = CamelCards.part1(input.split("\n"))
        assertEquals(6440, result)
    }

    @Test
    fun testPart2() {
        val result = CamelCards.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    32T3K 765
    T55J5 684
    KK677 28
    KTJJT 220
    QQQJA 483
""".trimIndent()
