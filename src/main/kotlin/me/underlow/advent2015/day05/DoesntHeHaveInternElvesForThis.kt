package me.underlow.advent2015.day05

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

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
        return 0
    }

}


fun main() {
    val input = getResourceAsLines("/2015/05 - DoesntHeHaveInternElvesForThis.txt")
    val res1 = DoesntHeHaveInternElvesForThis.part1(input)
    val res2 = DoesntHeHaveInternElvesForThis.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1) // 246
    println(res2)
}
