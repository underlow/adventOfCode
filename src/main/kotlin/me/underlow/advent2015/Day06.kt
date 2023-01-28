package me.underlow.advent2015

import me.underlow.advent2022.checkResult
import java.io.File

object ProbablyAFireHazard {

    data class Square(val x1: Int, val y1: Int, val x2: Int, val y2: Int, val action: String)

    class Field {
        private val lights = Array(1000) { BooleanArray(1000) }

        fun turnOn(square: Square) {
            for (x in square.x1..square.x2) {
                for (y in square.y1..square.y2) {
                    lights[x][y] = true
                }
            }
        }

        fun turnOff(square: Square) {
            for (x in square.x1..square.x2) {
                for (y in square.y1..square.y2) {
                    lights[x][y] = false
                }
            }
        }

        fun toggle(square: Square) {
            for (x in square.x1..square.x2) {
                for (y in square.y1..square.y2) {
                    lights[x][y] = !lights[x][y]
                }
            }
        }

        fun countOn(): Int {
            return lights.sumOf { it.count { it } }
        }
    }


    class Field2 {
        private val lights = Array(1000) { Array(1000) { 0 } }

        fun turnOn(square: Square) {
            for (x in square.x1..square.x2) {
                for (y in square.y1..square.y2) {
                    lights[x][y] += 1
                }
            }
        }

        fun turnOff(square: Square) {
            for (x in square.x1..square.x2) {
                for (y in square.y1..square.y2) {
                    lights[x][y] = maxOf(0, lights[x][y] - 1)
                }
            }
        }

        fun toggle(square: Square) {
            for (x in square.x1..square.x2) {
                for (y in square.y1..square.y2) {
                    lights[x][y] +=  2
                }
            }
        }

        fun countOn(): Int {
            return lights.sumOf { it.sumOf { it } }
        }
    }

    fun part1(list: List<String>): Int {
        val commands = parseInput(list)
        val field = Field()
        commands.forEach {
            when (it.action) {
                "on" -> field.turnOn(it)
                "off" -> field.turnOff(it)
                "toggle" -> field.toggle(it)
            }
        }

        return field.countOn()
    }

    fun part2(list: List<String>): Int {
        val commands = parseInput(list)
        val field = Field2()
        commands.forEach {
            when (it.action) {
                "on" -> field.turnOn(it)
                "off" -> field.turnOff(it)
                "toggle" -> field.toggle(it)
            }
        }

        return field.countOn()
    }

    private fun parseInput(list: List<String>): List<Square> {
        //parse
        // turn on 222,12 through 856,241
        // turn off 209,780 through 572,894
        // toggle 50,472 through 452,788

        return list.map {
            val s = it.replace("through", ",").filter { it.isDigit() || it == ',' }

            val (x1, y1, x2, y2) = s.split(",")

            when {
                it.startsWith("turn on") -> Square(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt(), "on")
                it.startsWith("turn off") -> Square(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt(), "off")
                else -> Square(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt(), "toggle")
            }
        }
    }
}


fun main() {
    val input = readInput("$pathPrefix/day06.txt")
    val res1 = ProbablyAFireHazard.part1(input)
    val res2 = ProbablyAFireHazard.part2(input)

    checkResult(res1, 543903)
    checkResult(res2, 14687245)

    println(res1)
    println(res2)
}

fun readInput(filename: String) = File(filename).readLines()
fun readInputAsString(filename: String) = File(filename).readText().trim()
const val pathPrefix = "src/main/kotlin/me/underlow/advent2015"
