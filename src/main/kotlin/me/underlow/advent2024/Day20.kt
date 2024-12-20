package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.parseToMap

object RaceCondition {

    fun part1(list: List<String>): Int {
        val field = list.parseToMap()
        return 0
    }

    fun part2(list: List<String>): Int {
        val field = list.parseToMap()
        return 0
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day20.txt")
    val res1 = RaceCondition.part1(input)
    val res2 = RaceCondition.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
