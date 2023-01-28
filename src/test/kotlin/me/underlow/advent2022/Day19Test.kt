package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class Day19Test {
    @Disabled
    @Test
    fun testSolvePart1() {
        val result = NotEnoughMinerals.part1(input.split("\n"))
        assertEquals(33, result)
    }

    @Disabled
    @Test
    fun testSolvePart2() {
        val result = NotEnoughMinerals.part2(input.split("\n"))
        assertEquals(56 * 62, result)
    }
}

private val input = """
        Blueprint 1: Each ore robot costs 4 ore.  Each clay robot costs 2 ore.  Each obsidian robot costs 3 ore and 14 clay.  Each geode robot costs 2 ore and 7 obsidian.
        Blueprint 2:  Each ore robot costs 2 ore.  Each clay robot costs 3 ore.  Each obsidian robot costs 3 ore and 8 clay.  Each geode robot costs 3 ore and 12 obsidian.
    """.trimIndent()
