package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object PlutonianPebbles {

    fun part1(list: String): Int {
        val stones = parseInput(list)
        return 0
    }

    fun part2(list: String): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: String): Any {
        return 0
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day11.txt")
    val res1 = PlutonianPebbles.part1("4 4841539 66 5279 49207 134 609568 0")
    val res2 = PlutonianPebbles.part2("4 4841539 66 5279 49207 134 609568 0")

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
