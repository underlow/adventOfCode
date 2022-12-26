package me.underlow.advent2022.day03

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

// https://adventofcode.com/2022/day/3

fun String.findCommonLetter(): Char {
    val part1 = substring(0, length / 2)
    val part2 = substring(length / 2, length)

    val set1 = part1.toCharArray().toSet()
    val set2 = part2.toCharArray().toSet()

    return (set1.intersect(set2)).first()
}

fun Char.toPriorityValue(): Int {
    if (this in 'a'..'z')
        return this.code - 'a'.code + 1
    if (this in 'A'..'Z')
        return this.code - 'A'.code + 27

    error("Incorrect input")
}

fun calculatePriority(strings: List<String>): Int {
    val wrongItems = strings.map { it.findCommonLetter() }
    return wrongItems.sumOf { it.toPriorityValue() }

}
/*------------------------- Part 2 ---------------*/

fun List<String>.splitToGroup(): List<List<String>> {
    val ret = mutableListOf<List<String>>()
    for (i in 0 until this.size step 3) {
        ret += listOf(this[i], this[i + 1], this[i + 2])
    }
    return ret
}

fun List<String>.findCommon(): Char {
    val s1 = this[0].toCharArray().toSet()
    val s2 = this[1].toCharArray().toSet()
    val s3 = this[2].toCharArray().toSet()

    return s1.intersect(s2).intersect(s3).first()
}

fun calculate2part(list: List<String>): Int =
    list.splitToGroup().map { it.findCommon() }.sumOf { it.toPriorityValue() }

/*----------------------------------------*/

fun main() {
    val strings = getResourceAsLines("/03 - RucksackReorganizationInput.txt")
    val total = calculatePriority(strings)

    println(total)

    val total2 = calculate2part(strings)
    println(total2)

    checkResult(total, 7997)
    checkResult(total2, 2545)
}


