package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object LensLibrary {

    fun part1(list: List<String>): Int {
        val steps = parseInput(list)

        val s = steps.map { it.calc1() }

        return s.sum()
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<String> {
        return list.map { it.split(',') }.flatten()
    }
}

private fun String.calc1(): Int {
    var res = 0
    for (c in this.toCharArray()) {
        res += c.code
        res = res * 17
        res = res % 256
    }
    return res
}


fun main() {
    val input = readInput("$pathPrefix23/day15.txt")
    val res1 = LensLibrary.part1(input)
    val res2 = LensLibrary.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 503487)
    checkResult(res2, 0)
}
