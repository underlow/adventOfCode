package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object HotSprings {

    data class Records(val springs: String, val damaged: List<Int>)

    fun part1(list: List<String>): Int {
        val records = parseInput(list)

        val count = records.map { find(0, 0, it, it.springs) }.sum()




        return count
    }

    private fun find(startSprings: Int, startDamages: Int, records: HotSprings.Records, currentSpring: String): Int {
        if (startDamages == records.damaged.size) {
            if (currentSpring.substring(startSprings, currentSpring.length).all { it != '#' }) {
                println("For $records found: $currentSpring")
                return 1
            } else
                return 0
        }
        if (startSprings == currentSpring.length + 1) {
//            if (!currentSpring.any { it == '?' }) {
//                println("For $records found: $currentSpring")
//                return 1
//            } else
            return 0
        }


        var s = 0
        // put damage on the next place
        val subSpring = currentSpring.substring(startSprings, currentSpring.length)
        val nextDamage = records.damaged[startDamages]

        if (nextDamage > subSpring.length)
            return 0


        if (subSpring.substring(0, nextDamage).all { it == '?' || it == '#' }
            && (nextDamage == subSpring.length)) {
            val newSprings = currentSpring.substring(0, startSprings) +
                    String(CharArray(nextDamage) { '#' })
            s += find(startSprings + nextDamage, startDamages + 1, records, newSprings)
            return s
        }

        if (subSpring.substring(0, nextDamage).all { it == '?' || it == '#' }
            && (subSpring[nextDamage] == '.' || subSpring[nextDamage] == '?')
        ) {
            val newSprings = currentSpring.substring(0, startSprings) +
                    String(CharArray(nextDamage) { '#' }) + '.' +
                    currentSpring.substring(startSprings + nextDamage + 1, currentSpring.length)
            s += find(startSprings + 1 + nextDamage, startDamages + 1, records, newSprings)
        }
        // just move to next one
        if (currentSpring[startSprings] != '#') {
            val currentSpring1 = currentSpring.substring(0, startSprings) +
                    '.' +
                    currentSpring.substring(startSprings + 1, currentSpring.length)
            s += find(startSprings + 1, startDamages, records, currentSpring1)
        }
        return s
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<HotSprings.Records> {
        return list.map { s ->
            val split = s.split(" ")
            return@map HotSprings.Records(springs = split[0], damaged = split[1].trim().split(",").map { it.toInt() })
        }
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day12.txt")
    val res1 = HotSprings.part1(input)
    val res2 = HotSprings.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 7379)
    checkResult(res2, 0)
}
