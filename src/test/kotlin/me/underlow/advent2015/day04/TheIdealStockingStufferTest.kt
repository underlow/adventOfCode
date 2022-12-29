package me.underlow.advent2015.day04

import me.underlow.advent2015.day04.TheIdealStockingStuffer.part1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TheIdealStockingStufferTest {
    @Test
    fun testPart1() {
        val result = part1(input1, "00000")
        assertEquals(609043, result)
    }
    @Test
    fun testPart11() {
        val result = part1(input2, "00000")
        assertEquals(1048970, result)
    }
}


private val input1 = "abcdef"
private val input2 = "pqrstuv"
