import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24

object RAMRun {
    // bruteforce
    fun part1(list: List<String>, steps: Int, x: Int, y: Int): Int {
        val bytes = parseInput(list)
        val field: Array<Array<Char>> = Array<Array<Char>>(x, { Array<Char>(y, { '.' }) })

        for (i in 0 until steps) {
            field[bytes[i]] = '#'
        }

        field.dumpWithAxis()

        val result = field.findShortestPath(Point(0, 0), Point(x - 1, y - 1))

        return result
    }

    fun part2(list: List<String>, x: Int, y: Int): Any {
        val bytes = parseInput(list)
        val field: Array<Array<Char>> = Array(x, { Array(y, { '.' }) })

        for (i in 0 until bytes.size) {
            field[bytes[i]] = '#'
            val result = field.findShortestPath(Point(0, 0), Point(x - 1, y - 1))
            println("For $i: $result")
            if (result == Int.MAX_VALUE)
                return "${bytes[i].col},${bytes[i].row}"
        }



        return -1
    }

    private fun parseInput(list: List<String>): List<Point> {
        return list.map { Point(it.split(",")[1].toInt(), it.split(",")[0].toInt()) }
    }

    private fun Array<Array<Char>>.findShortestPath(from: Point, to: Point): Int {
        val visited: Array<Array<Int>> = Array(this.size, { Array(this[0].size, { Int.MAX_VALUE }) })
        val queue = mutableListOf<Pair<Point, Int>>()

        queue += (from to 0)

        var result = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val current = queue.removeLast()

            if (current.first == to) {
                if (result > current.second) {
//                    println("New path: ${current.second}")
                    result = current.second
                    continue
                }
            }

            if (visited.get(current.first) <= current.second) {
                continue
            }

            visited[current.first] = current.second

            val next = current.first
                .around()
                .filter { this.isPointInside(it) }
                .filter { this.get(it) != '#' }
                .filter { p -> visited.get(p) > current.second }


            queue += next.map { it to (current.second + 1) }
        }

        return result
    }
}


fun main() {
    val res1 = RAMRun.part1(readInput("$pathPrefix24/day18.txt"), 1024, 71, 71)
    val res2 = RAMRun.part2(readInput("$pathPrefix24/day18.txt"), 71, 71)

    checkResult(res1, 250)
    checkResult(res2, "56,8")

    println(res1)
    println(res2)
}
