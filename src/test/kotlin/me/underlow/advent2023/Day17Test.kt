package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ClumsyCrucibleTest {
    @Test
    fun testPart1() {
        val result = ClumsyCrucible.part1(input.split("\n"))
        assertEquals(102, result)
    }

    @Test
    fun testPart2() {
        val result = ClumsyCrucible.part22(input.split("\n"))
        assertEquals(94, result)
    }

    @Test
    fun testPart22() {
        val result = ClumsyCrucible.part22(input12.split("\n"))
        assertEquals(71, result)
    }

}

private val input = """
    2413432311323
    3215453535623
    3255245654254
    3446585845452
    4546657867536
    1438598798454
    4457876987766
    3637877979653
    4654967986887
    4564679986453
    1224686865563
    2546548887735
    4322674655533
""".trimIndent()
private val input12 = """
    111111111111
    999999999991
    999999999991
    999999999991
    999999999991
""".trimIndent()
