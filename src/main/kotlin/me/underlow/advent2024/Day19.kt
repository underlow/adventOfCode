package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object LinenLayout {

    // brute force, too slow
    fun part1(list: List<String>): Int {
        val towels = parseInput(list)
        val r = towels.design.map {
            for (index in it.indices) {
                substrings[index] = it.substring(index)
            }
            cache.clear()
            result.clear()
            val possible = isPossible(it, 0, towels.options)
            if (possible) {
                println("Design $it is possible: ${result.reversed()}")
                if (result.reversed().joinToString("") != it) {
                    println("ERROR")
                }
            } else
                println("Design $it is NOT possible")
            possible
        }


        return r.count { it }
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    data class Towels(val options: Map<Char, List<String>>, val design: List<String>)

    private fun parseInput(list: List<String>): Towels {
        val options = list[0].split(',').map { it.trim() }.filter { it.isNotBlank() }.groupBy { it[0] }
        val design = list.subList(2, list.size)
        return Towels(options, design)
    }


    val cache = mutableMapOf<Int, Boolean>()
    val substrings = mutableMapOf<Int, String>()
    val result = mutableListOf<String>()
    private fun isPossible(design: String, position: Int, options: Map<Char, List<String>>): Boolean {
        if (position in cache)
            return cache[position]!!

        if (position > design.length)
            return false

        if (position == design.length)
            return true

        val firstChar = design[position]
        val oopp = options[firstChar]

        if (oopp.isNullOrEmpty())
            return false

        val filter = oopp.asSequence()
            .filter { substrings[position]!!.startsWith(it) }
            .filter { it.length + position <= design.length }
        filter.forEach {
            if (isPossible(design, position + it.length, options)) {
                cache[position] = true
                result.add(it)
                return true
            }
        }
        cache[position] = false

        return false

    }
}


fun main() {
    val input = readInput("$pathPrefix24/day19.txt")
    val res1 = LinenLayout.part1(input) //392 high
    val res2 = LinenLayout.part2(input)

    checkResult(res1, 306)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
