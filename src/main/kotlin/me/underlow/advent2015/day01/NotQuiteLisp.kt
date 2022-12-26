package me.underlow.advent2015.day01

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsString

object NotQuiteLisp {

    fun part1(list: String): Int = list.map { if (it == '(') 1 else -1 }.sum()

    fun part2(list: String): Int {
        var acc = 0
        for (c in list.toCharArray().indices) {
            acc += if (list[c] == '(') 1 else -1
            if (acc == -1)
                return c + 1
        }
        return 0
    }
}


fun main() {
    val input = getResourceAsString("/2015/01 - NotQuiteLisp.txt")
    val res1 = NotQuiteLisp.part1(input)
    val res2 = NotQuiteLisp.part2(input)

    checkResult(res1, 232)
    checkResult(res2, 1783)

    println(res1)
    println(res2)
}
