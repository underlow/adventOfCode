package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object CamelCards {

    fun part1(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Any {
        return 0
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day07.txt")
    val res1 = CamelCards.part1(input)
    val res2 = CamelCards.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 0)
    checkResult(res2, 0)
}
