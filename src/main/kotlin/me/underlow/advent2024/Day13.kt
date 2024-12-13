package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object ClawContraption {
    data class ClawMachine(val xA: Int, val yA: Int, val xB: Int, val yB: Int, val priceX: Int, val priceY: Int)

    const val aButton = 3
    const val bButton = 1

    fun part1(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<ClawMachine> {
        val l = mutableListOf<ClawMachine>()

        for (i in 0 until list.size step 3) {
            val a = list[i]
            val b = list[i + 1]
            val p = list[i + 2]

            val xA = a.split(", ")[0].removePrefix("Button A: X+").toInt()
            val yA = a.split(", ")[1].removePrefix("Y+").toInt()
            val xB = b.split(", ")[0].removePrefix("Button B: X+").toInt()
            val yB = b.split(", ")[1].removePrefix("Y+").toInt()

            val xP = p.split(", ")[0].removePrefix("Prize: X=").toInt()
            val yP = p.split(", ")[1].removePrefix("Y=").toInt()

            l += ClawMachine(xA, yA, xB, yB, xP, yP)

        }
        return l

    }
}


fun main() {
    val input = readInput("$pathPrefix24/day13.txt")
    val res1 = ClawContraption.part1(input)
    val res2 = ClawContraption.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
