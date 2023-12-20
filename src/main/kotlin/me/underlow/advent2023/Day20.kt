package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import java.util.*

object PulsePropagation {
    enum class PulseLevel { Low, High }
    enum class ModuleState { On, Off }
    data class Pulse(val level: PulseLevel, val destination: String, val source: String)
    interface Module {
        val destination: List<String>
        val name: String
        fun process(pulse: Pulse): List<Pulse>
    }

    data class EmptyModule(override val name: String) : Module {
        override val destination: List<String>
            get() = emptyList()

        override fun process(pulse: Pulse): List<Pulse> {
            return emptyList()
        }
    }

    data class Broadcaster(override val destination: List<String>) : Module {
        override val name: String
            get() = "broadcaster"

        override fun process(pulse: Pulse): List<Pulse> {
            return destination.map { Pulse(pulse.level, it, name) }
        }

    }

    data class FlipFlop(override val name: String, var state: ModuleState, override val destination: List<String>) :
        Module {
        override fun process(pulse: Pulse): List<Pulse> {
            if (pulse.level == PulseLevel.High)
                return emptyList()
            if (state == ModuleState.Off) {
                state = ModuleState.On
                return destination.map { Pulse(PulseLevel.High, it, name) }
            }
            if (state == ModuleState.On) {
                state = ModuleState.Off
                return destination.map { Pulse(PulseLevel.Low, it, name) }
            }
            error("oops")
        }
    }

    // todo: fill inputs to defaults
    data class Conjunction(
        override val name: String,
        val inputs: MutableMap<String, PulseLevel>,
        override val destination: List<String>
    ) : Module {
        override fun process(pulse: Pulse): List<Pulse> {
            inputs[pulse.source] = pulse.level

            if (inputs.values.all { it == PulseLevel.High }) {
                return destination.map { Pulse(PulseLevel.Low, it, name) }
            } else {
                return destination.map { Pulse(PulseLevel.High, it, name) }
            }
            error("oops")
        }
    }


    fun part1(list: List<String>): Int {
        val modules = parseInput(list)
        val broadcaster = modules.filterIsInstance<Broadcaster>().first()
        // fill conjunction
        val allConjunctionNodes = modules.filterIsInstance(Conjunction::class.java)
        for (allConjunctionNode in allConjunctionNodes) {
            val inputs = modules.filter { it.destination.contains(allConjunctionNode.name) }
            allConjunctionNode.inputs.putAll(inputs.map { it.name to PulseLevel.Low }.toMap())
        }
        var low = 0
        var high = 0

        for (i in 0 until 1000) {
            val pulseQueue = LinkedList<Pulse>()
            pulseQueue.add(Pulse(PulseLevel.Low, "broadcaster", "button"))

            low++

            while (pulseQueue.isNotEmpty()) {
                val p = pulseQueue.first
                pulseQueue.removeAt(0)
                val dest = modules.filter { it.name == p.destination }.firstOrNull()
                if (dest == null) {
                    continue //????
                }


                val process = dest.process(p)
                low += process.filter { it.level == PulseLevel.Low }.count()
                high += process.filter { it.level == PulseLevel.High }.count()
                pulseQueue.addAll(process)

            }
        }
        return low * high
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<Module> {
        return list.map { it.toModule() }
    }

    private fun String.toModule(): Module {
        if (this.startsWith("broadcaster")) {
            val dest = this.substring(this.indexOf('>') + 1).split(',').map { it.trim() }
            return PulsePropagation.Broadcaster(dest)
        }
        if (this.startsWith('%')) {
            val name = this.substring(1, this.indexOf('-')).trim()
            val dest = this.substring(this.indexOf('>') + 1).split(',').map { it.trim() }
            return FlipFlop(name, ModuleState.Off, dest)
        }
        if (this.startsWith('&')) {
            val name = this.substring(1, this.indexOf('-')).trim()
            val dest = this.substring(this.indexOf('>') + 1).split(',').map { it.trim() }
            return Conjunction(name, mutableMapOf(), dest)
        }
        error("Oops")
    }

}


fun main() {
    val input = readInput("${pathPrefix23}/day20.txt")
    val res1 = PulsePropagation.part1(input)
    val res2 = PulsePropagation.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 825896364)
    checkResult(res2, 0)
}
