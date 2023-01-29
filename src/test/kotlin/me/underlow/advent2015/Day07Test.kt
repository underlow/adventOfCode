package me.underlow.advent2015

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day07Test {
    @Test
    fun testPart1() {
        val result = SomeAssemblyRequired.part1(input.split("\n"), "h")
        val expected: UShort = 65412u
        assertTrue(expected == result)
    }

    @Test
    fun testPart2() {
//        val result = .part2(input.split("\n"))
//        assertEquals(0, result)
    }
}

private val input = """
    123 -> x
    456 -> y
    x AND y -> d
    x OR y -> e
    x LSHIFT 2 -> f
    y RSHIFT 2 -> g
    NOT x -> h
    NOT y -> i
""".trimIndent()
