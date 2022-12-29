package me.underlow.advent2015.day04

import me.underlow.advent2015.day04.TheIdealStockingStuffer.part1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TheIdealStockingStufferTest {
    @Test
    fun testPart1() {
        val result = part1(input1)
        assertEquals(609043, result)
    }

    fun testPart11() {
        val result = part1(input1)
        assertEquals(1048970, result)
    }

    @Test
    fun testPart2() {
        val result = part1("")
        assertEquals(0, result)
    }
}


private val input1 = "abcdef"
private val input2 = "pqrstuv"
