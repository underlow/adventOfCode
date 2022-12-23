package me.underlow.advent2022.day23

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UnstableDiffusionTest {
    @Test
    fun testPart1() {
        val result = UnstableDiffusion.part1(input.split("\n"))
        assertEquals(110, result)
    }

    @Test
    fun testPart2() {
        val result = UnstableDiffusion.part2(input.split("\n"))
        assertEquals(20, result)
    }
}


private val input = """
    ....#..
    ..###.#
    #...#.#
    .#...##
    #.###..
    ##.#.##
    .#..#..
""".trimIndent()
