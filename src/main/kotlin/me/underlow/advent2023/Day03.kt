package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object GearRatios {
    data class Data(val numbers: List<Number>, val symbols: List<Symbol>)

    data class Symbol(val line: Int, val col: Int)
    data class Number(val value: Int, val line: Int, val colStart: Int, val colEnd: Int) {

        init {
            require(colEnd - colStart == value.toString().length - 1)
        }

        fun hasAdjacentSymbol(symbols: List<Symbol>): Boolean {
            val adjacentLinesSymbols = symbols.filter { it.line in this.line - 1..this.line + 1 }
            val adjacentSymbols = adjacentLinesSymbols.filter { it.col in this.colStart - 1..this.colEnd + 1 }
            return adjacentSymbols.isNotEmpty()
        }
    }


    fun part1(list: List<String>): Int {
        val (numbers, symbols) = parseInput(list)

        val filter = numbers.filter {
            it.hasAdjacentSymbol(symbols)
        }
        val nn = numbers.filter {
            !it.hasAdjacentSymbol(symbols)
        }

        val n = filter.sumOf { it.value }

        return n
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Data {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()

        for ((lineIdx, line) in list.withIndex()) {
            var nStart = -1
            var nEnd = -1

            for ((colIdx, col) in line.withIndex()) {
                // dot
                if (col == '.') {
                    // end number
                    if (nEnd == colIdx - 1 && nStart != -1) {
                        val num = line.substring(nStart, nEnd + 1).toInt()
                        numbers.add(GearRatios.Number(num, lineIdx, nStart, nEnd))
                        nStart = -1
                        nEnd = -1
                    }
                    continue
                }
                // symbol
                if (!col.isDigit()) {
                    symbols.add(Symbol(lineIdx, colIdx))
                    // end number
                    if (nEnd == colIdx - 1 && nStart != -1) {
                        val num = line.substring(nStart, nEnd + 1).toInt()
                        numbers.add(GearRatios.Number(num, lineIdx, nStart, nEnd))
                        nStart = -1
                        nEnd = -1
                    }
                    continue
                }
                // number
                if (nStart != -1) {
                    nEnd = colIdx
                    continue
                }

                nStart = colIdx
                nEnd = colIdx
            }
            // end of line
            if (nEnd == line.length - 1 && nStart != -1) {
                val num = line.substring(nStart, nEnd + 1).toInt()
                numbers.add(GearRatios.Number(num, lineIdx, nStart, nEnd))
                nStart = -1
                nEnd = -1
            }
        }

        return Data(numbers, symbols)
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day03.txt")
    val res1 = GearRatios.part1(input)
    val res2 = GearRatios.part2(input)

    checkResult(res1, 0) // !536667 too low
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
