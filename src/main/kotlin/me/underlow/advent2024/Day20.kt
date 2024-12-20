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
            val p2List = p1.around().filter { field.isPointInside(it) }.filter { field.get(it) != 'S' }

            for (p2 in p2List) {
//                if (p1 == Point(1,8) && p2 == Point(1, 9)){
//                    field.dumpWithAxis()
//                }
//                if (cheatPairs[CheatPair(p1, p2)] == null) {
                field[p1] = '.'
                val p1Old = field.get(p2)
//                field[p2] = '.'
//                    if (p1 == Point(1,8) && p2 == Point(1, 9)){
//                        field.dumpWithAxis()
//                    }
                val path = listOf(start) + field.findShortestPath(start, finish)
                field[p1] = '#'
//                field[p2] = p1Old
                // let's find cheat points

                val m1 = path.indexOfFirst { it == p1 }
//                val m2 = path.indexOfFirst { it == p2 }
//                if (m1 == -1 && m2 == -1) {
//                    continue
//                }
//                if (m2 == 0){
//                    println()
//                }
                when {
                    m1 == -1 /*&& m2 == -1*/ -> continue
//                    m1 == -1 -> {
//                        cheatPairs[CheatPair(path[m2 - 1], path[m2])] = path.size
//
//                    }
//                    m2 == -1 -> {
//                        cheatPairs[CheatPair(path[m1 - 1], path[m1])] = path.size
//
//                    }
//                    m1 < m2 -> {
//                        cheatPairs[CheatPair(path[m1 - 1], path[m1])] = path.size
//                    }
                    else -> {
                        cheatPairs[CheatPair(path[m1 - 1], path[m1])] = path.size
                    }
                }

//                    cheatPairs[CheatPair(p1, p2)] = path
//                    cheatPairs[CheatPair(p2, p1)] = path

//                    field.dumpWithAxis()
//                }
            }
        }

        val ss = cheatPairs.entries
            .filter { it.value < baseline }
        val ss2 = cheatPairs.entries
            .filter { it.value == 21 }

        return cheatPairs.entries
            .filter { it.value < baseline }
            .count()
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
