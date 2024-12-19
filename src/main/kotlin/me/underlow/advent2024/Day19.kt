package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object LinenLayout {

    val graph = mutableMapOf<Char, Node>()

    data class Node(val char: Char, val link: MutableSet<Char>, var terminal: Boolean)

    fun MutableMap<Char, Node>.add(string: String) {
        for (i in string.indices) {
            val from = string[i]
            val to = string.getOrNull(i + 1) ?: string[0]

            val fromNode = this[from] ?: Node(from, mutableSetOf(), false)
            val toNode = this[to] ?: Node(to, mutableSetOf(), false)

            fromNode.link.add(to)
            if (i == string.length - 1)
                toNode.terminal = true

        }
    }

    // brute force, too slow
    fun part1(list: List<String>): Int {
        val towels = parseInput(list)
        val r = towels.design.map {
            for (index in it.indices) {
                substrings[index] = it.substring(index)
            }
            cache.clear()
            val possible = isPossible(it, 0, towels.options)
            if (possible)
                println("Design $it is possible")
            else
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


    val cache = mutableSetOf<Int>()
    val substrings = mutableMapOf<Int, String>()
    private fun isPossible(design: String, position: Int, options: Map<Char, List<String>>): Boolean {
        if (position in cache)
            return true

        if (position > design.length - 1)
            return false

        if (position == design.length - 1)
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
                cache.add(position + it.length)
                return true
            }
        }
        return false

    }
}


fun main() {
    val input = readInput("$pathPrefix24/day19.txt")
    val res1 = LinenLayout.part1(input)
    val res2 = LinenLayout.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
