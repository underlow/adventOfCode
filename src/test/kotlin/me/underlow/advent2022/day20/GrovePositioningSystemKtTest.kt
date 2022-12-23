package me.underlow.advent2022.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GrovePositioningSystemKtTest {
    @Test
    fun testSolvePart1() {
        val result = GrovePositioningSystem.solvePart1(input.split("\n"))
        assertEquals(3, result)
    }

    @Test
    fun testSolvePart2() {
        val result = GrovePositioningSystem.solvePart2(input.split("\n"), 811589153)
        assertEquals(1623178306, result)
    }
}

private val input = """
    1
    2
    -3
    3
    -2
    0
    4
""".trimIndent()
