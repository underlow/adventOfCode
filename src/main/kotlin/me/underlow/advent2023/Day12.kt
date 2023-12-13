package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object HotSprings {

    data class Records(val springs: String, val damaged: List<Int>)

    fun part1(list: List<String>): Long {
        val records = parseInput(list)
        val count = records.map { find(0, 0, it, it.springs) }.sum()
        return count
    }

    private fun find(startSprings: Int, startDamages: Int, records: Records, currentSpring: String): Long {
        if (startDamages == records.damaged.size) {
            if (currentSpring.substring(startSprings, currentSpring.length).all { it != '#' }) {
//                println("For $records found: $currentSpring")
                return 1
            } else {
//                println("For $records fail 1: $currentSpring")
                return 0
            }
        }
        if (startSprings == currentSpring.length + 1) {
//            if (!currentSpring.any { it == '?' }) {
//                println("For $records found: $currentSpring")
//                return 1
//            } else
//            println("For $records fail 2: $currentSpring")
            return 0
        }

//        if (
//            records.damaged.subList(startDamages, records.damaged.size).sum() +
//            records.damaged.size > records.springs.length - startSprings
//        ) {
//            println("For $records fail 3: $currentSpring")
//            return 0
//        }

        // optimize simple case
//        if (currentSpring.substring(startSprings, currentSpring.length).all { it == '?' }){
//            val all = records.damaged.subList(startDamages, records.damaged.size)
//            val allLen = all.sum() + all.size
//            val sLen = currentSpring.length - startSprings
//            val toLong = (allLen..(sLen + 1)).sum().toLong()
//            return toLong
//        }

        var s = 0L
        // put damage on the next place
        val subSpring = currentSpring.substring(startSprings, currentSpring.length)
        val nextDamage = records.damaged[startDamages]

        if (nextDamage > subSpring.length) {
//            println("For $records fail 4: $currentSpring")
            return 0
        }


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

    fun part2(list: List<String>): Long {
        val records = parseInput2(list)

        val count = records.mapIndexed { idx, record ->
            val find = p2(record)
//            val find = find(0, 0, record, record.springs)
            println("[$idx/${records.size}] ${record.springs} -> $find")
            find
        }.sum()




        return count
    }

    fun p2(record: Records): Long {
        // split to groups
        // ???.??? -> two groups
        val groups = record.springs.split(".").filter { it.isNotBlank() }

        val count = calc2(record.damaged, groups, 0, 0)

        return count

    }

    private fun calc2(damaged: List<Int>, groups: List<String>, startDamages: Int, startGroup: Int): Long {
        // fit everything we can into first group and multiply to all nested groups
        if (startDamages == damaged.size && groups.subList(startGroup, groups.size).any { it.contains("#") })
            return 0L

        if (startDamages == damaged.size)
            return 1L

        if (startGroup == groups.size && startDamages < damaged.size)
            return 0L


        var c = 0L
        for (g in startGroup until groups.size) {
            for (d in startDamages until damaged.size) {
                val records = Records(groups[g] + '.', damaged.subList(startDamages, d + 1))
                val pushDinG = find(0, 0, records, groups[g] + '.')
                if (pushDinG == 0L) {
                    // fail no chances
                    continue
                }
                c += pushDinG * calc2(damaged, groups, d + 1, g + 1)
            }
        }
        return c
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
}


fun main() {
    val input = readInput("$pathPrefix23/day12.txt")
    val res1 = HotSprings.part1(input)
    println("part 1: $res1")
    checkResult(res1, 7379)

    val res2 = HotSprings.part2(input)
    println("part 2: $res2")
    checkResult(res2, 0)
}
