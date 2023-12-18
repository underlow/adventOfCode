package me.underlow.advent2023

import me.underlow.Dir
import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object LavaductLagoon {

    data class Command(val dir: Dir, val steps: Int, val color: String)
    data class Hole(val point: Point, val color: String)

    fun part1(list: List<String>): Int {
        val commands = parseInput(list)

        val path = mutableListOf<Hole>()
        path.add(Hole(Point(0, 0), ""))
        var current = Point(0, 0)
        for (command in commands) {
            for (i in 0 until command.steps) {
                current = current.move(command.dir)
                path.add(Hole(current, command.color))
            }
        }

        require(path.last().point == Point(0, 0))

        val top = path.map { it.point.row }.max()
        val bottom = path.map { it.point.row }.min()

        val left = path.map { it.point.col }.min()
        val right = path.map { it.point.col }.max()

        val width = right - left + 1
        val height = top - bottom + 1

        val queue = mutableSetOf<Point>()

        for (i in bottom..top) {
            queue.add(Point(i, left))
            queue.add(Point(i, right))
        }
        for (i in left + 1..right + 1) {
            queue.add(Point(top, i))
            queue.add(Point(bottom, i))
        }

        val marked = path.map { it.point }.toMutableSet()
        val empty = mutableSetOf<Point>()
        while (queue.isNotEmpty()) {
            val p = queue.first()
            queue.remove(p)

            if (!(p.row in bottom..top && p.col in left..right))
                continue

            if (p in marked || p in empty)
                continue

            empty += p

            queue.add(p.copy(row = p.row + 1))
            queue.add(p.copy(row = p.row - 1))
            queue.add(p.copy(col = p.col + 1))
            queue.add(p.copy(col = p.col - 1))

        }



        return width * height - empty.size
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<Command> {
        return list.map {
            val s = it.split(" ")
            return@map Command(s[0].toDir(), s[1].toInt(), s[2])
        }
    }

    private fun String.toDir() = when (this) {
        "L" -> Dir.Left
        "U" -> Dir.Up
        "D" -> Dir.Down
        "R" -> Dir.Right
        else -> {
            error("ooops")
        }
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day18.txt")
    val res1 = LavaductLagoon.part1(input)
    val res2 = LavaductLagoon.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 35991)
    checkResult(res2, 0)
}
