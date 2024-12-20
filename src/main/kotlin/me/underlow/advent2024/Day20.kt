package me.underlow.advent2024

import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object RaceCondition {

    // brute force
    fun part1(list: List<String>, save: Int): Int {
        val field = list.parseToMap()

        val start = field.findFirst('S')
        val finish = field.findFirst('E')

        val baseline = field.findShortestPathLength(start, finish)

        // find all walls
        val walls = mutableListOf<Point>()
        for (i in field.indices) {
            for (j in field[0].indices) {
                if (field[i][j] == '#')
                    walls += Point(i, j)
            }
        }

        data class CheatPair(val p1: Point, val p2: Point)
        //deduplicate pairs of cheating points
        val cheatPairs = mutableMapOf<CheatPair, Int>()
        // for each pair of close walls calculate path and store it

        for (p1 in walls) {

            field[p1] = '.'
            val path = field.findShortestPath(start, finish)
            field[p1] = '#'

            val m1 = path.indexOfFirst { it == p1 }
            when (m1) {
                -1 -> continue
                0 -> cheatPairs[CheatPair(start, path[m1])] = path.size
                else -> {
                    cheatPairs[CheatPair(path[m1 - 1], path[m1])] = path.size
                }
            }
        }

        val ss = cheatPairs.entries
            .filter { it.value < baseline }
        val ss2 = cheatPairs.entries
            .filter { it.value == 21 }

        ss.sortedBy { it.value }.groupBy { it.value }.forEach { (t, u) ->
            println("${baseline - t}: ${u.size}")
        }

        return cheatPairs.entries
            .filter { it.value < baseline - (save - 1) }
            .count()
    }

    fun part2(list: List<String>): Int {
        val field = list.parseToMap()
        return 0
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day20.txt")
    val res1 = RaceCondition.part1(input, 100)
    val res2 = RaceCondition.part2(input)

    checkResult(res1, 1409) // 6966 high, 1385 low
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
