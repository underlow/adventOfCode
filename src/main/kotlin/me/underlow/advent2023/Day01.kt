package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object Trebuchet {

    fun part1(list: List<String>): Int {
        return list.map { s ->
            val numbers = s.filter { it.isDigit() }
            val number = numbers.first().digitToInt() * 10 + numbers.last().digitToInt()
            return@map number
        }.sum()
    }

    fun part2(list: List<String>): Int {
        val words = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        return list.map { s ->
            var w = ""
            var i = 0
            while (i < s.length) {
                if (s[i].isDigit())
                    w += s[i]
                else
                    words.forEach { (word, num) ->
                        if (s.substring(i until s.length).startsWith(word))
                            w += num
                    }
                i++
            }

            return@map w
        }.map { s ->
            val numbers = s.filter { it.isDigit() }
            return@map numbers.first().digitToInt() * 10 + numbers.last().digitToInt()
        }.sum()
    }

    private fun parseInput(list: List<String>): Any {
        return 0
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day01.txt")
    val res1 = Trebuchet.part1(input)
    val res2 = Trebuchet.part2(input)

    checkResult(res1, 54916)
    checkResult(res2, 54728)

    println(res1)
    println(res2)
}

const val pathPrefix23 = "src/main/kotlin/me/underlow/advent2023"
