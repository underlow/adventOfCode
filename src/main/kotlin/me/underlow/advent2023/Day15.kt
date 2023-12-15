package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object LensLibrary {

    fun part1(list: List<String>): Int {
        val steps = parseInput(list)

        val s = steps.map { it.calc1() }

        return s.sum()
    }

    data class Command(val prefix: String, val sign: Char, val n: Int?) {
        fun toCode() = Code(prefix, n!!)
    }

    data class Code(val prefix: String, val n: Int)

    fun String.toCode(): Command {
        val sign = if (this.indexOf("=") >= 0) '=' else '-'
        val split = this.split(sign)
        val prefix = split[0]
        val n = split[1].toIntOrNull()
        return Command(prefix, sign, n)
    }

    fun part2(list: List<String>): Int {
        val steps = parseInput(list)

        val hashMap = mutableMapOf<Int, MutableList<Code>>()

        for (step in steps) {
            val c = step.toCode()
            val hash = c.prefix.calc1()

            hashMap.putIfAbsent(hash, mutableListOf())

            if (c.sign == '=') {
                // If there is already a lens in the box with the same label
                if (hashMap[hash]!!.any { it.prefix == c.prefix }) {
                    val idx = hashMap[hash]!!.indexOfFirst { it.prefix == c.prefix }
                    hashMap[hash]!![idx] = c.toCode()
                } else {
                    hashMap[hash]!!.add(c.toCode())
                }
            } else {
                hashMap[hash]!!.removeIf { it.prefix == c.prefix }
            }
        }

        val res = hashMap.entries.map { (k, v) ->
            val mapIndexed = v.mapIndexed { idx, code -> (k + 1) * (idx + 1) * code.n }
            return@map mapIndexed.sum()
        }.sum()

        return res
    }

    private fun parseInput(list: List<String>): List<String> {
        return list.map { it.split(',') }.flatten()
    }
}

private fun String.calc1(): Int {
    var res = 0
    for (c in this.toCharArray()) {
        res += c.code
        res *= 17
        res %= 256
    }
    return res
}


fun main() {
    val input = readInput("$pathPrefix23/day15.txt")
    val res1 = LensLibrary.part1(input)
    val res2 = LensLibrary.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 503487)
    checkResult(res2, 261505)
}
