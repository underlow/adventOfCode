package me.underlow.advent2022.day20

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

// https://adventofcode.com/2022/day/20

object GrovePositioningSystem {

    fun solvePart1(list: List<String>): Long {
        val numbers = parseInput(list)
        val cList = CircularList(numbers)

        numbers.forEachIndexed { idx, data ->
            cList.moveNode(idx, data)
        }
        return extractAnswer(cList)
    }

    fun solvePart2(list: List<String>, decryptionKey: Int): Long {
        val numbers = parseInput(list).map { it * decryptionKey }
        val cList = CircularList(numbers)

        repeat(10) {
            numbers.forEachIndexed { idx, data ->
                cList.moveNode(idx, data)
            }
        }
        return extractAnswer(cList)
    }

    private fun extractAnswer(cList: CircularList): Long {
        val newListState = cList.toList(0)

        val i1000th = 1000 - (newListState.size) * (1000 / (newListState.size))
        val i2000th = 2000 - (newListState.size) * (2000 / (newListState.size))
        val i3000th = 3000 - (newListState.size) * (3000 / (newListState.size))
        return newListState[i1000th] + newListState[i2000th] + newListState[i3000th]
    }

    private fun parseInput(list: List<String>): List<Long> = list.map { it.toLong() }
}

fun main() {
    val input = getResourceAsLines("/20 - GrovePositioningSystem.txt")
    val res1 = GrovePositioningSystem.solvePart1(input)
    val res2 = GrovePositioningSystem.solvePart2(input, 811589153)

    checkResult(res1,  11123L)

    println(res1)
    println(res2)
}

