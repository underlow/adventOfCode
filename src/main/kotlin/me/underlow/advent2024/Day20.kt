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


    fun part2(list: List<String>, save: Int, cheatDistance: Int): Int {
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

        val memo = mutableMapOf<Pair<Point, Point>, Int>()
        for ((idx, p1) in (listOf(start) + baseline).withIndex()) {

            // all points we can get to in 20 steps
            val allPointsDistance = field.getAllPointsDistance(p1, cheatDistance + 1)

            for (p2 in allPointsDistance) {
                if (p2 == p1)
                    continue

                if (field.get(p2) == '#')
                    continue

                val path = if (memo[p2 to finish] == null) {
                    val path = field.findShortestPathLength(p2, finish)
                    memo[p2 to finish] = path
                    path
                } else {
                    memo[p2 to finish]!!
                }

                val value = idx + path + p1.distance(p2)
                if (value < baseline.size - (save - 1)) {
                    cheatPairs += CheatPair(p1, p2) to value
                }

            }
        }

        val ss = cheatPairs.entries
//            .filter { it.value < baseline }
        val ss2 = cheatPairs.entries
            .filter { it.value == cheatDistance }

//        ss.sortedBy { it.value }.groupBy { it.value }.forEach { (t, u) ->
//            println("${baseline.size - t}: ${u.size}")
//        }

        return cheatPairs.entries
            .filter { it.value < baseline.size - (save - 1) }
            .count()
    }

}

private fun Array<Array<Char>>.getAllPointsDistance(p1: Point, distance: Int): MutableSet<Point> {
    val ret = mutableSetOf<Point>()
    for (i in -distance..distance)
        for (j in -distance..distance) {
            if (isPointInside(Point(p1.row + i, p1.col + j)) && p1.distance(
                    Point(
                        p1.row + i,
                        p1.col + j
                    )
                ) <= distance
            ) {
                ret += Point(p1.row + i, p1.col + j)
            }
        }
    return ret
}


fun main() {
    val input = readInput("$pathPrefix24/day20.txt")
    val res1 = RaceCondition.part2(input, 100, 1)
    val res2 = RaceCondition.part2(input, 100, 20)

    checkResult(res1, 1409) // 6966 high, 1385 low
    checkResult(res2, 0) // 1144969 high

    println(res1)
    println(res2)
}
