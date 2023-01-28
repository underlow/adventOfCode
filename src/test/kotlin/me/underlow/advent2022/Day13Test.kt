package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13KtTest {

    @Test
    fun parseInput() {
        val res = DistressSignalInput.parseInput(input.split("\n"))

        assertEquals(16, res.size)
    }

    @Test
    fun testSolution1() {
        val res = DistressSignalInput.solution1(input.split("\n"))

        assertEquals(13, res)
    }

    @Test
    fun testSolution2() {
        val res = DistressSignalInput.solution2(input.split("\n"))

        assertEquals(140, res)
    }

}


private val input = """
    [1,1,3,1,1]
    [1,1,5,1,1]

    [[1],[2,3,4]]
    [[1],4]

    [9]
    [[8,7,6]]

    [[4,4],4,4]
    [[4,4],4,4,4]

    [7,7,7,7]
    [7,7,7]

    []
    [3]

    [[[]]]
    [[]]

    [1,[2,[3,[4,[5,6,7]]]],8,9]
    [1,[2,[3,[4,[5,6,0]]]],8,9]
""".trimIndent()


