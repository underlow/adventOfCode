package me.underlow.advent2022.day24

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

//https://adventofcode.com/2022/day/24

object BlizzardBasin {

    fun part1(list: List<String>): Long {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Any {
        TODO("Not yet implemented")
    }

    fun part2(list: List<String>): Long {
        val directions = parseInput(list)
        return 0
    }
}


fun main() {
    val input = getResourceAsLines("/")
    val res1 = BlizzardBasin.part1(input)
    val res2 = BlizzardBasin.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
