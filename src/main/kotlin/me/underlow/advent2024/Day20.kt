package me.underlow.advent2024

import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object RaceCondition {

    // brute force
    fun part1(list: List<String>): Int {
        val field = list.parseToMap()

        val start = field.findFirst('S')
        val finish = field.findFirst('E')

        val baseline = field.findShortestPath(start, finish)

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
            val p2List = p1.around().filter { field.isPointInside(it) }.filter { field.get(it) == '#' }

            for (p2 in p2List) {
//                if (p1 == Point(1,8) && p2 == Point(1, 9)){
//                    field.dumpWithAxis()
//                }
                if (cheatPairs[CheatPair(p1, p2)] == null) {
                    field[p1] = '.'
                    field[p2] = '.'
//                    if (p1 == Point(1,8) && p2 == Point(1, 9)){
//                        field.dumpWithAxis()
//                    }
                    val path = field.findShortestPath(start, finish)
                    cheatPairs[CheatPair(p1, p2)] = path
                    cheatPairs[CheatPair(p2, p1)] = path
                    field[p1] = '#'
                    field[p2] = '#'
//                    field.dumpWithAxis()
                }
            }
        }

        val ss = cheatPairs.entries
            .filter { it.value < baseline }

        return cheatPairs.entries
            .filter { it.value < baseline }
            .count() / 2
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
