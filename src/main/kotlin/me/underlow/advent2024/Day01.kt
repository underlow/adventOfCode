package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import kotlin.math.abs

object HistorianHysteria {

    fun part1(list: List<String>): Int {
        val (l1, l2) = parseInput(list)
        val l1sorted = l1.sorted()
        val l2sorted = l2.sorted()
        return l1sorted.mapIndexed { index, value -> abs(value - l2sorted[index]) }.sum()
    }

    fun part2(list: List<String>): Long {
        val (l1, l2) = parseInput(list)
        val m2 = l2.groupBy { it }.mapValues { it.value.count() }

        val l1count = l1.map { it.toLong() * (m2[it] ?: 0) }.sum()

        return l1count
    }

    private fun parseInput(list: List<String>): Pair<List<Int>, List<Int>> {
        val l1 = list.map { it.split(" ").filter { it.isNotEmpty() } }.map { it[0].toInt() }
        val l2 = list.map { it.split(" ").filter { it.isNotEmpty() } }.map { it[1].toInt() }
        return l1 to l2
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day01.txt")
    val res1 = HistorianHysteria.part1(input)
    val res2 = HistorianHysteria.part2(input)

    checkResult(res1, 1579939)
    checkResult(res2, 0L)

    println(res1)
    println(res2)
}

const val pathPrefix24 = "src/main/kotlin/me/underlow/advent2024"
