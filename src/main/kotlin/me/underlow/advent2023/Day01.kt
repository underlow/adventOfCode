package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object Trebuchet {

    fun part1(list: List<String>): Int {
        return list.map { s ->
            val numbers = s.filter { it.isDigit() }
            val number = numbers.first().digitToInt() * 10 + numbers.last().digitToInt()
            return@map number
        }.sum()
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
    val input = readInput("$pathPrefix23/day01.txt")
    val res1 = Trebuchet.part1(input)
    val res2 = Trebuchet.part2(input)

    checkResult(res1, 54916)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}

const val pathPrefix23 = "src/main/kotlin/me/underlow/advent2023"
