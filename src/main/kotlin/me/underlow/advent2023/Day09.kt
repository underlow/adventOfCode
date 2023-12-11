package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object MirageMaintenance {

    fun part1(list: List<String>): Int {
        val numbers = parseInput(list)

        val s = numbers.map { it.calc1() }.sum()

        return s
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<List<Int>> {
        return list.map { it.split(" ").map { it.trim().toInt() } }
    }
}

private fun List<Int>.calc1(): Int {
    val matrix = mutableListOf<MutableList<Int>>()
    matrix.add(this.toMutableList())

    var lastRow = 0
    while (!matrix[lastRow].all { it == 0 }) {
        val newRow = mutableListOf<Int>()
        val currentRow = matrix[lastRow]

        for (i in 0 until currentRow.size - 1) {
            newRow.add(currentRow[i + 1] - currentRow[i])
        }
        matrix.add(newRow)
        lastRow++
    }
    // roll back
    for (i in (matrix.size - 2) downTo 0) {
        val current = matrix[i + 1]
        val addTo = matrix[i]
        addTo.add(addTo.last() + current.last())
    }
    return matrix[0].last()
}


fun main() {
    val input = readInput("$pathPrefix23/day09.txt")
    val res1 = MirageMaintenance.part1(input)
    val res2 = MirageMaintenance.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 0)
    checkResult(res2, 0)
}
