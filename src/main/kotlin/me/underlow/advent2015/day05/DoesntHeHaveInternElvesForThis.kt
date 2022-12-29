package me.underlow.advent2015.day05

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines
import kotlin.math.abs

object DoesntHeHaveInternElvesForThis {
    private val vowels = setOf('a', 'e', 'i', 'o', 'u')
    private val prohibited = setOf("ab", "cd", "pq", "xy")

    private fun String.containsTreeVowels(): Boolean =
        count { it in vowels } >= 3

    private fun String.containsDoubleLetter(): Boolean {
        for (i in 0 until this.length - 1) {
            if (this[i] == this[i + 1])
                return true
        }
        return false
    }

    private fun String.containsDoubleLetterWithBuffer(): Boolean {
        for (i in 0 until this.length - 2) {
            if (this[i] == this[i + 2])
                return true
        }
        return false
    }

    private fun String.containsDoubleDoubleLetter(): Boolean {

        val pairsMap = mutableMapOf<String, MutableList<Int>>()
        for (i in 0 until this.length - 1) {
            val pair = substring(i, i + 2)
            pairsMap.getOrPut(pair) { mutableListOf() }.add(i)
        }

        for (p in pairsMap.entries) {
            if (p.value.size > 1)
                for (i in p.value.indices) {
                    val i1 = p.value[i]
                    val i2 = p.value[0]
                    if (abs(i1 - i2) > 1)
                        return true
                }
        }

        return false
    }

    private fun String.doesNotContainProhibited() =
        prohibited.none {
            this.contains(it)
        }

    fun part1(list: List<String>): Int {
        return list.count {
            it.containsTreeVowels() &&
                    it.containsDoubleLetter() &&
                    it.doesNotContainProhibited()
        }
    }

    fun part2(list: List<String>): Int {
        return list.count {
            it.containsDoubleDoubleLetter() &&
                    it.containsDoubleLetterWithBuffer()
        }
    }

}


fun main() {
    val input = getResourceAsLines("/2015/05 - DoesntHeHaveInternElvesForThis.txt")
    val res1 = DoesntHeHaveInternElvesForThis.part1(input)
    val res2 = DoesntHeHaveInternElvesForThis.part2(input)

    checkResult(res1, 255)
    checkResult(res2, 55)

    println(res1)
    println(res2)
}
