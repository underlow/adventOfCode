package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12KtTest {

    @Test
    fun testSolution1() {
        val result = HillClimbingAlgorithmInput.solution1(s.split("\n"))

        assertEquals(31, result)
    }

    @Test
    fun testSolution2() {
        val result = HillClimbingAlgorithmInput.solution2(s.split("\n"))

        assertEquals(29, result)
    }
}

private val s = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
""".trimIndent()
