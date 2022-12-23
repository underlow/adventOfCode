package me.underlow.advent2022.day23

import me.underlow.advent2022.CircularProvider
import me.underlow.advent2022.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

// https://adventofcode.com/2022/day/23

object UnstableDiffusion {

    fun part1(list: List<String>): Int {
        val elfList = parseInput(list)
        val proposalProvider = CircularProvider(proposals)
        val container = ElfContainer(elfList, proposalProvider)

//        container.dump()
        for (i in 0 until 10){
            container.makeRound()
//            println("Move ${i + 1}")
//            container.dump()
        }

        return container.calculateEmptySpace()
    }

    private fun parseInput(list: List<String>): List<Elf> {
        var i = 'a'
        return list.mapIndexed { x, s ->
            s.mapIndexed { y, c ->
                when (c) {
                    '#' -> Elf("${i++}", Point(x, y))
                    else -> null
                }
            }.filterNotNull()
        }.flatten()
    }

    fun part2(list: List<String>): Int {
        println("part 2")
        val elfList = parseInput(list)
        val proposalProvider = CircularProvider(proposals)
        val container = ElfContainer(elfList, proposalProvider)

//        container.dump()
        var round = 1
        var start = System.currentTimeMillis()
        var end = System.currentTimeMillis()
        while (container.makeRound() != 0){
            if (round % 100 == 0) {
                end = System.currentTimeMillis()
                println("Round $round in [${end - start} ms]")
                start = System.currentTimeMillis()
            }
            round++
        }

        return round
    }
}

// vertical axis - X increasing down from 0
val proposals = listOf(
    Proposal("north", Point(-1, 0), check = setOf(Point(-1, -1), Point(-1, 0), Point(-1, 1))),
    Proposal("south", Point(1, 0), check = setOf(Point(1, -1), Point(1, 0), Point(1, 1))),
    Proposal("west", Point(0, -1), check = setOf(Point(1, -1), Point(0, -1), Point(-1, -1))),
    Proposal("east", Point(0, 1), check = setOf(Point(-1, 1), Point(0, 1), Point(1, 1))),
)

fun main() {
    val input = getResourceAsLines("/23 - UnstableDiffusion.txt")
    val res1 = UnstableDiffusion.part1(input)
    val res2 = UnstableDiffusion.part2(input)

    checkResult(res1, 4114)
    checkResult(res2, 970)

    println(res1)
    println(res2)
}

