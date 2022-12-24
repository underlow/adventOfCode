package me.underlow.advent2022.day24

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

//https://adventofcode.com/2022/day/24

object BlizzardBasin {

    fun part1(list: List<String>): Int {
        val lines = parseInput(list)
        val field = BlizzardField(lines)
        return field.findPath()
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.map { it.toCharArray().toTypedArray() }.toTypedArray()
    }

    fun part2(list: List<String>): Int {
        val lines = parseInput(list)
        val field = BlizzardField(lines)
        return field.findPath2part()
    }
}


fun main() {
    val input = getResourceAsLines("/24 - BlizzardBasin.txt")
    val res1 = BlizzardBasin.part1(input)
    val res2 = BlizzardBasin.part2(input)

    checkResult(res1, 274)
    checkResult(res2, 839)

    println(res1)
    println(res2)
}
