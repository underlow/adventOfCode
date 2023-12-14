package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object HotSprings {

    data class Records(val springs: String, val damaged: List<Int>) {

        private val memoize = mutableMapOf<Pair<Int, Int>, Long>()

        fun find(startSprings: kotlin.Int, startDamages: kotlin.Int): Long {
            if (memoize[startSprings to startDamages] != null)
                return memoize[startSprings to startDamages]!!

            if (startDamages == damaged.size) {
                if (springs.substring(startSprings, springs.length).all { it != '#' }) {
//                println("For $records found: $currentSpring")
                    memoize[startSprings to startDamages] = 1L
                    return 1
                } else {
//                println("For $records fail 1: $currentSpring")
                    memoize[startSprings to startDamages] = 0L
                    return 0
                }
            }
            if (startSprings == springs.length + 1) {
                memoize[startSprings to startDamages] = 0L
                return 0
            }

            var s = 0L
            // put damage on the next place
            val subSpring = springs.substring(startSprings, springs.length)
            val nextDamage = damaged[startDamages]

            if (nextDamage > subSpring.length) {
//            println("For $records fail 4: $currentSpring")
                memoize[startSprings to startDamages] = 0L
                return 0
            }


            if (subSpring.substring(0, nextDamage).all { it == '?' || it == '#' }
                && (nextDamage == subSpring.length)) {
                val newSprings = springs.substring(0, startSprings) +
                        String(CharArray(nextDamage) { '#' })
                s += find(startSprings + nextDamage, startDamages + 1)
                memoize[startSprings to startDamages] = s
                return s
            }

            if (subSpring.substring(0, nextDamage).all { it == '?' || it == '#' }
                && (subSpring[nextDamage] == '.' || subSpring[nextDamage] == '?')
            ) {
                s += find(startSprings + 1 + nextDamage, startDamages + 1)
            }
            // just move to next one
            if (springs[startSprings] != '#') {
                s += find(startSprings + 1, startDamages)
            }
            memoize[startSprings to startDamages] = s
            return s
        }
    }

    fun part2(list: List<String>): Long {
        val records = parseInput2(list)
        return records.sumOf { it.find(0, 0) }
    }

    private fun parseInput(list: List<String>): List<Records> {
        return list.map { s ->
            val split = s.split(" ")
            return@map Records(springs = split[0], damaged = split[1].trim().split(",").map { it.toInt() })
        }
    }

    private fun parseInput2(list: List<String>): List<Records> {
        return list.map { s ->
            val split = s.split(" ")
            val rm = (0..4).map { split[0] }.joinToString("?")
            val rd = (0..4).map { split[1] }.joinToString(",")
            return@map Records(springs = rm, damaged = rd.trim().split(",").map { it.toInt() })
        }
    }


    fun part1(list: List<String>): Long {
        val records = parseInput(list)
        val count = records.map { it.find(0, 0) }.sum()
        return count
    }

}


fun main() {
    val input = readInput("$pathPrefix23/day12.txt")
    val res1 = HotSprings.part1(input)
    println("part 1: $res1")
    checkResult(res1, 7379)

    val res2 = HotSprings.part2(input)
    println("part 2: $res2")
    checkResult(res2, 7732028747925)  //7737897507115 high
}
