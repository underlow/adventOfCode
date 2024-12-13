package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import kotlin.math.max

object ClawContraption {
    data class ClawMachine(val xA: Int, val yA: Int, val xB: Int, val yB: Int, val xPrice: Long, val yPrice: Long) {
        fun calc(): Long {
            val presses = mutableListOf<Pair<Long, Long>>()


            val maxAPresses = max(xPrice / xA, yPrice / yA)
            val maxBPresses = max(xPrice / xB, yPrice / yB)
            for (a in 0 until maxAPresses) {
                for (b in 0 until maxBPresses) {
                    if (a * xA + b * xB == xPrice && a * yA + b * yB == yPrice) {
                        presses += a to b
                    }
                }
            }

            if (presses.isEmpty())
                return 0

            return presses.map { aButton * it.first + bButton * it.second }.min()
        }

        fun calc2(): Long {
            val a1 = xA
            val b1 = xB
            val c1 = xPrice
            val a2 = yA
            val b2 = yB
            val c2 = yPrice

            val y = (c2 * a1 - c1 * a2) / (b2 * a1 - b1 * a2)
            val x = (c1 - b1 * y) / a1


//            val y = (yPrice * xA - xPrice * xB) / (yB * xA - xB * yA)
//            val x = (xPrice - xB * y) / xA

            if (x * xA + y * xB == xPrice && y * yB + x * yA == yPrice) {
                return x * aButton + y
            }

            return 0L
        }

    }

    const val aButton = 3
    const val bButton = 1

    fun part1(list: List<String>): Long {
        val clawMachines = parseInput(list)

        // brute force
        val calc = clawMachines.map { it.calc2() }

        return calc.sum()
    }

    fun part2(list: List<String>): Long {
        val clawMachines = parseInput(list).map {
            it.copy(xPrice = it.xPrice + 10000000000000, yPrice = it.yPrice + 10000000000000)
        }

        // brute force
        val calc = clawMachines.map { it.calc2() }

        return calc.sum()
    }

    private fun parseInput(list: List<String>): List<ClawMachine> {
        val l = mutableListOf<ClawMachine>()

        for (i in list.indices step 4) {
            val a = list[i]
            val b = list[i + 1]
            val p = list[i + 2]

            val xA = a.split(", ")[0].removePrefix("Button A: X+").toInt()
            val yA = a.split(", ")[1].removePrefix("Y+").toInt()
            val xB = b.split(", ")[0].removePrefix("Button B: X+").toInt()
            val yB = b.split(", ")[1].removePrefix("Y+").toInt()

            val xP = p.split(", ")[0].removePrefix("Prize: X=").toInt()
            val yP = p.split(", ")[1].removePrefix("Y=").toInt()

            l += ClawMachine(xA, yA, xB, yB, xP.toLong(), yP.toLong())

        }
        return l

    }
}


fun main() {
    val input = readInput("$pathPrefix24/day13.txt")
    val res1 = ClawContraption.part1(input)
    val res2 = ClawContraption.part2(input)

    checkResult(res1, 27157)
    checkResult(res2, 104015411578548)

    println(res1)
    println(res2)
}
