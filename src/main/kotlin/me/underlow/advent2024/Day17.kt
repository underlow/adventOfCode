package me.underlow.advent2024

import me.underlow.advent2022.readInput
import java.lang.Math.pow
import kotlin.math.truncate

object ChronospatialComputer {

    var debug = true
    fun log(s: String) {
        if (debug)
            println(s)
    }

    data class Programm(val rA: Long, val rB: Long, val rC: Long, val instructions: List<Int>)

    fun part1(list: List<String>): String {
        val program = parseInput(list)

        return execute(program).joinToString(",")
    }

    private fun execute(program: Programm): List<Int> {
        val out = mutableListOf<Int>()

        var registerA = program.rA
        var registerB = program.rB
        var registerC = program.rC

        var pointer = 0L

        fun Int.comboOperand(): Long = when (this) {
            in 0..3 -> this.toLong()
            4 -> registerA
            5 -> registerB
            6 -> registerC
            else -> error("combo operand")
        }

        fun Int.literalOperand() = this.toLong()

        while (pointer < program.instructions.size) {
            val operand = program.instructions[pointer.toInt() + 1]
            when (program.instructions[pointer.toInt()]) {
                // adv
                0 -> {
                    val numerator = registerA
                    val denominator = pow(2.0, operand.comboOperand().toDouble()).toInt()
                    val result = truncate(numerator / denominator.toDouble()).toInt()
                    log("adv( >> 3): A:=$result")
                    registerA = result.toLong()
                    pointer += 2
                }
                //bxl
                1 -> {
                    val result = registerB xor operand.literalOperand()
                    registerB = result
                    log("bxl (xor): B:=$result")
                    pointer += 2
                }
                // bst
                2 -> {
                    registerB = operand.comboOperand() % 8
                    log("bst (last 3): B:=$registerB")
                    pointer += 2
                }
                //                jnz
                3 -> {
                    if (registerA != 0L) {
                        pointer = operand.literalOperand()
                        log("jnz: $pointer")
                        log("A: $registerA, B: $registerB, C: $registerC")
                    } else {
                        pointer += 2
                        log("jnz: nothing")
                    }
                }
                //bxc
                4 -> {
                    registerB = registerB xor registerC
                    log("bxc (xor): B:=$registerB")
                    pointer += 2
                }
                // out
                5 -> {
                    out += (operand.comboOperand() % 8L).toInt()
                    log("out: ${operand.comboOperand() % 8}")
                    pointer += 2
                }
                //bdv
                6 -> {
                    val numerator = registerA
                    val denominator = pow(2.0, operand.comboOperand().toDouble()).toLong()
                    val result = truncate(numerator / denominator.toDouble()).toLong()
                    registerB = result
                    log("bdv (>>3): B:=$registerB")
                    pointer += 2
                }
                //cvd
                7 -> {
                    val numerator = registerA
                    val denominator = pow(2.0, operand.comboOperand().toDouble()).toLong()
                    val result = truncate(numerator / denominator.toDouble()).toLong()
                    registerC = result
                    log("cvd (>>): C:=$registerC")
                    pointer += 2
                }

                else -> error("sjrhfer")
            }
        }

        return out
    }

    fun part2(list: List<String>): Long {
        println("PART2")
        val program = parseInput(list)

        var res = 0L
        for (firstNum in 0..7) {
            for (inst in program.instructions.indices.reversed()) {
                if (inst == program.instructions.size - 1)
                    continue
                for (num in 0..7) {
                    val num2 = 7
                    val execute = execute(program.copy(rA = (res shl 3) + num2))
                    if (program.instructions[inst] == execute.first()) {
                        if (num == 0 && inst == program.instructions.size - 1) {
                            continue
                        }
                        res = (res shl 3) + num
                        println("Found for $inst: ${program.instructions[inst]}, result: $res")
                        break
                    }
                }

            }

            // lets check

            val e = execute(program.copy(rA = res))
            if (e != program.instructions) {
                println("Not correct $res")
            } else {
                println("Great: $res")
            }
        }

        return res


        for (i in program.rA..program.rA) {
//            if (i % 1000000 == 0)
//                println("i == $i")
            val result = execute(program.copy(rA = i))
            if (result == program.instructions)
                return i
            println("$i: $result")
        }
        return -1
    }

    private fun parseInput(list: List<String>): Programm {
        val rA = list[0].removePrefix("Register A: ").toLong()
        val rB = list[1].removePrefix("Register B: ").toLong()
        val rC = list[2].removePrefix("Register C: ").toLong()

        val instructions = list[4].removePrefix("Program: ").split(",").map { it.trim().toInt() }

        return Programm(rA, rB, rC, instructions)
    }
}


fun main() {
    ChronospatialComputer.debug = false
    val input = readInput("$pathPrefix24/day17.txt")
//    val res1 = ChronospatialComputer.part1(input)
    val res2 = ChronospatialComputer.part2(input)

//    checkResult(res1, "6,7,5,2,1,3,5,1,7")
//    checkResult(res2, 0)

//    println(res1)
    println(res2)
}
