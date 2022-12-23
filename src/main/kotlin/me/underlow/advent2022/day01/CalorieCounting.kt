package me.underlow.advent2022.day01

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.day01.CalorieCounting.findMost3Calories
import me.underlow.advent2022.day01.CalorieCounting.findMostCalories
import me.underlow.advent2022.getResourceAsString

// https://adventofcode.com/2022/day/1

object CalorieCounting {
    fun findMostCalories(elfCaloriesList: List<List<Int>>): Int? {
        return elfCaloriesList.maxOfOrNull { it.sum() }
    }

    fun findMost3Calories(elfCaloriesList: List<List<Int>>): Int {
        val sorted = elfCaloriesList.map { it.sum() }.sorted()
        return sorted[sorted.size - 1] + sorted[sorted.size - 2] + sorted[sorted.size - 3]
    }

    fun parseList(bigList: String): List<List<Int>> {
        return bigList.split("\n\n").map { innerList ->
            innerList.split("\n").map { num ->
                num.toInt()
            }
        }
    }
}

fun main() {
    val elfCaloriesList = CalorieCounting.parseList(getResourceAsString("/01 - CalorieCounting.txt"))

    val res1 = findMostCalories(elfCaloriesList)
    checkResult(res1, 69501)

    val res2 = findMost3Calories(elfCaloriesList)
    checkResult(res2, 202346)

    println(res1)
    println(res2)
}

