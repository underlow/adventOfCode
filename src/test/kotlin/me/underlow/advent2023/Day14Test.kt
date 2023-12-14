package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParabolicReflectorDishTest {
    @Test
    fun testPart1() {
        val result = ParabolicReflectorDish.part1(input.split("\n"))
        assertEquals(136, result)
    }

    @Test
    fun testPart2() {
        val result = ParabolicReflectorDish.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    O....#....
    O.OO#....#
    .....##...
    OO.#O....O
    .O.....O#.
    O.#..O.#.#
    ..O..#O..O
    .......O..
    #....###..
    #OO..#....
""".trimIndent()
