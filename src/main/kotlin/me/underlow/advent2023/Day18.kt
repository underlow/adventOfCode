package me.underlow.advent2023

import me.underlow.Dir
import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object LavaductLagoon {

    data class Command(val dir: Dir, val steps: Long, val color: String)
    data class Hole(val point: Point, val color: String)

    fun part1(list: List<String>): Long {
        val commands = parseInput(list)

        return calc(commands)
    }

    private fun calc(commands: List<Command>): Long {
        val path = mutableSetOf<Point>()
        path.add(Point(0, 0))
        var current = Point(0, 0)
        for (command in commands) {
            for (i in 0 until command.steps) {
                current = current.move(command.dir)
                path.add(current)
            }
        }

//        require(path.last() == Point(0, 0))

        val top = path.map { it.row }.max()
        val bottom = path.map { it.row }.min()

        val left = path.map { it.col }.min()
        val right = path.map { it.col }.max()

        val width = right - left + 1
        val height = top - bottom + 1

        val queue = mutableSetOf<Point>()

//        val marked = path.toSet()
        val empty = mutableSetOf<Point>()

        for (i in bottom..top) {
            queue.add(Point(i, left))
            queue.add(Point(i, right))
        }
        for (i in left + 1..right + 1) {
            queue.add(Point(top, i))
            queue.add(Point(bottom, i))
        }

        while (queue.isNotEmpty()) {
            val p = queue.first()
            queue.remove(p)

            if (!(p.row in bottom..top && p.col in left..right))
                continue

            if (p in path || p in empty)
                continue

            empty += p

            queue.add(p.copy(row = p.row + 1))
            queue.add(p.copy(row = p.row - 1))
            queue.add(p.copy(col = p.col + 1))
            queue.add(p.copy(col = p.col - 1))

        }



        return width * height - empty.size.toLong()
    }

    fun part2(list: List<String>): Long {
        val commands = parseInput2(list)

        return calc(commands)
    }

    private fun parseInput(list: List<String>): List<Command> {
        return list.map {
            val s = it.split(" ")
            return@map Command(s[0].toDir(), s[1].toLong(), s[2])
        }
    }

    private fun parseInput2(list: List<String>): List<Command> {
        return list.map {
            val s = it.split(" ")
            val color = s[2].substring(1, s[2].length - 1)
            val distance = color.substring(1, 6).toLong(radix = 16)
            val dir = when (color.last()) {
                '0' -> Dir.Right
                '1' -> Dir.Down
                '2' -> Dir.Left
                '3' -> Dir.Up
                else -> {
                    error("oops")
                }
            }
            return@map Command(dir, distance, s[2])
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
