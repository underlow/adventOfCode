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

        val result = field.findShortestPath(Point(0, 0), Point(x - 1, y - 1))

        return result
    }

    // smarter
    fun part12(list: List<String>, steps: Int, x: Int, y: Int): Int {
        val bytes = parseInput(list)
        val field: Array<Array<Char>> = Array<Array<Char>>(x, { Array<Char>(y, { '.' }) })

        var path = field.findShortestPathWithPoints(Point(0, 0), Point(x - 1, y - 1))

        for (i in 0 until steps) {
            field[bytes[i]] = '#'
            // we need to modify the path if point is on the path
            if (path.contains(bytes[i])) {
                val index = path.indexOf(bytes[i])
                // first and last shouldn't be in bytes so no IndexOutOfBounds
                val from = path[index - 1]
                val to = path[index + 1]

                val p = field.findShortestPathWithPoints(from, to)
                println(p)

                path = path.subList(0, index - 1) + p + path.subList(index + 1, path.size)
                println("Step #$i: path: ${path.size}")
            }
        }

        field.dumpWithAxis()

        val result = field.findShortestPathWithPoints(Point(0, 0), Point(x - 1, y - 1))

        return result.size - 1 // result is a path, returning steps
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<Point> {
        return list.map { Point(it.split(",")[1].toInt(), it.split(",")[0].toInt()) }
    }

    private fun Array<Array<Char>>.findShortestPath(from: Point, to: Point): Int {
        val visited = mutableMapOf<Point, Int>()
        val queue = PriorityQueue<Pair<Point, Int>>(Comparator { o1, o2 -> o2.second - o1.second })

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

            if ((visited[current.first] ?: Int.MAX_VALUE) <= current.second) {
                continue
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

    data class Path(val path: List<Point>)

    private fun Array<Array<Char>>.findShortestPathWithPoints(from: Point, to: Point): List<Point> {
        val visited = mutableMapOf<Point, Int>()
        val queue = PriorityQueue<Path>(Comparator { o1, o2 -> -o2.path.size + o1.path.size })

        queue += Path(listOf(from))

        var result = Int.MAX_VALUE
        var resultPath: Path = Path(emptyList())
        while (queue.isNotEmpty()) {
            val current = queue.poll()

            if (current.path.last() == to) {
                if (result > current.path.size) {
                    println("New path: ${current.path}")
                    result = current.path.size
                    resultPath = current
                    continue
                }
            }

            if ((visited[current.path.last()] ?: Int.MAX_VALUE) < current.path.size) {
                continue
            }

            visited[current.path.last()] = current.path.size


            val next = current.path.last().around().filter { this.isPointInside(it) }.filter {
                this.get(it) != '#'
            }.filter { p ->
                if (visited[p] != null && visited[p]!! < current.path.size)
                    return@filter false
                return@filter true
//                (visited[p] ?: Int.MAX_VALUE) > current.second
            }

            queue += next.map { current.copy(path = current.path + it) }
        }

        return resultPath.path
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day18.txt")
    val res1 = RAMRun.part1(input, 1024, 71, 71)
    val res2 = RAMRun.part2(input)

    checkResult(res1, 250)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
