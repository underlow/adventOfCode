package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import java.lang.Math.pow

object Scratchcards {

    data class Card(val winning: List<Int>, val num: List<Int>) {
        fun p1(): Int {
            val total = num.filter { it in winning }.count()
            return pow(2.0, total.toDouble() - 1).toInt()
        }

        fun p2(): Int {
            return num.filter { it in winning }.count()
        }
    }

    fun part1(list: List<String>): Int {
        val cards = parseInput(list)


        return cards.map { it.p1() }.sum()
    }

    fun part2(list: List<String>): Int {
        val cards = parseInput(list)

        val count = Array(list.size) { 1 }
        val winNumbers = Array(list.size) { 0 }

        for (i in cards.indices) {
            winNumbers[i] = cards[i].p2()
        }

        for (i in cards.indices) {
            repeat(count[i]) {
                for (j in 1..winNumbers[i]) {
                    if (i + j < count.size) count[i + j] = count[i + j] + 1
                }
            }
        }

        return count.sum()
    }

    private fun parseInput(list: List<String>): List<Card> {
        return list.map {
            val skipNum = it.substringAfter(":")
            val winning = skipNum.substringBefore("|").trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            val num = skipNum.substringAfter("|").trim().split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
            return@map Card(winning, num)
        }
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day04.txt")
    val res1 = Scratchcards.part1(input)
    val res2 = Scratchcards.part2(input)

    checkResult(res1, 23441)
    checkResult(res2, 0) // 23441 low

    println(res1)
    println(res2)
}
