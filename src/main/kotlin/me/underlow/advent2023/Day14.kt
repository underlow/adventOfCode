package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object ParabolicReflectorDish {

    fun part1(list: List<String>): Int {
        val input = parseInput(list)

        for (row in input.indices) {
            for (column in input[0].indices) {
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
        var sum = 0
        input.dump()
        for (row in input.indices) {
            for (column in input[0].indices) {
                if (input[row][column] == 'O') {
                    sum += input.size - row
                }
            }
        }

        return sum
    }

    fun Array<Array<Char>>.dump() {
        for (column in this[0].indices) {
            for (row in this.indices) {
                print(this[row][column])
            }
            println()
        }
    }


    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
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
    checkResult(res2, 0)
}
