package me.underlow.advent2022.d_02

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.pathPrefix
import me.underlow.advent2022.readInput

// https://adventofcode.com/2022/day/2

object RockPaperScissors {
    enum class Selection(val price: Int) {
        Rock(1),
        Paper(2),
        Scissors(3);

        companion object {
            fun fromString(s: String): Selection =
                when (s) {
                    "A", "X" -> Rock
                    "B", "Y" -> Paper
                    "C", "Z" -> Scissors
                    else -> error("Incorrect input")
                }
        }
    }

    data class Round(val opponent: Selection, val you: Selection) {
        // Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock
        // returns
        // 0 if opponent wins
        // 3 if it is a draw
        // 6 if you wins
        fun points(): Int =
            when {
                opponent == you -> 3
                (you == Selection.Rock && opponent == Selection.Scissors) ||
                        (you == Selection.Scissors && opponent == Selection.Paper) ||
                        (you == Selection.Paper && opponent == Selection.Rock) -> 6

                else -> 0
            }

        /**
         * A for Rock, B for Paper, and C for Scissors
         * X for Rock, Y for Paper, and Z for Scissors
         */
        fun calculateRound(): Int {
            return you.price + points()
        }

        companion object {
            fun fromStringToSimpleRound(s: String): Round {
                val split = s.split(" ")
                return Round(Selection.fromString(split[0]), Selection.fromString(split[1]))
            }

            fun fromStringToSecondRound(s: String): Round {
                val split = s.split(" ")
                val opponent = Selection.fromString(split[0])
                val you = when (split[1]) {
                    "X" /*Lose*/ ->
                        when (opponent) {
                            Selection.Paper -> Selection.Rock
                            Selection.Rock -> Selection.Scissors
                            Selection.Scissors -> Selection.Paper
                        }

                    "Y" /*Draw*/ ->
                        opponent

                    "Z" /*Win*/ ->
                        when (opponent) {
                            Selection.Paper -> Selection.Scissors
                            Selection.Rock -> Selection.Paper
                            Selection.Scissors -> Selection.Rock
                        }

                    else -> error("Incorrect input data")
                }
                return Round(opponent, you)
            }
        }
    }
}

fun main() {
    val rounds = readInput("$pathPrefix/day02.txt")
    val result1 = rounds.map { RockPaperScissors.Round.fromStringToSimpleRound(it) }.map { it.calculateRound() }.sum()
    val result2 = rounds.map { RockPaperScissors.Round.fromStringToSecondRound(it) }.map { it.calculateRound() }.sum()

    checkResult(result1, 13221)
    checkResult(result2, 13131)

    println(result1)
    println(result2)
}
