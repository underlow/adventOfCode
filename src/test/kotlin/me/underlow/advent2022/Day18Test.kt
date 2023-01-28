package me.underlow.advent2022

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun testSolvePart1() {
        val result = BoilingBouldersInput.solvePart1(input.split("\n"))
        Assertions.assertEquals(64, result)
    }

    @Test
    fun testSolvePart2() {
        val result = BoilingBouldersInput.solvePart2(input.split("\n"))
        Assertions.assertEquals(58, result)
    }  @Test

    fun testSolvePart3() {
        val result = BoilingBouldersInput.solvePart2(s2.split("\n"))
        Assertions.assertEquals(30, result)
    }
    @Test
    fun testSolvePart4() {
        val result = BoilingBouldersInput.solvePart2(s4.split("\n"))
        Assertions.assertEquals(6, result)
    }
}

private val input = """
    2,2,2
    1,2,2
    3,2,2
    2,1,2
    2,3,2
    2,2,1
    2,2,3
    2,2,4
    2,2,6
    1,2,5
    3,2,5
    2,1,5
    2,3,5
""".trimIndent()


private val s4 = """
    2,2,2
""".trimIndent()

private val s2 = """
    4,4,3
    4,3,4
    4,5,4
    3,4,4
    5,4,4
    4,4,5
""".trimIndent()
