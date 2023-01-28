package me.underlow.advent2022

import me.underlow.advent2022.d_02.RockPaperScissors
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {
    @Test
    fun calculateRound() {
        assertEquals(
            4,
            RockPaperScissors.Round(RockPaperScissors.Selection.Rock, RockPaperScissors.Selection.Rock).calculateRound()
        ) //  1 + 3
        assertEquals(
            1,
            RockPaperScissors.Round(RockPaperScissors.Selection.Paper, RockPaperScissors.Selection.Rock)
                .calculateRound()
        ) // 1 + 0
        assertEquals(
            7,
            RockPaperScissors.Round(RockPaperScissors.Selection.Scissors, RockPaperScissors.Selection.Rock)
                .calculateRound()
        ) // 1 + 6

        assertEquals(
            8,
            RockPaperScissors.Round(RockPaperScissors.Selection.Rock, RockPaperScissors.Selection.Paper)
                .calculateRound()
        ) // 2 + 6
        assertEquals(
            5,
            RockPaperScissors.Round(RockPaperScissors.Selection.Paper, RockPaperScissors.Selection.Paper)
                .calculateRound()
        ) // 2 + 3
        assertEquals(
            2,
            RockPaperScissors.Round(RockPaperScissors.Selection.Scissors, RockPaperScissors.Selection.Paper)
                .calculateRound()
        ) // 2 + 0

        assertEquals(
            3,
            RockPaperScissors.Round(RockPaperScissors.Selection.Rock, RockPaperScissors.Selection.Scissors)
                .calculateRound()
        ) // 3 + 0
        assertEquals(
            9,
            RockPaperScissors.Round(RockPaperScissors.Selection.Paper, RockPaperScissors.Selection.Scissors)
                .calculateRound()
        ) // 3 + 6
        assertEquals(
            6,
            RockPaperScissors.Round(RockPaperScissors.Selection.Scissors, RockPaperScissors.Selection.Scissors)
                .calculateRound()
        ) // 3 + 3
    }
}
