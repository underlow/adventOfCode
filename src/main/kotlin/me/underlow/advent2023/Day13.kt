package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object PointofIncidence {
    enum class Cell(val n: Int) { Mirror(1), Field(0) }

    fun part1(list: List<String>): Int {
        val mirrors = parseInput(list)
        return mirrors.map { calc1(it) }.sum()

    }

    fun calc1(mirror: Array<Array<Cell>>): Int {
        // number of mirrors in columns
        val colCounts = columnCounts(mirror)

        val rowCounts = rowCounts(mirror)

        val possibleColumns = possibleMirrors(colCounts)
        val possibleRows = possibleMirrors(rowCounts)

        val mirrorColumns = possibleColumns.filter { columnMirror(it, mirror) }
        val mirrorRows = possibleRows.filter { rowMirror(it, mirror) }



        println("pc: $possibleColumns pr: $possibleRows")
        val column = mirrorColumns.firstOrNull() ?: 0
        val row = mirrorRows.firstOrNull() ?: 0

        return column + 100 * row
    }

    fun columnMirror(center: Int, mirror: Array<Array<Cell>>): Boolean {
        var i = 0
        var isMirror = true
//            println("Starting $center position")
        while (center - i - 1 >= 0 && center + i < mirror[0].size) {
//                println("Comparing  ${center - i - 1} with ${center + i} (${colCounts[center - i - 1]} with ${colCounts[center + i]})")
            if (!columnsEqual(mirror, center - i - 1, center + i)) {
                isMirror = false
                break
            }
            i++
        }
        if (isMirror) {
//                println("Candidate: $center")
            return isMirror
        }
        return isMirror
    }

    fun rowMirror(center: Int, mirror: Array<Array<Cell>>): Boolean {
        var i = 0
        var isMirror = true
//            println("Starting $center position")
        while (center - i - 1 >= 0 && center + i < mirror.size) {
//                println("Comparing  ${center - i - 1} with ${center + i} (${colCounts[center - i - 1]} with ${colCounts[center + i]})")
            if (!rowsEqual(mirror, center - i - 1, center + i)) {
                isMirror = false
                break
            }
            i++
        }
        if (isMirror) {
//                println("Candidate: $center")
            return isMirror
        }
        return isMirror
    }

    private fun columnsEqual(mirror: Array<Array<Cell>>, c1: Int, c2: Int): Boolean {
        var equal = true
        for (i in mirror.indices) {
            if (mirror[i][c1] != mirror[i][c2])
                equal = false
        }
        return equal
    }

    private fun rowsEqual(mirror: Array<Array<Cell>>, r1: Int, r2: Int): Boolean {
        var equal = true
        for (i in mirror[0].indices) {
            if (mirror[r1][i] != mirror[r2][i])
                equal = false
        }
        return equal
    }

    fun rowCounts(mirror: Array<Array<Cell>>) =
        mirror.map { it.sumOf { it.n } }

    fun columnCounts(mirror: Array<Array<Cell>>): MutableList<Int> {
        val colCounts = MutableList(mirror[0].size) { 0 }

        for (i in mirror[0].indices) {
            for (j in mirror.indices)
                colCounts[i] += mirror[j][i].n
        }
        return colCounts
    }

    fun possibleMirrors(
        colCounts: List<Int>
    ): List<Int> {
        // find possible vertical mirror
        val possibleColumns = mutableListOf<Int>()

        for (center in 1 until colCounts.size) {
            var i = 0
            var isMirror = true
//            println("Starting $center position")
            while (center - i - 1 >= 0 && center + i < colCounts.size) {
//                println("Comparing  ${center - i - 1} with ${center + i} (${colCounts[center - i - 1]} with ${colCounts[center + i]})")
                if (colCounts[center - i - 1] != colCounts[center + i]) {
                    isMirror = false
                    break
                }
                i++
            }
            if (isMirror) {
//                println("Candidate: $center")
                possibleColumns.add(center)
            }
        }
        return possibleColumns
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    fun parseInput(list: List<String>): List<Array<Array<Cell>>> {
        val groups = mutableListOf<MutableList<String>>()
        groups.add(mutableListOf())
        list.forEach { l ->
            if (l.isEmpty())
                groups.add(mutableListOf())
            else
                groups.last().add(l)
        }

        return groups.map {
            it.map {
                it.toCharArray().map { if (it == '#') Cell.Mirror else Cell.Field }.toTypedArray()
            }.toTypedArray()
        }
    }


}


fun main() {
    val input = readInput("$pathPrefix23/day13.txt")
    val res1 = PointofIncidence.part1(input)
    val res2 = PointofIncidence.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 33195)
    checkResult(res2, 0)
}
