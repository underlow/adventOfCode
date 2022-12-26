package me.underlow.advent2022.day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PyroclasticFlowKtTest {

    @Test
    fun testSolvePart1() {
        val res = solvePart1(input, 2022)

        assertEquals(3068, res)


    }@Test
    fun testSolvePart2() {
        val res = solvePart1(input, 1000000000000L)

        assertEquals(1514285714288L, res)
    }
}

private const val input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"
