import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24

typealias Field = Array<Array<Char>>

object CeresSearch {

    data class Point(val x: Int, val y: Int) {
        fun findNeighbours(): List<Point> {
            return listOf(
                Point(x - 1, y),
                Point(x + 1, y),
                Point(x, y - 1),
                Point(x, y + 1),
                Point(x - 1, y - 1),
                Point(x + 1, y + 1),
                Point(x - 1, y + 1),
                Point(x + 1, y - 1),
            )
        }

        fun validNeighbours(mX: Int, mY: Int) = findNeighbours().filter { it.x in (0 until mX) && it.y in (0 until mY) }
    }

    data class Task(val step: Int, val point: Point, val visited: List<Point>, val l: List<Char>) {
        fun isFinal(field: Field): Boolean {
            return step == 4
        }

        fun next(field: Field): List<Task> {
            val n = this.point.validNeighbours(field.size, field[0].size)

            val n2 = n.filter {
                when (step) {
                    1 -> return@filter field[it.x][it.y] == 'M'
                    2 -> return@filter field[it.x][it.y] == 'A'
                    3 -> return@filter field[it.x][it.y] == 'S'
                    else -> false
                }
            }

            val n3 = n2.filter { it !in visited }

            return n3.map { Task(step = this.step + 1, it, this.visited + it, this.l + field[it.x][it.y]) }
        }

    }

    // XMAS
    fun part1(list: List<String>): Int {
        val field = parseInput(list)
        val tasks = mutableListOf<Task>()

        var count = 0

        for (i in field.indices)
            for (j in field[0].indices) {
                if (field[i][j] == 'X')
                    tasks += Task(1, Point(i, j), listOf(Point(i, j)), listOf(field[i][j]))
            }

        while (tasks.isNotEmpty()) {
            val t = tasks.removeLast()
            if (t.isFinal(field)) {
                count++
                println("Find word: $t")
                continue
            }

            tasks += t.next(field)

        }
        return count
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.map { it.toCharArray().toTypedArray() }.toTypedArray()
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day04.txt")
    val res1 = CeresSearch.part1(input)
    val res2 = CeresSearch.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
