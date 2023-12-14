package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointofIncidenceTest {
    @Test
    fun testPart1() {
        val result = PointofIncidence.part1(input.split("\n"))
        assertEquals(405, result)
    }

    @Test
    fun testPart2() {
        val result = PointofIncidence.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    #.##..##.
    ..#.##.#.
    ##......#
    ##......#
    ..#.##.#.
    ..##..##.
    #.#.##.#.

    #...##..#
    #....#..#
    ..##..###
    #####.##.
    #####.##.
    ..##..###
    #....#..#
""".trimIndent()
