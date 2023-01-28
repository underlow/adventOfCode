package me.underlow.advent2022

// https://adventofcode.com/2022/day/25

object FullOfHotAir {

    fun part1(list: List<String>): String {
        val directions = list.map { AirNumber.fromString(it) }

        val foldIndexed = directions.foldIndexed(AirNumber.fromString("0")) { idx, total, item ->
            total + item
        }
        return foldIndexed.to5thString()
    }
}

fun main() {
    val input = readInput("${pathPrefix}/day25.txt")
    val res1 = FullOfHotAir.part1(input)

    checkResult(res1, "2-0-0=1-0=2====20=-2")

    println(res1)
}

