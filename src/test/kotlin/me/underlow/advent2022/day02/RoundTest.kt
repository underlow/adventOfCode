package me.underlow.advent2022.day02

import me.underlow.advent2022.d_02.Round
import me.underlow.advent2022.d_02.Selection
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RoundTest {
    @Test
    fun calculateRound() {
        assertEquals(4, Round(Selection.Rock, Selection.Rock).calculateRound()) //  1 + 3
        assertEquals(1, Round(Selection.Paper, Selection.Rock).calculateRound()) // 1 + 0
        assertEquals(7, Round(Selection.Scissors, Selection.Rock).calculateRound()) // 1 + 6

        assertEquals(8, Round(Selection.Rock, Selection.Paper).calculateRound()) // 2 + 6
        assertEquals(5, Round(Selection.Paper, Selection.Paper).calculateRound()) // 2 + 3
        assertEquals(2, Round(Selection.Scissors, Selection.Paper).calculateRound()) // 2 + 0

        assertEquals(3, Round(Selection.Rock, Selection.Scissors).calculateRound()) // 3 + 0
        assertEquals(9, Round(Selection.Paper, Selection.Scissors).calculateRound()) // 3 + 6
        assertEquals(6, Round(Selection.Scissors, Selection.Scissors).calculateRound()) // 3 + 3
    }
}
