package me.underlow.advent2023

import me.underlow.advent2022.checkResult

object WaitForIt {

    data class Race(val time: Long, val distance: Long) {
        fun waysToWin(): Int {
            var c = 0
            for (i in 0..time) {
                if (i * (time - i) > distance)
                    c++
            }
            return c
        }
    }

    fun part1(list: List<String>): Int {
        val races = parseInput(list)

        return races.map { it.waysToWin() }.reduce { acc, i -> acc * i }

    }

    fun part2(list: List<String>): Int {
        val races = parseInput2(list)

        return races.map { it.waysToWin() }.reduce { acc, i -> acc * i }

    }

    private fun parseInput(list: List<String>): List<Race> {
        val times = list[0].substring(5, list[0].length)
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.toLong() }
        val distances = list[1].substring(10, list[1].length)
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.toLong() }
        return (0 until times.size).map { Race(times[it], distances[it]) }
    }

    private fun parseInput2(list: List<String>): List<Race> {
        val times = list[0].substring(5, list[0].length)
            .filter { it.isDigit() }.toLong()
        val distances = list[1].substring(10, list[1].length)
            .filter { it.isDigit() }.toLong()
        return listOf(Race(times, distances))
    }
}


fun main() {
    val input = """
        Time:        46     80     78     66
        Distance:   214   1177   1402   1024
    """.trimIndent().split("\n")

    val res1 = WaitForIt.part1(input)
    val res2 = WaitForIt.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 512295)
    checkResult(res2, 0)
}
