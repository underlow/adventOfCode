package me.underlow.advent2024

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DiskFragmenterTest {
    @Test
    fun testPart1() {
        val result = DiskFragmenter.part1(input.split("\n"))
        assertEquals(1928, result)
    }

    @Test
    fun testPart2() {
        val result = DiskFragmenter.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
2333133121414131402
""".trimIndent()
