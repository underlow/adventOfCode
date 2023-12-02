package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object CubeConundrum {
    //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    data class Game(val id: Int, val rounds: List<GameRound>)
    data class GameRound(val red: Int, val green: Int, val blue: Int)
    data class Restriction(val red: Int, val green: Int, val blue: Int) {
        fun isGamePossible(game: Game): Boolean =
            game.rounds.any {
                return@any it.blue <= this.blue && it.green <= this.green && it.red <= this.red
            }

    }

    //        only 12 red cubes, 13 green cubes, and 14 blue cubes?
    val part1Restriction = Restriction(12, 13, 14)


    fun part1(list: List<String>): Int {
        val games = parseInput(list)
        return 0
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<Game> {
        return list.map { it.toGame() }
    }
}

//Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
private fun String.toGame(): CubeConundrum.Game {
    val id = this.substring(0, this.indexOf(":")).filter { it.isDigit() }.toInt()
    val rounds = this
        .substring(this.indexOf(":") + 1, this.length).trim()
        .split(";")
        .map { it.toGameRound() }

    return CubeConundrum.Game(id, rounds)
}

private fun String.toGameRound(): CubeConundrum.GameRound {
    return CubeConundrum.GameRound(0, 0, 0)
}


fun main() {
    val input = readInput("$pathPrefix23/day02.txt")
    val res1 = CubeConundrum.part1(input)
    val res2 = CubeConundrum.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
