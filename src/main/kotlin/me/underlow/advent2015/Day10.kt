package me.underlow.advent2015

import me.underlow.advent2022.checkResult

object ElvesLookElvesSay {

    fun part1(input: String): Int {
        var result = input
        repeat(40) {
            result = gameRound(result)
        }
        return result.length
    }

    fun part2(input: String): Int {
        var result = input
        repeat(50) {
            result = gameRound(result)
        }
        return result.length
    }

    fun gameRound(input: String): String {
        val result = mutableListOf<Pair<Int, Int>>()
        input.forEach { ch ->

            if (result.isEmpty())
                result += ch.digitToInt() to 0

            var current: Pair<Int, Int> = result.last()

            if (current.first == ch.digitToInt()) {
                current = current.copy(second = current.second + 1)
                result[result.size - 1] = current
                return@forEach
            }
            result += current
            current = ch.digitToInt() to 1
            result[result.size - 1] = current
        }


        return result.map { "${it.second}${it.first}" }.joinToString("")
    }

}


fun main() {
    val res1 = ElvesLookElvesSay.part1("3113322113")
    val res2 = ElvesLookElvesSay.part2("3113322113")

    checkResult(res1, 329356)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
