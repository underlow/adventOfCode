package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HotSpringsTest {
    @Test
    fun testPart1() {
        val result = HotSprings.part1(input.split("\n"))
        assertEquals(21, result)
    }

    @Test
    fun testPart12() {
        val result = HotSprings.part1("???.### 1,1,3".split("\n"))
        assertEquals(1, result)
        val result2 = HotSprings.part1(".??..??...?##. 1,1,3".split("\n"))
        assertEquals(4, result2)
        val result3 = HotSprings.part1("?#?#?#?#?#?#?#? 1,3,1,6".split("\n"))
        assertEquals(1, result3)
        val result4 = HotSprings.part1("????.#...#... 4,1,1".split("\n"))
        assertEquals(1, result4)
        val result5 = HotSprings.part1("????.######..#####. 1,6,5".split("\n"))
        assertEquals(4, result5)
        val result6 = HotSprings.part1("?###???????? 3,2,1".split("\n"))
        assertEquals(10, result6)
    }

    @Test
    fun testPart2() {
        val result = HotSprings.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    ???.### 1,1,3
    .??..??...?##. 1,1,3
    ?#?#?#?#?#?#?#? 1,3,1,6
    ????.#...#... 4,1,1
    ????.######..#####. 1,6,5
    ?###???????? 3,2,1
""".trimIndent()
//    ???.### 1,1,3
