package me.underlow.advent2022.day19

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.max

// https://adventofcode.com/2022/day/19

object NotEnoughMinerals {

    private data class Blueprint(
        val id: Int,
        val ore: OrePrice,
        val clay: ClayPrice,
        val obsidian: ObsidianPrice,
        val geode: GeodePrice,
    ) {
        fun maxOre() = max(ore.value, max(clay.value, max(obsidian.ore, geode.ore)))
        fun maxClay() = obsidian.clay
        fun maxObsidian() = geode.obsidian
    }

    @JvmInline
    private value class OrePrice(val value: Int)

    @JvmInline
    private value class ClayPrice(val value: Int)

    private data class ObsidianPrice(
        val ore: Int,
        val clay: Int,
    )

    private data class GeodePrice(
        val ore: Int, val obsidian: Int
    )


    private data class State(
        val step: Int,
        val oreRobots: Int,
        val clayRobots: Int,
        val obsidianRobots: Int,
        val geodeRobots: Int,
        val ore: Int,
        val clay: Int,
        val obsidian: Int,
        val geode: Int,
    ) {
        fun hasEnoughForGeode(blueprint: Blueprint): Boolean =
            blueprint.geode.ore <= ore && blueprint.geode.obsidian <= obsidian

        fun hasEnoughForObsidian(blueprint: Blueprint): Boolean =
            blueprint.obsidian.ore <= ore && blueprint.obsidian.clay <= clay

        fun hasEnoughForClay(blueprint: Blueprint): Boolean = blueprint.clay.value <= ore

        fun hasEnoughForOre(blueprint: Blueprint): Boolean = blueprint.ore.value <= ore

    }

    private val initialState = State(0, 1, 0, 0, 0, 0, 0, 0, 0)

    private fun Blueprint.craftNewRobots(state: State, totalSteps: Int, currentMax: Int): Set<State> {
        // do not craft robots on the last step (state.step will be incremented in the end of this step)
        if (totalSteps - 1 == state.step)
            return setOf(state)

        // stop if we can't beat current best solution
        val maxCurrentGeode = state.geodeRobots * (totalSteps - state.step + 1) + state.geode
        val maxPossibleGeodeByNewRobots = (1..totalSteps - state.step + 1).sum()
        if (maxPossibleGeodeByNewRobots + maxCurrentGeode <= currentMax) {
            return setOf()
        }


        // craft new robots
        val newStateGeode = if (state.hasEnoughForGeode(this)) {
            state.copy(
                geodeRobots = state.geodeRobots + 1,
                ore = state.ore - this.geode.ore,
                obsidian = state.obsidian - this.geode.obsidian,
            )
        } else state

        // we have more obsidian than can spend
        fun notEnoughObsidian() =
            (totalSteps - state.step + 1) * state.obsidianRobots + state.obsidian < this.maxObsidian() * (totalSteps - state.step + 1)

        val newStateObsidian =
            if (notEnoughObsidian() &&
                state.hasEnoughForObsidian(this) &&
                state.obsidianRobots <= this.maxObsidian()
            ) {
                state.copy(
                    obsidianRobots = state.obsidianRobots + 1,
                    ore = state.ore - this.obsidian.ore,
                    clay = state.clay - this.obsidian.clay,
                )
            } else state

        // we have more clay than can spend
        fun notEnoughClay() =
            (totalSteps - state.step + 1) * state.clayRobots + state.clay < this.maxClay() * (totalSteps - state.step + 1)

        val newStateClay =
            if (notEnoughClay() &&
                state.hasEnoughForClay(this) &&
                state.clayRobots < this.maxClay()
            ) {
                state.copy(
                    clayRobots = state.clayRobots + 1,
                    ore = state.ore - this.clay.value,
                )
            } else state

        fun notEnoughOre() =
            (totalSteps - state.step + 1) * state.oreRobots + state.ore < this.maxOre() * (totalSteps - state.step + 1)

        val newStateOre =
            if (notEnoughOre() &&
                state.hasEnoughForOre(this) &&
                state.oreRobots < this.maxOre()
            ) {
                state.copy(
                    oreRobots = state.oreRobots + 1,
                    ore = state.ore - this.ore.value,
                )
            } else state

        return setOf(newStateGeode, newStateObsidian, newStateClay, newStateOre, state)
    }

    private fun Blueprint.makeRound(state: State, totalSteps: Int, currentMax: Int): List<State> =
        craftNewRobots(state, totalSteps, currentMax).map { newRobotsState ->
            state.copy(
                step = state.step + 1,
                ore = newRobotsState.ore + state.oreRobots,
                clay = newRobotsState.clay + state.clayRobots,
                obsidian = newRobotsState.obsidian + state.obsidianRobots,
                geode = newRobotsState.geode + state.geodeRobots,
                oreRobots = newRobotsState.oreRobots,
                clayRobots = newRobotsState.clayRobots,
                obsidianRobots = newRobotsState.obsidianRobots,
                geodeRobots = newRobotsState.geodeRobots
            )

        }

    fun part1(list: List<String>): Long {
        val blueprints = parseInput(list)
        var totalTime = 0L
        val newStates = blueprints.map {
            val start = System.currentTimeMillis()
            val maxGeode = bruteForceStates(mutableListOf(initialState), it, 24, 0)
            val end = System.currentTimeMillis()
            totalTime += end - start
            println("[${end - start} ms] Max value for Blueprint #${it.id} is $maxGeode")
            maxGeode * it.id
        }

        val res = newStates.sum().toLong()
        println("[$totalTime ms] total answer is $res")
        return res
    }

    private fun bruteForceStates(
        queue: MutableList<State>,
        blueprint: Blueprint,
        totalSteps: Int,
        accInit: Int
    ): Int {
        var acc = accInit
        while (queue.isNotEmpty()) {
            val processingState = queue.removeAt(queue.size - 1)
            val newStates = blueprint.makeRound(processingState, totalSteps, acc)

            for (newState in newStates) {
                if (newState.step >= totalSteps) {
                    acc = max(newState.geode, acc)
                } else {
                    queue.add(newState)
                }
            }
        }
        return acc
    }

    private fun parseInput(list: List<String>): List<Blueprint> {
        return list.map {
            val p: Pattern = Pattern.compile("(\\d+)")
            val m: Matcher = p.matcher(it)
            val matches = mutableListOf<Int>()
            while (m.find()) {
                matches.add(m.group().toInt())
            }

            val id = matches[0]
            val ore = matches[1]
            val clay = matches[2]
            val obsidianOre = matches[3]
            val obsidianClay = matches[4]
            val geodeOre = matches[5]
            val geodeObsidian = matches[6]
            Blueprint(
                id,
                OrePrice(ore),
                ClayPrice(clay),
                ObsidianPrice(obsidianOre, obsidianClay),
                GeodePrice(geodeOre, geodeObsidian)
            )
        }
    }

    fun part2(list: List<String>): Long {
        val blueprints = parseInput(list)
        val newStates = blueprints.take(3).map {
            val start = System.currentTimeMillis()
            val maxGeode = bruteForceStates(mutableListOf(initialState), it, 32, 1)
            val end = System.currentTimeMillis()
            println("[${end - start} ms] Max value for Blueprint #${it.id} is $maxGeode")
            maxGeode
        }

        return newStates.reduce { accumulator, element -> accumulator * element }.toLong()
    }
}

fun main() {
    val input = getResourceAsLines("/19 - NotEnoughMineralsInput.txt")
    val res1 = NotEnoughMinerals.part1(input)
    val res2 = NotEnoughMinerals.part2(input)

    checkResult(res1, 1115)
    checkResult(res2, 25056)

    println(res1)
    println(res2)
}
