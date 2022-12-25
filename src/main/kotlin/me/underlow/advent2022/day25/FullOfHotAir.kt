package me.underlow.advent2022.day25

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.day24.AirNumber
import me.underlow.advent2022.getResourceAsLines

// https://adventofcode.com/2022/day/25

object FullOfHotAir {

    fun part1(list: List<String>): String {
        val directions = list.map { AirNumber.fromString(it) }

        val foldIndexed = directions.foldIndexed(AirNumber.fromString("0")) { idx, total, item ->
            total + item
        }
        return foldIndexed.to5thString()
    }

    fun part2(list: List<String>): Long {
        val directions = list.map { AirNumber.fromString(it) }
        return 0
    }
}

fun main() {
    val input = getResourceAsLines("/24 - FullOfHotAir.txt")
    val res1 = FullOfHotAir.part1(input)

    checkResult(res1, "2-0-0=1-0=2====20=-2")

    println(res1)
}

