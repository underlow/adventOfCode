package me.underlow.advent2015

import me.underlow.advent2022.checkResult

object IWasToldThereWouldBeNoMath {

    data class Box(val l: Int, val w: Int, val h: Int)

    private fun Box.requiredPaper() = 2 * l * w + 2 * w * h + 2 * h * l + listOf(l * w, w * h, h * l).min()
    private fun Box.requiredRibbon() = 2 * listOf(l + w, w + h, h + l).min() + l * w * h

    fun part1(list: List<String>): Int = parseInput(list).sumOf { it.requiredPaper() }

    fun part2(list: List<String>): Int = parseInput(list).sumOf { it.requiredRibbon() }

    //19x24x29
    private fun parseInput(list: List<String>): List<Box> = list.map {
        val s = it.split("x")
        return@map Box(l = s[0].toInt(), w = s[1].toInt(), h = s[2].toInt())
    }
}


fun main() {
    val input = readInput("$pathPrefix/day02.txt")
    val res1 = IWasToldThereWouldBeNoMath.part1(input) //1959856 1466916
    val res2 = IWasToldThereWouldBeNoMath.part2(input)

    checkResult(res1, 1598415)
    checkResult(res2, 3812909)

    println(res1)
    println(res2)
}
