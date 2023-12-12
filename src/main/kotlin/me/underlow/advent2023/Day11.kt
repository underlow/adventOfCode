package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import kotlin.math.max
import kotlin.math.min

object CosmicExpansion {

    data class Point(val row: Int, val col: Int)

    fun part1(list: List<String>, expandSize: Int): Long {
        val galaxy = parseInput(list)

        val emptyRows = mutableListOf<Int>()
        val emptyCols = mutableListOf<Int>()

        for (i in galaxy.indices) {
            if (galaxy.rowEmpty(i))
                emptyRows.add(i)
        }
        for (i in galaxy.indices) {
            if (galaxy.colEmpty(i))
                emptyCols.add(i)
        }

        val galaxies = mutableListOf<Point>()

        for (i in galaxy.indices) {
            for (j in galaxy[0].indices) {
                if (galaxy[i][j] == '#') {
                    galaxies.add(Point(i, j))
                }
            }
        }

        var sum = 0L
        for (i in galaxies.indices)
            for (j in (i + 1) until galaxies.size) {
                val calcPath = calcPath(galaxies[i], galaxies[j], galaxy, emptyRows, emptyCols, expandSize)
                println("Between galaxy ${galaxies[i]} and galaxy ${galaxies[j]}: $calcPath")
                sum += calcPath
            }

        return sum
    }

    private fun calcPath(
        from: Point,
        to: Point,
        galaxy: Array<Array<Char>>,
        emptyRows: MutableList<Int>,
        emptyCols: MutableList<Int>,
        expandSize: Int
    ): Long {
        if (from == to) return 0

        if (from == Point(5, 1) && to == Point(9, 4)) {
            println()
        }

        val minCol = min(from.col, to.col)
        val maxCol = max(from.col, to.col)
        val upDown = (maxCol - minCol) + emptyCols.filter { it in minCol..maxCol }.size * (expandSize - 1).toLong()

        val minRow = min(from.row, to.row)
        val maxRow = max(from.row, to.row)
        val leftRight = (maxRow - minRow) + emptyRows.filter { it in minRow..maxRow }.size * (expandSize - 1).toLong()

        return upDown + leftRight
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.map { it.toCharArray().toTypedArray() }.toTypedArray()

    }
}

private fun Array<Array<Char>>.rowEmpty(i: Int): Boolean {
    var empty = true
    for (j in this.indices) {
        if (this[i][j] != '.')
            empty = false
    }
    return empty
}

private fun Array<Array<Char>>.colEmpty(i: Int): Boolean {
    var empty = true
    for (j in this.indices) {
        if (this[j][i] != '.')
            empty = false
    }
    return empty
}


fun main() {
    val input = readInput("$pathPrefix23/day11.txt")
    val res1 = CosmicExpansion.part1(input, 2)
    val res2 = CosmicExpansion.part1(input, 1_000_000)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 9965032)
    checkResult(res2, 0)  //603050444 to low
}
