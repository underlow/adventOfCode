package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CosmicExpansionTest {
    @Test
    fun testPart1() {
        val result = CosmicExpansion.part1(input.split("\n"))
        assertEquals(374, result)
    }

    @Test
    fun testPart2() {
        val result = CosmicExpansion.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    ...#......
    .......#..
    #.........
    ..........
    ......#...
    .#........
    .........#
    ..........
    .......#..
    #...#.....
""".trimIndent()
