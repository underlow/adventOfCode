package me.underlow.advent2023

import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import java.math.BigInteger

object StepCounter {

    fun part1(list: List<String>, steps: Int): Int {
        val map = parseInput(list)
        val start = map.findFirst('S')

        val queue = mutableListOf<Point>().apply { add(start) }

        for (i in 0 until steps) {
            val newPosition = queue.map { p ->
                listOf(p.move(Dir.Up), p.move(Dir.Down), p.move(Dir.Left), p.move(Dir.Right))
            }.flatten()
                .filter { it.row in map.indices }
                .filter { it.col in map[0].indices }
                .filter { map[it.row][it.col] != '#' }
                .toSet()
            queue.clear()
            queue.addAll(newPosition)
        }

        return queue.size
    }

    fun calcOddsEvens(list: List<String>): Pair<Int, Int> {
        val map = parseInput(list)
        val start = map.findFirst('S')

        val arr = Array(map.size) { Array(map[0].size) { -1 } }
        var step = 0
        val queue = mutableListOf<Point>().apply { add(start) }

        while (queue.isNotEmpty()) {
            step = (step + 1) % 2
            val newPosition = queue.map { p ->
                listOf(p.move(Dir.Up), p.move(Dir.Down), p.move(Dir.Left), p.move(Dir.Right))
            }.flatten()
                .filter { it.row in map.indices }
                .filter { it.col in map[0].indices }
                .filter { map[it.row][it.col] != '#' }
                .filter { arr[it.row][it.col] != 1 }
                .filter { arr[it.row][it.col] != 2 }
                .toSet()
            queue.clear()
            queue.addAll(newPosition)

            newPosition.forEach {
                arr[it.row][it.col] = step
            }
        }

        var c = 0
        for (i in 0 until arr.size / 2) {
            for (j in 0 until arr[0].size / 2 - i) {
                if (arr[i][j] == 1)
                    c++
            }
        }
        println(c)
        c = 0
        for (i in arr.size / 2 until arr.size) {
            for (j in i until arr[0].size) {
                if (arr[i][j] == 1)
                    c++
            }
        }
        println(c)


        require(arr.countElements(0) + arr.countElements(1) + arr.countElements(-1) == arr.size * arr[0].size) {
            println("${arr.countElements(0) + arr.countElements(1) + arr.countElements(-1)} != ${arr.size * arr[0].size}")
        }

        return (arr.countElements(0) to arr.countElements(1))
    }

    fun part2(list: List<String>, steps: Int): BigInteger {
        val odds = calcOddsEvens(list)
        val interestingInOneSquare = if (steps % 2 == 0) odds.first else odds.second
        // how many squares we have:
        // works only for part2 input (26501365 == 65 + 131* 202 300)

        return interestingInOneSquare.toBigInteger() * (202300 * 2 + 1).toBigInteger() * (202300 * 2 + 1).toBigInteger()
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.parseToMap()
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day21.txt")
    val res1 = StepCounter.part1(input, 64)
    val res2 = StepCounter.part2(input, 26501365)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 3574)
    checkResult(res2, 0) // 1200917646058536 high 1_199_444_328_335_727 high

}
