package me.underlow.advent2015.day02

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

object IWasToldThereWouldBeNoMath {

    data class Box(val l: Int, val w: Int, val h: Int)

    private fun Box.requiredPaper() = 2 * l * w + 2 * w * h + 2 * h * l + listOf(l * w, w * h, h * l).min()

    fun part1(list: List<String>): Int {
        val boxes = parseInput(list)
        return boxes.sumOf {
            it.requiredPaper()
        }
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    //19x24x29
    private fun parseInput(list: List<String>): List<Box> = list.map {
        val s = it.split("x")
        return@map Box(l = s[0].toInt(), w = s[1].toInt(), h = s[2].toInt())
    }
}


fun main() {
    val input = getResourceAsLines("/2015/02 - IWasToldThereWouldBeNoMath.txt")
    val res1 = IWasToldThereWouldBeNoMath.part1(input) //1959856 1466916
    val res2 = IWasToldThereWouldBeNoMath.part2(input)

    checkResult(res1, 1598415)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
