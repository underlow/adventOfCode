package me.underlow.advent2015.day03

import me.underlow.advent2022.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsString

//

object PerfectlySphericalHousesInAVacuum {

    fun part1(list: String): Int {
        var currentPoint = Point(0, 0)
        val visited = mutableSetOf<Point>().apply { add(currentPoint) }

        list.forEach {
            val np = when (it) {
                '>' -> currentPoint.copy(y = currentPoint.y + 1)
                '<' -> currentPoint.copy(y = currentPoint.y - 1)
                '^' -> currentPoint.copy(x = currentPoint.x + 1)
                'v' -> currentPoint.copy(x = currentPoint.x - 1)
                else -> error("Incorrect input")
            }
            currentPoint = np
            visited.add(currentPoint)
        }
        return visited.size
    }

    fun part2(list: String): Int {
        return 0
    }
}


fun main() {
    val input = getResourceAsString("/2015/03 - PerfectlySphericalHousesInAVacuum.txt")
    val res1 = PerfectlySphericalHousesInAVacuum.part1(input)
    val res2 = PerfectlySphericalHousesInAVacuum.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
