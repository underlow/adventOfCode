package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LensLibraryTest {
    @Test
    fun testPart1() {
        val result = LensLibrary.part1(input.split("\n"))
        assertEquals(1320, result)
    }

    @Test
    fun testPart2() {
        val result = LensLibrary.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
""".trimIndent()
