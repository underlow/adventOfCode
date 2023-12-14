package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object PointofIncidence {
    enum class Cell(val n: Int) { Mirror(1), Field(0) }

    fun part1(list: List<String>): Int {
        val mirrors = parseInput(list)
        return mirrors.map {
            val (mirrorColumns, mirrorRows) = calc1(it)
            println("pc: $mirrorColumns pr: $mirrorRows")
            val column = mirrorColumns.firstOrNull() ?: 0
            val row = mirrorRows.firstOrNull() ?: 0

            return@map column + 100 * row
        }.sum()

    }

    fun calc1(mirror: Array<Array<Cell>>): Pair<List<Int>, List<Int>> {
        // number of mirrors in columns
        val colCounts = columnCounts(mirror)

        val rowCounts = rowCounts(mirror)

        val possibleColumns = possibleMirrors(colCounts)
        val possibleRows = possibleMirrors(rowCounts)

        val mirrorColumns = possibleColumns.filter { columnMirror(it, mirror) }
        val mirrorRows = possibleRows.filter { rowMirror(it, mirror) }

        return mirrorColumns to mirrorRows
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
        val mirrors = parseInput(list)
        return mirrors.mapIndexed { idx, mirror ->
            val initial = calc1(mirror)
            val newValues = mutableSetOf<Pair<List<Int>, List<Int>>>()
            for (i in mirror.indices) {
                for (j in mirror[0].indices) {
                    val save = mirror[i][j]
                    mirror[i][j] = if (mirror[i][j] == Cell.Mirror) Cell.Field else Cell.Mirror
                    val changed = calc1(mirror)
                    mirror[i][j] = save
                    if (changed != initial && changed != emptyList<Int>() to emptyList<Int>()) {
                        println("NEW: $initial -> $changed")
                        val f = if (changed.first == initial.first) 0 else changed.first
                        val s = if (changed.second == initial.second) 0 else changed.second

                        newValues.add(changed.first.minus(initial.first) to changed.second.minus(initial.second))
                    }
                }

            }

            require(newValues.size == 1) {
                "Oops for $idx"
            }


            return@mapIndexed (newValues.first().first.firstOrNull()
                ?: 0) + 100 * (newValues.first().second.firstOrNull() ?: 0)
        }.sum()
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
    checkResult(res2, 31836)
}
