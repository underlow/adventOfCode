package me.underlow.advent2022

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day04Test {
    @Test
    fun calculate1Test() {
        val s = listOf(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8"
        )

        val c = CampCleanupInput.part1(s)
        assertEquals(2, c)
    }
}
