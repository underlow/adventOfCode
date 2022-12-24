package me.underlow.advent2022.day22

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

// https://adventofcode.com/2022/day/22

object MonkeyMap {

    fun part1(list: List<String>): Int {
        val input = list
            .subList(0, list.size - 1)
            .filter { it.isNotEmpty() }
            .map { it.toCharArray().toTypedArray() }.toTypedArray()

        val field = MonkeyField(input)
        val path = MonkeyPath(list.last().trim())
        val monkey = Monkey(field)
        val (x, y, facingCode) = monkey.followPath(path)
        return (x + 1) * 1000 + (y + 1) * 4 + facingCode
    }

    fun part2(list: List<String>): Int {
        val input = list
            .subList(0, list.size - 1)
            .filter { it.isNotEmpty() }
            .map { it.toCharArray().toTypedArray() }.toTypedArray()

        val field = MonkeyField(input)
        val path = MonkeyPath(list.last().trim())
        val monkey = Monkey(field, isCube = true)
        val (x, y, facingCode) = monkey.followPath(path)
        return (x + 1) * 1000 + (y + 1) * 4 + facingCode
    }
}


fun main() {
    val input = getResourceAsLines("/22 - MonkeyMap.txt")
    val res1 = MonkeyMap.part1(input)
//    val res2 = MonkeyMap.part2(input)

    checkResult(res1, 65368)
//    checkResult(res2, 0)

    println(res1)
//    println(res2)
}


