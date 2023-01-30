package me.underlow.advent2015

import me.underlow.advent2022.checkResult

object Matchsticks {

    fun part1(list: List<String>): Int {
        val numberInCode = list.sumOf { it.length }
        val numberEscaped = list.sumOf { it.unescape().length - 2 }
        return numberInCode - numberEscaped
    }

    fun part2(list: List<String>): Int {
        val numberInCode = list.sumOf { it.length }
        val numberAfterEscaping = list.sumOf { it.escape().length + 2 }
        return numberAfterEscaping - numberInCode
    }

}

private fun String.escape(): String {
    val buildString = buildString {
        this@escape.forEach {
            when (it) {
                '\\' -> append("\\\\")
                '\"' -> append("\\\"")
                else -> append(it)
            }
        }
    }
    return buildString
}

private fun String.unescape(): String {
    var s = this.replace("\\\\", "_")
    s = s.replace("\\\"", "_")
    val regex = Regex("\\\\x[0123456789abcdef][0123456789abcdef]")
    s = s.replace(regex, "_")
    return s
}


fun main() {
    val input = readInput("$pathPrefix/day08.txt")
    val res1 = Matchsticks.part1(input)
    val res2 = Matchsticks.part2(input)

    checkResult(res1, 1333)
    checkResult(res2, 2046)

    println(res1)
    println(res2)
}
