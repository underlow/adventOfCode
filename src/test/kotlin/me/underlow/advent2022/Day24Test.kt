package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BlizzardBasinTest {
    @Test
    fun testPart1() {
        val result = BlizzardBasin.part1(input1.split("\n"))
        assertEquals(18, result)
    }

    @Test
    fun testPart2() {
        val result = BlizzardBasin.part2(input1.split("\n"))
        assertEquals(54, result)
    }
}

private val input1 = """
    #.######
    #>>.<^<#
    #.<..<<#
    #>v.><>#
    #<^v^^>#
    ######.#
""".trimIndent()


class AirNumberTest {
    @Test
    fun `test convertion from 5base to 10base`() {
        val list = listOf(
            "1=-0-2" to 1747,
            "12111" to 906,
            "2=0=" to 198,
            "21" to 11,
            "2=01" to 201,
            "111" to 31,
            "20012" to 1257,
            "112" to 32,
            "1=-1=" to 353,
            "1-12" to 107,
            "12" to 7,
            "1=" to 3,
            "122" to 37
        )
        list.forEach { (from, to) ->
            assertEquals(to, AirNumber.fromString(from).toDecimal())

        }
    }

    @Test
    fun `test sum of two AirNumbers`() {
        data class TestData(val l: String, val r: String, val expected: Int)

        val list = listOf(
            TestData("112", "12", 39),
            TestData("1=-0-2", "1=-0-2", 2 * 1747),
            TestData("1=", "1=", 2 * 3),
            TestData("2", "2", 4),
        )
        list.forEach { td ->
            assertEquals(td.expected, (AirNumber.fromString(td.l) + AirNumber.fromString(td.r)).toDecimal())
        }
    }

    @Test
    fun `check test data step by step`() {
        val numbers = input.split("\n").map { AirNumber.fromString(it) }
        var acc = AirNumber.fromString("0")
        var accDec = 0
        for (i in numbers.indices) {
            acc = acc + numbers[i]
            accDec = accDec + numbers[i].toDecimal()
            assertEquals(acc.toDecimal(), accDec, "Fail to add $numbers[i]")
        }
        assertEquals(4890, accDec)
        assertEquals(4890, acc.toDecimal())
    }
}

private val input = """
    1=-0-2
    12111
    2=0=
    21
    2=01
    111
    20012
    112
    1=-1=
    1-12
    12
    1=
    122
""".trimIndent()

