package me.underlow.advent2022.day24

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BlizzardBasinTest {
    @Test
    fun testPart1() {
        val result = BlizzardBasin.part1(input.split("\n"))
        assertEquals(18, result)
    }

    @Test
    fun testPart2() {
        val result = BlizzardBasin.part2(input.split("\n"))
        assertEquals(54, result)
    }
}

private val input = """
    #.######
    #>>.<^<#
    #.<..<<#
    #>v.><>#
    #<^v^^>#
    ######.#
""".trimIndent()
