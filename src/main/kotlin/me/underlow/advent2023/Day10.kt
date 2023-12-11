package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object PipeMaze {
    data class Point(val row: Int, val col: Int) {
        operator fun plus(other: Point) =
            Point(row + other.row, col + other.col)

    }

    data class Move(val moves: List<Point>)

    /**
     * | is a vertical pipe connecting north and south.
     * - is a horizontal pipe connecting east and west.
     * L is a 90-degree bend connecting north and east.
     * J is a 90-degree bend connecting north and west.
     * 7 is a 90-degree bend connecting south and west.
     * F is a 90-degree bend connecting south and east.
     * . is ground; there is no pipe in this tile.
     *
     */
    fun part1(list: List<String>): Int {
        val maze = parseInput(list)
        var start: Point? = null

        for (i in list.indices) {
            for (j in list[0].indices)
                if (list[i][j] == 'S') {
                    start = Point(i, j)
                    break
                }
        }
        if (start == null) error("Oops")

        val visited = Array<Array<Boolean>>(list.size) { Array(list[0].length) { false } }
        val route = Array<Array<Int>>(list.size) { Array(list[0].length) { 0 } }

        visited[start.row][start.col] = true
        route[start.row][start.col] = 0

        fun canMakeMove(from: Point, to: Point): Boolean {
            if (from.row !in 0 until maze.size)
                return false
            if (from.col !in 0 until maze.size)
                return false

            val m = maze[from.row][from.col]

            return m.moves.any { (it + from) == to }
        }

        // find starting moves
        val p1 = if (canMakeMove(start + Point(1, 0), start)) start + Point(1, 0) else null
        val p2 = if (canMakeMove(start + Point(-1, 0), start)) start + Point(-1, 0) else null
        val p3 = if (canMakeMove(start + Point(0, 1), start)) start + Point(0, 1) else null
        val p4 = if (canMakeMove(start + Point(0, -1), start)) start + Point(0, -1) else null

        var s1 = listOf(p1, p2, p3, p4).filterNotNull()[0]
        var s2 = listOf(p1, p2, p3, p4).filterNotNull()[1]

        var count = 1
        while (s1 != s2) {
            visited[s1.row][s1.col] = true
            visited[s2.row][s2.col] = true
            route[s1.row][s1.col] = count
            route[s2.row][s2.col] = count


            val pos1 = maze[s1.row][s1.col].moves.map {
                val cell = it + s1
                if (!visited[cell.row][cell.col])
                    return@map cell
                else
                    return@map null
            }.filterNotNull()
            require(pos1.size == 1) {
                "OOps"
            }
            s1 = pos1[0]

            val pos2 = maze[s2.row][s2.col].moves.map {
                val cell = it + s2
                if (!visited[cell.row][cell.col])
                    return@map cell
                else
                    return@map null
            }.filterNotNull()
            require(pos2.size == 1)
            s2 = pos2[0]

            count++

        }


        return count
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Array<Array<Move>> {
        val s = list.map { it.toCharArray().map { it.toMove() }.toTypedArray() }.toTypedArray()
        return s
    }


    private fun Char.toMove(): Move {
        return when (this) {
            '|' -> Move(listOf(Point(-1, 0), Point(1, 0)))
            '-' -> Move(listOf(Point(0, -1), Point(0, 1)))
            'L' -> Move(listOf(Point(-1, 0), Point(0, 1)))
            'J' -> Move(listOf(Point(-1, 0), Point(0, -1)))
            '7' -> Move(listOf(Point(1, 0), Point(0, -1)))
            'F' -> Move(listOf(Point(1, 0), Point(0, 1)))
            else -> Move(emptyList())
        }
    }


}

fun main() {
    val input = readInput("$pathPrefix23/day10.txt")
    val res1 = PipeMaze.part1(input)
    val res2 = PipeMaze.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 0)
    checkResult(res2, 0)
}
