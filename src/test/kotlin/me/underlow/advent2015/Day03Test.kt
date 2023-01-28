package me.underlow.advent2015

import me.underlow.TestData
import me.underlow.parametrizedTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class Day03Test {
    @TestFactory
    fun testPart1(): List<DynamicTest> {
        val data = listOf(
            TestData(2, ">"),
            TestData(4, "^>v<"),
            TestData(2, "^v^v^v^v^v"),
        )

        return data.parametrizedTest {
            val result = PerfectlySphericalHousesInAVacuum.part1(it.actual)
            assertEquals(it.expected, result)
        }
    }


}
