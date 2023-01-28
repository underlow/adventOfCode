package me.underlow.advent2022.d_10

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.d_10.CathodeRayTubeInput.dumpArray
import me.underlow.advent2022.pathPrefix
import me.underlow.advent2022.readInput

object CathodeRayTubeInput {
    sealed interface Command

    object Noop : Command
    data class Addx(val i: Int) : Command

    fun parseInput(list: List<String>): List<Command> {
        return list.map {
            when (it) {
                "noop" -> return@map Noop
                // assume input always correct
                else -> {
                    val split = it.split(" ")
                    return@map Addx(split[1].toInt())
                }
            }
        }
    }

    fun processCommands(command: List<Command>): Int {
        var tick = 0
        var total = 0
        var register = 1

        val interestingTicks = setOf(20, 60, 100, 140, 180, 220)
        command.forEach { it ->
            when (it) {
                is Noop -> {
                    tick++
                    if (tick in interestingTicks)
                        total += tick * register
                }

                is Addx -> {
                    tick++
                    if (tick in interestingTicks)
                        total += tick * register
                    tick++
                    if (tick in interestingTicks)
                        total += tick * register
                    register += it.i
                }
            }
        }
        return total
    }

    fun processCommands2(command: List<Command>): Array<Boolean> {
        val result = Array<Boolean>(240) { false }
        var spritePosition = 0
        var register = 1

        fun check(spritePosition: Int, register: Int): Boolean =
            spritePosition % 40 == register || (spritePosition + 1) % 40 == register || (spritePosition - 1) % 40 == register

        command.forEach { it ->
            when (it) {
                is Noop -> {
                    result[spritePosition] = check(spritePosition, register)
                    spritePosition++
                }

                is Addx -> {
                    result[spritePosition] = check(spritePosition, register)
                    spritePosition++

                    result[spritePosition] = check(spritePosition, register)
                    spritePosition++

                    register += it.i
                }
            }
        }
        return result
    }

    fun solution1(list: List<String>): Int {
        val cmds = parseInput(list)
        val result = processCommands(cmds)
        return result
    }

    fun solution2(list: List<String>): Array<Boolean> {
        val cmds = parseInput(list)
        val result = processCommands2(cmds)
        return result
    }

    fun dumpArray(array: Array<Boolean>) {
        for (i in 0 until 6) {
            for (j in 0 until 40) {
                print(if (array[i * 40 + j]) "#" else ".")
            }
            println()
        }
    }
}

fun main() {
    val input = readInput("$pathPrefix/day10.txt")
    val result = CathodeRayTubeInput.solution1(input)
    val result2 = CathodeRayTubeInput.solution2(input)

    println(result)
    dumpArray(result2)

    checkResult(result, 14220)
}
