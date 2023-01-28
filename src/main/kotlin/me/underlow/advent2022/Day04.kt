package me.underlow.advent2022

// https://adventofcode.com/2022/day/4
object CampCleanupInput {
    // splits 9-35,36-90 to pair of (9,35) & (36,90)
    fun String.splitToRanges(): Pair<Range, Range> {
        val split = this.split(",")

        fun String.toRange(): Range {
            val split2 = this.split("-")
            return Range(split2[0].toInt(), split2[1].toInt())
        }

        return split[0].toRange() to split[1].toRange()
    }

    data class Range(val b: Int, val e: Int)

    fun contains(r1: Range, r2: Range): Boolean {
        return when {
            r1.b <= r2.b && r1.e >= r2.e -> true
            r2.b <= r1.b && r2.e >= r1.e -> true
            else -> false
        }
    }

    fun part1(input: List<String>): Int {
        val ranges = input.map { it.splitToRanges() }.map { contains(it.first, it.second) }
        return ranges.map { if (it) 1 else 0 }.sum()
    }

    /*------------------ part 2 ----------------*/
    fun overlaps(r1: Range, r2: Range): Boolean {
        return when {
            r1.e < r2.b -> false
            r2.e < r1.b -> false
            else -> true
        }
    }

    fun part2(input: List<String>): Int {
        val ranges = input.map { it.splitToRanges() }.map { overlaps(it.first, it.second) }
        return ranges.map { if (it) 1 else 0 }.sum()
    }
}
fun main() {
    val input = readInput("${pathPrefix}/day04.txt")

    val sum1 = CampCleanupInput.part1(input)
    val sum2 = CampCleanupInput.part2(input)

    println(sum1)
    println(sum2)

    checkResult(sum1, 305)
    checkResult(sum2, 811)
}
