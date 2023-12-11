package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day08Test {
    @Test
    fun testPart1() {
        val result = HauntedWasteland.part1(input.split("\n"))
        assertEquals(2, result)
    }

    @Test
    fun testPart12() {
        val result = HauntedWasteland.part1(input2.split("\n"))
        assertEquals(6, result)
    }

    @Test
    fun testPart2() {
        val result = HauntedWasteland.part2(input3.split("\n"))
        assertEquals(6, result)
    }
}

private val input = """
    RL

    AAA = (BBB, CCC)
    BBB = (DDD, EEE)
    CCC = (ZZZ, GGG)
    DDD = (DDD, DDD)
    EEE = (EEE, EEE)
    GGG = (GGG, GGG)
    ZZZ = (ZZZ, ZZZ)
""".trimIndent()

private val input2 = """
    LLR

    AAA = (BBB, BBB)
    BBB = (AAA, ZZZ)
    ZZZ = (ZZZ, ZZZ)
""".trimIndent()

private val input3 = """
    LR

    11A = (11B, XXX)
    11B = (XXX, 11Z)
    11Z = (11B, XXX)
    22A = (22B, XXX)
    22B = (22C, 22C)
    22C = (22Z, 22Z)
    22Z = (22B, 22B)
    XXX = (XXX, XXX)
""".trimIndent()
