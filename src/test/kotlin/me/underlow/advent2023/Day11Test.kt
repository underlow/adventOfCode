package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CosmicExpansionTest {
    @Test
    fun testPart1() {
        val result = CosmicExpansion.part1(input.split("\n"), 2)
        assertEquals(374, result)
    }

    @Test
    fun testPart2() {
        val result = CosmicExpansion.part1(input.split("\n"), 10)
        assertEquals(1030, result)
    }

    @Test
    fun testPart22() {
        val result = CosmicExpansion.part1(input.split("\n"), 100)
        assertEquals(8410, result)
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
