package me.underlow.advent2023

import me.underlow.Dir
import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.findFirst
import me.underlow.parseToMap

object StepCounter {

    fun part1(list: List<String>, steps: Int): Int {
        val map = parseInput(list)
        val start = map.findFirst('S')

        val queue = mutableListOf<Point>().apply { add(start) }

        for (i in 0 until steps) {
            val newPosition = queue.map { p ->
                listOf(p.move(Dir.Up), p.move(Dir.Down), p.move(Dir.Left), p.move(Dir.Right))
            }.flatten()
                .filter { it.row in map.indices }
                .filter { it.col in map[0].indices }
                .filter { map[it.row][it.col] != '#' }
                .toSet()
            queue.clear()
            queue.addAll(newPosition)
        }

        return queue.size
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.parseToMap()
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day21.txt")
    val res1 = StepCounter.part1(input, 64)
    val res2 = StepCounter.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 3574)
    checkResult(res2, 0)
}
