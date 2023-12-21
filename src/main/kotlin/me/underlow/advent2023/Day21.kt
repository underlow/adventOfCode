package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object StepCounter {

    fun part1(list: List<String>, steps: Int): Int {
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
    val input = readInput("$pathPrefix23/day21.txt")
    val res1 = StepCounter.part1(input, 6)
    val res2 = StepCounter.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 0)
    checkResult(res2, 0)
}
