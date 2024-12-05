import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24
import kotlin.math.abs

object RedNosedReports {

    fun part1(list: List<String>): Int {
        val levels = parseInput(list)

        return levels.count { it.isSafe() }
    }

    fun part2(list: List<String>): Int {
        val levels = parseInput(list)

        return levels.count { it.isErrorSafe() || it.isSafe() }
    }

    private fun parseInput(list: List<String>): List<List<Int>> {
        return list.map { it.split(" ").map { it.toInt() } }
    }
}

private fun List<Int>.isSafe(): Boolean {
    val dir = if (this[0] > this[1]) 1 else -1; // -1 down +1 up
    for (i in 0..this.size - 2) {
        if (this[i] == this[i + 1])
            return false
        if (abs(this[i] - this[i + 1]) > 3)
            return false

        val nDir = if (this[i] > this[i + 1]) 1 else -1
        if (nDir != dir)
            return false
    }
    return true
}

private fun List<Int>.isSkipSafe(idx: Int): Boolean {
    val arr = this.map { it }.filterIndexed { index, i -> index != idx }
    return arr.isSafe()
}

private fun List<Int>.isErrorSafe(): Boolean {
    val any = (0..this.size).map { this.isSkipSafe(it) }.any { it }
    println("$this: is error safe: $any ")
    return any
}


fun main() {
    val input = readInput("$pathPrefix24/day02.txt")
    val res1 = RedNosedReports.part1(input)
    val res2 = RedNosedReports.part2(input)

    checkResult(res1, 282)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
