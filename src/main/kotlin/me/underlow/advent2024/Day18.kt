import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24
import java.util.*

object RAMRun {
    // bruteforce
    fun part1(list: List<String>, steps: Int, x: Int, y: Int): Int {
        val bytes = parseInput(list)
        val field: Array<Array<Char>> = Array<Array<Char>>(x, { Array<Char>(y, { '.' }) })

        for (i in 0 until steps) {
            field[bytes[i]] = '#'
        }

        field.dumpWithAxis()

        val result = field.findShortesPath(Point(0, 0), Point(x - 1, y - 1))

        return result
    }

    fun part12(list: List<String>, steps: Int, x: Int, y: Int): Int {
        val bytes = parseInput(list)
        val field: Array<Array<Char>> = Array<Array<Char>>(x, { Array<Char>(y, { '.' }) })

        for (i in 0 until steps) {
            field[bytes[i]] = '#'
        }

        field.dumpWithAxis()

        val result = field.findShortesPath(Point(0, 0), Point(x - 1, y - 1))

        return result
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<Point> {
        return list.map { Point(it.split(",")[1].toInt(), it.split(",")[0].toInt()) }
    }

    private fun Array<Array<Char>>.findShortesPath(from: Point, to: Point): Int {
        val visited = mutableMapOf<Point, Int>()
        val queue = PriorityQueue<Pair<Point, Int>>(Comparator { o1, o2 -> -o2.second + o1.second })

        queue += (from to 0)

        var result = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val current = queue.poll()

            if (current.first == to) {
                if (result > current.second) {
                    println("New path: ${current.second}")
                    result = current.second
                    continue
                }
            }
            val c = visited.size
            visited[current.first] = current.second
            if (visited.size != c)
                println("Visited: ${visited.size}")

            val next = current.first.around().filter { this.isPointInside(it) }.filter {
                this.get(it) != '#'
            }.filter { p ->
                if (visited[p] != null && visited[p]!! < current.second)
                    return@filter false
                return@filter true
//                (visited[p] ?: Int.MAX_VALUE) > current.second
            }

            queue += next.map { it to (current.second + 1) }
        }

        return result
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day18.txt")
    val res1 = RAMRun.part1(input, 1024, 71, 71)
    val res2 = RAMRun.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
