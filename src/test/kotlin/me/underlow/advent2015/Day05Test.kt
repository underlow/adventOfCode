package me.underlow.advent2015

import me.underlow.TestData
import me.underlow.advent2015.DoesntHeHaveInternElvesForThis.part1
import me.underlow.advent2015.DoesntHeHaveInternElvesForThis.part2
import me.underlow.parametrizedTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class Day05Test {
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

    @TestFactory
    fun testPart2(): List<DynamicTest> {
        val data = listOf(
            TestData(1, "qjhvhtzxzqqjkmpb"),
            TestData(1, "xxyxx"),
            TestData(0, "uurcxstgmygtbstg"),
            TestData(0, "ieodomkazucvgmuy"),
        )

        return data.parametrizedTest {
            val result = part2(listOf(it.actual))
            assertEquals(it.expected, result)
        }
    }
}

