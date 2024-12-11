package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object PlutonianPebbles {

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
    val input = readInput("$pathPrefix24/day11.txt")
    val res1 = PlutonianPebbles.part1(input)
    val res2 = PlutonianPebbles.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
