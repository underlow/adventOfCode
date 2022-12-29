package me.underlow.advent2015.day05

import me.underlow.TestData
import me.underlow.advent2015.day05.DoesntHeHaveInternElvesForThis.part1
import me.underlow.advent2015.day05.DoesntHeHaveInternElvesForThis.part2
import me.underlow.parametrizedTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class DoesntHeHaveInternElvesForThisTest {
    @TestFactory
    fun testPart1(): List<DynamicTest> {
        val data = listOf(
            TestData(1, "ugknbfddgicrmopn"),
            TestData(1, "aaa"),
            TestData(0, "jchzalrnumimnmhp"),
            TestData(0, "haegwjzuvuyypxyu"),
            TestData(0, "dvszwmarrgswjxmb"),
            TestData(0, "ugknbfddgicrmopn" + "pq"),
            TestData(1, "ugknbfdgicrmopnaa"),
        )

        return data.parametrizedTest {
            val result = part1(listOf(it.actual))
            assertEquals(it.expected, result)
        }
    }

    @Test
    fun testPart2() {
        val result = part2("ugknbfddgicrmopn".split("\n"))
        assertEquals(0, result)
    }
}

