package me.underlow.advent2024

import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.isPointInside
import me.underlow.parseToMap

object GardenGroups {

    data class Plant(val point: Point, val char: Char, val groupId: Int, val fence: List<Point>) {
        fun perimeter(field: Array<Array<Plant?>>) =
            fence.count { !field.isPointInside(it) } + fence.count { field.isPointInside(it) && field[it.row][it.col]!!.char != this.char }
    }


    fun part1(list: List<String>): Int {
        val charField = list.parseToMap()

        val field = Array<Array<Plant?>>(charField.size) {
            Array(charField[0].size) { null }
        }
        var gId = 0
        // find out groups
        for (i in charField.indices) {
            for (j in charField[0].indices) {
                if (field[i][j] != null)
                    continue

                val groupPoints = findPoints(charField, i, j, setOf(Point(i, j)))
                groupPoints.forEach { p ->
                    field[p.row][p.col] =
                        Plant(
                            p,
                            charField[p.row][p.col],
                            gId,
                            p.around()
                        )
                }
                gId++

            }
        }

        val grouped = mutableMapOf<Int, List<Plant>>()

        for (i in field.indices) {
            for (j in field[0].indices) {
                val p = field[i][j]!!
                grouped[p.groupId] = grouped.getOrElse(p.groupId) { emptyList() } + listOf(p)
            }
        }

        val s = grouped.entries.map { (k, points) ->
            val perimeter = points.sumOf { it.perimeter(field) }
            val area = points.size
            return@map perimeter * area
        }


        return s.sum()
    }

    private fun findPoints(field: Array<Array<Char>>, i: Int, j: Int, visited: Set<Point>): Set<Point> {
        val c = field[i][j]
        val p = Point(i, j)

        val candidates = p.around()
            .filter { field.isPointInside(it) }
            .filter { field[it.row][it.col] == c }
            .filter { it !in visited }
        return (candidates + listOf(p) + candidates.map { findPoints(field, it.row, it.col, visited + it) }
            .flatten()).toSet()
    }

    fun part2(list: List<String>): Int {
        val charField = list.parseToMap()

        return 0
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day12.txt")
    val res1 = GardenGroups.part1(input)
    val res2 = GardenGroups.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
