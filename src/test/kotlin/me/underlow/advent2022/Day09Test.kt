package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day09Test {

    @Test
    fun testSolution1() {
        val input = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent()

        val result = RopeBridgeInput.solution1(input.split("\n"))
        assertEquals(14, result)
    }

    @Test
    fun testSolution2() {
        val input = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent()

        val result = RopeBridgeInput.solution2(input.split("\n"))
        assertEquals(36, result)
    }
}
