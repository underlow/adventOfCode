package me.underlow.advent2022.day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TreetopTreeHouseKtTest {

    @Test
    fun checkVisibility() {
        val s = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent()

        val count = solution1(s.split("\n"))

        assertEquals(21, count)

    }

    @Test
    fun checkVisibility2() {
        val s = """
            00000
            00000
            00000
            21000
            00000
        """.trimIndent()

        val count = solution1(s.split("\n"))

        assertEquals(17, count)

    }

    @Test
    fun checkVisibility3() {
        val s = """
            0000
            0200
            2120
            0200
        """.trimIndent()

        val count = solution1(s.split("\n"))

        assertEquals(14, count)

    }

    @Test
    fun calcCoef() {
        val s = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent()

        val count = solution2(s.split("\n"))

        assertEquals(8, count)

    }
}
