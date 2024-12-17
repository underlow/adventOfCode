package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import java.lang.Math.pow
import kotlin.math.truncate

object ChronospatialComputer {

    data class Programm(val rA: Int, val rB: Int, val rC: Int, val instructions: List<Int>)

    fun part1(list: List<String>): String {
        var program = parseInput(list)

        var registerA = program.rA
        var registerB = program.rB
        var registerC = program.rC

        val out = mutableListOf<Int>()

        var pointer = 0

        fun Int.comboOperand() = when (this) {
            in 0..3 -> this
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> error("combo operand")
        }

        fun Int.literalOperand() = this

        while (pointer < program.instructions.size) {
            val operand = program.instructions[pointer + 1]
            when (program.instructions[pointer]) {
                // adv
                0 -> {
                    val numerator = registerA
                    val denominator = pow(2.0, operand.comboOperand().toDouble()).toInt()
                    val result = truncate(numerator / denominator.toDouble()).toInt()
                    registerA = result
                    pointer += 2
                }
                //bxl
                1 -> {
                    val result = registerB xor operand.literalOperand()
                    registerB = result
                    pointer += 2
                }
                // bst
                2 -> {
                    registerB = operand.comboOperand() % 8
                    pointer += 2
                }
//                jnz
                3 -> {
                    if (registerA != 0) {
                        pointer = operand.literalOperand()
                    } else {
                        pointer += 2
                    }
                }
                //bxc
                4 -> {
                    registerB = registerB xor registerC
                    pointer += 2
                }
                // out
                5 -> {
                    out += operand.comboOperand() % 8
                    pointer += 2
                }
                //bdv
                6 -> {
                    val numerator = registerA
                    val denominator = pow(2.0, operand.comboOperand().toDouble()).toInt()
                    val result = truncate(numerator / denominator.toDouble()).toInt()
                    registerB = result
                    pointer += 2
                }
                //bdc
                7 -> {
                    val numerator = registerA
                    val denominator = pow(2.0, operand.comboOperand().toDouble()).toInt()
                    val result = truncate(numerator / denominator.toDouble()).toInt()
                    registerC = result
                    pointer += 2
                }

                else -> error("sjrhfer")
            }
        }

        return out.joinToString(",")
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Programm {
        val rA = list[0].removePrefix("Register A: ").toInt()
        val rB = list[1].removePrefix("Register B: ").toInt()
        val rC = list[2].removePrefix("Register C: ").toInt()

        val instructions = list[4].removePrefix("Program: ").split(",").map { it.trim().toInt() }

        return Programm(rA, rB, rC, instructions)
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day17.txt")
    val res1 = ChronospatialComputer.part1(input)
    val res2 = ChronospatialComputer.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
