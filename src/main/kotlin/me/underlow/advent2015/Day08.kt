package me.underlow.advent2015

import me.underlow.advent2022.checkResult

object Matchsticks {

    fun part1(list: List<String>): Int {
        val numberInCode = list.map { it.length + 2 }.sum()
        val numberEscaped = list.map { it.unescape().length }.sum()
        return numberInCode - numberEscaped
    }

    fun part2(list: List<String>): Int {
        return 0
    }

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
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
