package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StepCounterTest {
    @Test
    fun testPart1() {
        val result = StepCounter.part1(input.split("\n"), 6)
        assertEquals(16, result)
    }

    @Test
    fun testPart2() {
        val result = StepCounter.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    ...........
    .....###.#.
    .###.##..#.
    ..#.#...#..
    ....#.#....
    .##..S####.
    .##..#...#.
    .......##..
    .##.#.####.
    .##..##.##.
    ...........
""".trimIndent()
