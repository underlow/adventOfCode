package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object ParabolicReflectorDish {

    fun part1(list: List<String>): Int {
        val input = parseInput(list)

        north(input)

        return load(input)
    }

    // up
    fun north(input: Array<Array<Char>>) {
        for (column in input[0].indices) {
            for (row in input.indices) {
                if (input[row][column] == 'O') {
                    // move up
                    for (rowUp in row downTo 1) {
                        if (input[rowUp - 1][column] == '.') {
                            input[rowUp - 1][column] = 'O'
                            input[rowUp][column] = '.'
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }

    //down
    fun south(input: Array<Array<Char>>) {
        for (column in input[0].indices) {
            for (row in input.indices.reversed()) {
                if (input[row][column] == 'O') {
                    // move down
                    for (rowMove in row until input.size - 1) {
                        if (input[rowMove + 1][column] == '.') {
                            input[rowMove + 1][column] = 'O'
                            input[rowMove][column] = '.'
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }

    //left
    fun west(input: Array<Array<Char>>) {
        for (row in input.indices) {
            for (column in input[0].indices) {
                if (input[row][column] == 'O') {
                    // move left
                    for (colMove in column downTo 1) {
                        if (input[row][colMove - 1] == '.') {
                            input[row][colMove - 1] = 'O'
                            input[row][colMove] = '.'
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }

    // right
    fun east(input: Array<Array<Char>>) {
        for (row in input.indices) {
            for (column in input[0].indices.reversed()) {
                if (input[row][column] == 'O') {
                    // move right
                    for (colMove in column until input.size - 1) {
                        if (input[row][colMove + 1] == '.') {
                            input[row][colMove + 1] = 'O'
                            input[row][colMove] = '.'
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }

    fun part2(list: List<String>, count: Long = 1_000_000_000): Int {
        val input = parseInput(list)
        val results = mutableListOf<Int>()
        for (i in 0 until count) {
            north(input)
            west(input)
            south(input)
            east(input)

            val sum = load(input)
//            if (results.contains(sum))
//                println("$i -> $sum")

            results.add(sum)

            val c = findCycle(results)
            if (c != null && c.second > 1) {
                val cStart = c.first
                val cLen = c.second

                val rem = count % cLen
                val a = results[((count - cStart) % cLen).toInt() + cStart - 1]
                return a
            }

        }

        return load(input)
    }

    fun findCycle(results: List<Int>): Pair<Int, Int>? {
        // looking for a cycle filling list at least two times
        // lkfjerlkfj|aabbcc|aabbcc

        for (i in results.indices) {
            if ((results.size - i) % 2 != 0)
                continue

            var cycle = true
            for (j in 0 until (results.size - i) / 2) {
                if (results[j + i] != results[i + (results.size - i) / 2 + j])
                    cycle = false
            }
            if (cycle)
                return i to (results.size - i) / 2
        }

        return null
    }

    private fun load(input: Array<Array<Char>>): Int {
        var sum = 0
        for (row in input.indices) {
            for (column in input[0].indices) {
                if (input[row][column] == 'O') {
                    sum += input.size - row
                }
            }
        }

        return sum
    }

    fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.map {
            it.toCharArray().toTypedArray()
        }.toTypedArray()
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day14.txt")
    val res1 = ParabolicReflectorDish.part1(input)
    val res2 = ParabolicReflectorDish.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 112773)
    checkResult(res2, 98894)
}
