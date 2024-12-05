package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SandSlabsTest {
    @Test
    @Disabled
    fun testPart1() {
        val result = SandSlabs.part1(input.split("\n"))
        assertEquals(5, result)
    }

    @Test
    fun testPart2() {
        val result = SandSlabs.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    1,0,1~1,2,1
    0,0,2~2,0,2
    0,2,3~2,2,3
    0,0,4~0,2,4
    2,0,5~2,2,5
    0,1,6~2,1,6
    1,1,8~1,1,9
""".trimIndent()
