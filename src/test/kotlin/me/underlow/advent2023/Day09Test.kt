package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MirageMaintenanceTest {
    @Test
    fun testPart1() {
        val result = MirageMaintenance.part1(input.split("\n"))
        assertEquals(114, result)
    }

    @Test
    fun testPart2() {
        val result = MirageMaintenance.part2(input.split("\n"))
        assertEquals(2, result)
    }
}

private val input = """
    0 3 6 9 12 15
    1 3 6 10 15 21
    10 13 16 21 30 45
""".trimIndent()
