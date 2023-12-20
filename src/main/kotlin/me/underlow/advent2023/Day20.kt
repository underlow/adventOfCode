package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object PulsePropagation {
    enum class PulseLevel { Low, High }
    enum class ModuleState { On, Off }
    data class Pulse(val level: PulseLevel, val destination: String)
    interface Module {
        fun process(pulse: Pulse)
    }

    data class Broadcaster(val destination: MutableList<Module>) : Module {
        override fun process(pulse: Pulse) {
            TODO("Not yet implemented")
        }

    }

    data class FlipFlop(val name: String, var state: ModuleState, val destination: MutableList<Module>) : Module {
        override fun process(pulse: Pulse) {
            TODO("Not yet implemented")
        }
    }

    // todo: fill inputs to defaults
    data class Conjunction(
        val name: String,
        val inputs: MutableMap<String, PulseLevel>,
        val destination: MutableList<Module>
    )


    fun part1(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Any {
        return 0
    }
}


fun main() {
    val input = readInput("${pathPrefix23}3/day20.txt")
    val res1 = PulsePropagation.part1(input)
    val res2 = PulsePropagation.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 0)
    checkResult(res2, 0)
}
