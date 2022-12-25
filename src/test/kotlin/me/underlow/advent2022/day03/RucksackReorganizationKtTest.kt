package me.underlow.advent2022.day03

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RucksackReorganizationKtTest {

    @Test
    fun calculatePriority() {
        val strings =  """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()
        val total = calculatePriority(strings.split("\n"))

        assertEquals(157, total)
    }

    @Test
    fun calculate2part() {
        val strings =  """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()
        val total = calculate2part(strings.split("\n"))

        assertEquals(70, total)
    }


}
