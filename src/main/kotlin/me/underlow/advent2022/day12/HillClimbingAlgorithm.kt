package me.underlow.advent2022.day12

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.dump
import me.underlow.advent2022.getResourceAsLines

// https://adventofcode.com/2022/day/12

data class Cell(
    val height: Char,
    var steps: Int = Int.MAX_VALUE,
    var stopProcessing: Boolean = false,
    val point: Point
)

enum class Point {
    Start,
    Finish,
    Path
}

data class Coord(val i: Int, val j: Int)

fun parseInput(list: List<String>): Array<Array<Cell>> {
    val result = Array(list.size) { Array(list[0].length) { Cell(' ', point = Point.Path) } }

    for (i in list.indices) {
        for (j in list[i].indices) {
            val point = when (list[i][j]) {
                'S' -> Point.Start
                'E' -> Point.Finish
                else -> Point.Path
            }
            val steps = if (point == Point.Start) 0 else Int.MAX_VALUE
            val height = when (point) {
                Point.Start -> 'a'
                Point.Finish -> 'z'
                else -> list[i][j]
            }
            result[i][j] = Cell(height, steps = steps, point = point)
        }
    }
    return result
}

fun findPath(cells: Array<Array<Cell>>): Int {
    var start = Coord(0, 0)
    var finish = Coord(0, 0)

    for (i in cells.indices)
        for (j in cells[0].indices) {
            when (cells[i][j].point) {
                Point.Start -> start = Coord(i, j)
                Point.Finish -> finish = Coord(i, j)
                else -> continue
            }
        }

    processCell(cells, start.i, start.j)
    cells.dump() { cell -> "[${cell.point}:${cell.height.code}:${cell.steps}]" }
    return cells[finish.i][finish.j].steps
}

fun processCell(cells: Array<Array<Cell>>, i: Int, j: Int) {
    fun canMakeStep(fi: Int, fj: Int, ti: Int, tj: Int): Boolean {
        val iInBounds = ti in (0 until cells.size)
        val jInBounds = tj in (0 until cells[0].size)

        if (!(iInBounds && jInBounds))
            return false

        val fChar = cells[fi][fj]
        val tChar = cells[ti][tj]
        val minimizeSteps = fChar.steps + 1 < tChar.steps
        val fCode = fChar.height.code
        val tCode = tChar.height.code - 1
        val canClimb = fCode >= tCode
        return minimizeSteps && canClimb
    }

    if (canMakeStep(i, j, i, j - 1)) {
        cells[i][j - 1].steps = cells[i][j].steps + 1
        processCell(cells, i, j - 1)
    }
    if (canMakeStep(i, j, i, j + 1)) {
        cells[i][j + 1].steps = cells[i][j].steps + 1
        processCell(cells, i, j + 1)
    }
    if (canMakeStep(i, j, i - 1, j)) {
        cells[i - 1][j].steps = cells[i][j].steps + 1
        processCell(cells, i - 1, j)
    }
    if (canMakeStep(i, j, i + 1, j)) {
        cells[i + 1][j].steps = cells[i][j].steps + 1
        processCell(cells, i + 1, j)
    }

}

fun solution1(list: List<String>): Int {
    val input = parseInput(list)
    val result = findPath(input)
    return result
}

fun solution2(list: List<String>): Int {
    val cells = parseInput(list)

    val options = mutableListOf<Array<Array<Cell>>>()

    var min = Int.MAX_VALUE

    var start = Coord(0, 0)
    var finish = Coord(0, 0)

    for (i in cells.indices)
        for (j in cells[0].indices) {
            when (cells[i][j].point) {
                Point.Start -> start = Coord(i, j)
                Point.Finish -> finish = Coord(i, j)
                else -> continue
            }
        }


    for (i in 0 until cells.size) {
        for (j in 0 until cells[0].size) {
            if (cells[i][j].height == 'a') {
                val ar = parseInput(list)
                ar[start.i][start.j] = ar[start.i][start.j].copy(point = Point.Path)
                ar[i][j] = ar[i][j].copy(point = Point.Start, steps = 0)
                processCell(ar, i, j)
                println("Found a at: $i, $j, result is ${ar[finish.i][finish.j].steps}")

//                dump(cells) { cell -> "[${cell.point}:${cell.height.code}:${cell.steps}]" }
                if (min > ar[finish.i][finish.j].steps && ar[finish.i][finish.j].steps > 0)
                    min = ar[finish.i][finish.j].steps
            }
        }
    }


//    val result = findPath(input)
    return min
}

fun main() {
    val input = getResourceAsLines("/12 - HillClimbingAlgorithmInput.txt")
    val result = solution1(input)
    val result2 = solution2(input)

    println(result)
    println(result2)

    checkResult(result, 534)
    checkResult(result2, 525)

}
