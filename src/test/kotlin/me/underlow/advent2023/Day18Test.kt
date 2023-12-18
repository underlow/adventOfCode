package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LavaductLagoonTest {
    @Test
    fun testPart1() {
        val result = LavaductLagoon.part1(input.split("\n"))
        assertEquals(62, result)
    }

    @Test
    fun testPart2() {
        val result = LavaductLagoon.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    R 6 (#70c710)
    D 5 (#0dc571)
    L 2 (#5713f0)
    D 2 (#d2c081)
    R 2 (#59c680)
    D 2 (#411b91)
    L 5 (#8ceee2)
    U 2 (#caa173)
    L 1 (#1b58a2)
    U 2 (#caa171)
    R 2 (#7807d2)
    U 3 (#a77fa3)
    L 2 (#015232)
    U 2 (#7a21e3)
""".trimIndent()
