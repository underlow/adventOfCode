package me.underlow.advent2022.day12

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HillClimbingAlgorithmKtTest {

    @Test
    fun testSolution1() {
        val result = solution1(s.split("\n"))

        assertEquals(31, result)
    }
    @Test
    fun testSolution2() {
        val result = solution2(s.split("\n"))

        assertEquals(29, result)
    }
}

val s ="""
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
""".trimIndent()
