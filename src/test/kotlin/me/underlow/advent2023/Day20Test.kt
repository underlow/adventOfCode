package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PulsePropagationTest {
    @Test
    fun testPart1() {
        val result = PulsePropagation.part1(input.split("\n"))
        assertEquals(32000000, result)
    }

    @Test
    fun testPart12() {
        val result = PulsePropagation.part1(input2.split("\n"))
        assertEquals(11687500, result)
    }

    @Test
    fun testPart2() {
        val result = PulsePropagation.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    broadcaster -> a, b, c
    %a -> b
    %b -> c
    %c -> inv
    &inv -> a
""".trimIndent()
private val input2 = """
    broadcaster -> a
    %a -> inv, con
    &inv -> b
    %b -> con
    &con -> output
""".trimIndent()
