package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class StepCounterTest {
    @Test
    fun testPart1() {
        val result = StepCounter.part1(input.split("\n"), 6)
        assertEquals(16, result)
    }

    @Test
    @Disabled
    fun testPart2() {
//        var result = StepCounter.part2(input.split("\n"), 6)
//        assertEquals(16, result)
//        result = StepCounter.part2(input.split("\n"), 10)
//        assertEquals(50, result)
//        result = StepCounter.part2(input.split("\n"), 50)
//        assertEquals(1594, result)
//        result = StepCounter.part2(input.split("\n"), 100)
//        assertEquals(6536, result)
//        result = StepCounter.part2(input.split("\n"), 500)
//        assertEquals(167004, result)
//        result = StepCounter.part2(input.split("\n"), 1000)
//        assertEquals(668697, result)
//        result = StepCounter.part2(input.split("\n"), 5000)
//        assertEquals(16733044, result)
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
