package me.underlow.advent2024

import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.parseToMap

object KeypadConundrum {

    private val numericKeypad = """
        789
        456
        123
        #0A
    """.trimIndent().split("\n").parseToMap()

    private val directionalKeypad = """
        #^A
        <v>
    """.trimIndent().split("\n").parseToMap()

    // now let's find and store all shortest paths on directionalKeypad from one button to another on numericKeypad
    val directionalToNumeric: MutableMap<Pair<Point, Point>, List<List<Char>>> = findAllDirectionalToNumeric(
        directionalKeypad,
        numericKeypad
    )

    fun findAllDirectionalToNumeric(
        directional: Array<Array<Char>>,
        numeric: Array<Array<Char>>
    ): MutableMap<Pair<Point, Point>, List<List<Char>>> {
        for (i in numeric.indices) {
            for (j in numeric[0].indices) {
                if (i == j) continue
                // for each coord pair


            }
        }
        return mutableMapOf()
    }


    fun part1(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Any {
        return 0
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day21.txt")
    val res1 = KeypadConundrum.part1(input)
    val res2 = KeypadConundrum.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
