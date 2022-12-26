package me.underlow.advent2022.day17

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.d_17.*
import me.underlow.advent2022.getResourceAsString


// https://adventofcode.com/2022/day/17

data class FlowPoint(val x: Int, val y: Long) {
    fun down() = FlowPoint(x, y - 1)
    fun left() = FlowPoint(x - 1, y)
    fun right() = FlowPoint(x + 1, y)

    operator fun plus(other: FlowPoint) = FlowPoint(x + other.x, y + other.y)
}

private enum class Direction {
    LEFT, RIGHT
}

private fun parseInput(list: String): List<Direction> {
    return list.map {
        when (it) {
            '<' -> Direction.LEFT
            '>' -> Direction.RIGHT
            else -> error("Invalid direction $it")
        }
    }
}

private fun makeSteps(count: Long, directions: List<Direction>): Long {
    val field = PyroclasticFlowField()
    val shapesProvider = CircularProvider(listOf(minusShape, plusShape, lShape, verticalMinusShape, squareShape))
    val hotGasProvider = CircularProvider(directions)

    val heights = mutableListOf<Long>()

    for (i in 0 until 10000L) {
        var shape = shapesProvider.nextShape()
        field.addRowsForNextStep(requiredHeight = field.currentTowerHeight + shape.height + PyroclasticFlowField.fieldHeightForANewShape)
        var nextShape = shape.moveTo(FlowPoint(2, field.height - 1))

        while (field.canPlaceShape(nextShape)) {
            shape = nextShape
            val possibleMove = when (hotGasProvider.nextShape()) {
                Direction.LEFT -> nextShape.left()
                Direction.RIGHT -> nextShape.right()
            }
            if (field.canPlaceShape(possibleMove)) {
                shape = possibleMove
            }
            nextShape = shape.down()
        }

        field.placeShape(shape)
        heights.add(field.currentTowerHeight)

    }

    val heightsDiff = heights.zipWithNext().map { it.second - it.first }

    return tryFindPattern(heightsDiff, heights, count)
}

private fun tryFindPattern(list: List<Number>, heights: List<Long>, rounds: Long): Long {
    var startingPoint = 0
    var patternLength = 15

    while (startingPoint + patternLength * 3 < list.size) {
        val pattern = list.subList(startingPoint, startingPoint + patternLength)
        val nextPattern = list.subList(startingPoint + patternLength, startingPoint + patternLength * 2)
        if (pattern == nextPattern) {
            println("Found pattern $pattern at $startingPoint, length $patternLength")

            val patternCount = (rounds - (startingPoint - 1)) / patternLength
            val patternRemainder = (rounds - (startingPoint - 1)) % patternLength

            heights[startingPoint - 1]
            val heightPattern = pattern.sumOf { it.toLong() }
            val heightReminder = heights[(startingPoint - 1 + patternRemainder - 1).toInt()]

            return heightPattern * patternCount + heightReminder
        }
        if (startingPoint + (patternLength + 1) * 3 >= list.size) {
            startingPoint++
            patternLength = 15
        } else {
            patternLength++
        }
    }
    error("No pattern found")


}

fun solvePart1(list: String, rounds: Long): Long {
    val directions = parseInput(list)
    return makeSteps(rounds, directions)
}

fun main() {
    val input = getResourceAsString("/17 - PyroclasticFlowInput.txt").trim()
    val res1 = solvePart1(input, 2022)
    val res2 = solvePart1(input, 1000000000000L)

    println(res1)
    println(res2)

    checkResult(res1, 3124)
    checkResult(res2, 1561176470569L)
}
