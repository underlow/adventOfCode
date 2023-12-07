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
        assertEquals(5905, result)
    }

    @Test
    fun testPart21() {
        val result = CamelCards.part2(input2.split("\n"))
        assertEquals(13, result)
    }

    @Test
    fun testPart22() {
        val result = CamelCards.part2(input3.split("\n"))
        assertEquals(6839, result)
    }

    @Test
    fun testPart23() {
        val result = CamelCards.part2(input4.split("\n"))
        assertEquals(1149, result)
    }

    @Test
    fun testPart24() {
        val result = CamelCards.part2(input5.split("\n"))
        assertEquals(1, result)
    }
}

private val input = """
    32T3K 765
    T55J5 684
    KK677 28
    KTJJT 220
    QQQJA 483
""".trimIndent()
private val input2 = """
    2A6Q2 1
    2J8TQ 2
    3J8TQ 3
""".trimIndent()
private val input5 = """
    AAAJJ 1
""".trimIndent()
private val input3 = """
    2345A 1
    Q2KJJ 13
    Q2Q2Q 19
    T3T3J 17
    T3Q33 11
    2345J 3
    J345A 2
    32T3K 5
    T55J5 29
    KK677 7
    KTJJT 34
    QQQJA 31
    JJJJJ 37
    JAAAA 43
    AAAAJ 59
    AAAAA 61
    2AAAA 23
    2JJJJ 53
    JJJJ2 41
""".trimIndent()
private val input4 = """
A2345 1
A2344 2
A234J 2
A2233 3
A2333 4
A233J 4
A23JJ 4
AA222 5
AA22J 5
A2222 6
A222J 6
A22JJ 6
A2JJJ 6
AAAAA 7
AAAAJ 7
AAAJJ 7
AAJJJ 7
AJJJJ 7
JJJJJ 7
""".trimIndent()
