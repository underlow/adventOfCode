package me.underlow.advent2022.day14

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.day14.Reservoir.Point
import me.underlow.advent2022.getResourceAsLines
import kotlin.math.max
import kotlin.math.min

enum class RegolithContent(val c: Char) {
    Empty('.'),
    Rock('#'),
    Sand('o');
}

/**
 * parses lines with consecutive points into list of list of points
 * 498,4 -> 498,6 -> 496,6
 * 503,4 -> 502,4 -> 502,9 -> 494,9
 */
fun parseInput(list: List<String>): List<List<Point>> {
    return list.map { parseLine(it) }
}

private fun parseLine(it: String): List<Point> {
    val split = it.split(" -> ")
    return split.map { parsePoint(it) }
}

private fun parsePoint(it: String): Point {
    val split = it.split(",")
    return Point(split[0].toInt(), split[1].toInt())
}

fun solution1(list: List<String>): Int {
    val rocks = parseInput(list)
    val reservoir = fillReservoir(rocks)
    while (true) {
        if (!makeSandFall(reservoir)) {
            break
        }
    }
    val ret = reservoir.countSandInTheReservoir()
    return ret
}
fun solution2(list: List<String>): Int {
    val rocks = parseInput(list)
    val reservoir = fillReservoir(rocks)

    // find lowest y in reservoir
    val lowestY = reservoir.lowestY()
    // fill lowestY + 2  row with Rock
    for (i in 0 until reservoir.xSize) {
        reservoir[i, lowestY + 2] = RegolithContent.Rock
    }

    while (true) {
        if (!makeSandFall2(reservoir)) {
            break
        }
    }
    val ret = reservoir.countSandInTheReservoir()
    return ret
}


private fun Array<Array<RegolithContent>>.dump(x: IntRange, y: IntRange) {
    for (i in x) {
        for (j in y) {
            print(this[j][i].c)
        }
        println()
    }
}

private fun makeSandFall(reservoir: Reservoir): Boolean {
    reservoir.dump(494 until 504, 0 until 10)
    var sand = Point(500, 0)
    while (true) {
        val next = reservoir.findNextStep(sand)

        if (next.isOut) {
            println("out at ${next.next}")
            reservoir.dump(494 until 504, 0 until 10)
            return false
        }

        if (next.stop) {
            reservoir[next.next.x,next.next.y] = RegolithContent.Sand
            println("stop at ${next.next}")
            reservoir.dump(494 until 504, 0 until 10)
            return true
        }

        sand = next.next
    }
}

private fun makeSandFall2(reservoir: Reservoir): Boolean {
    reservoir.dump(494 until 504, 0 until 10)
    var sand = Point(500, 0)
    while (true) {
        val next = reservoir.findNextStep(sand)

        if (next.isOut) {
            println("out at ${next.next}")
            reservoir.dump(494 until 504, 0 until 10)
            return false
        }

        if (next.stop) {
            if (next.next == Point(500, 0)){
                reservoir[next.next.x,next.next.y] = RegolithContent.Sand
                println("fill all at ${next.next}")
                return false
            }
            reservoir[next.next.x,next.next.y] = RegolithContent.Sand
            println("stop at ${next.next}")
            reservoir.dump(494 until 504, 0 until 10)
            return true
        }

        sand = next.next
    }
}



private fun fillReservoir(rocks: List<List<Point>>): Reservoir {
    val result = Reservoir()

    rocks.forEach { pointsList ->
        for (i in 0 until pointsList.size - 1) {
            val f = pointsList[i]
            val t = pointsList[i + 1]
            when {
                f.x == t.x -> fillVerticalLine(result, f, t)
                f.y == t.y -> fillHorizontalLine(result, f, t)
            }
        }
    }
    return result
}

private fun fillHorizontalLine(result: Reservoir, f: Point, t: Point) {

    val x1 = min(f.x, t.x)
    val x2 = max(f.x, t.x)
    val y = f.y
    for (i in x1..x2) {
        result[i, y] = RegolithContent.Rock
    }
}

private fun fillVerticalLine(result: Reservoir, f: Point, t: Point) {
    val fromY = min(f.y, t.y)
    val toY = max(f.y, t.y)

    for (y in fromY..toY) {
        result[f.x, y] = RegolithContent.Rock
    }

}

fun main() {
    val input = getResourceAsLines("/14 - RegolithReservoirInput.txt")
    val solution1 = solution1(input)
    val solution2 = solution2(input)
    println("solution1: $solution1")
    println("solution1: $solution2")

    checkResult(solution1, 1016)
    checkResult(solution2, 25402)
}
