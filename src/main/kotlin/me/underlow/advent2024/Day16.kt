import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24

object ReindeerMaze {

    data class Reindeer(val p: Point, val dir: Dir, val points: Int, val path: List<Point>) {
        fun toKey() = Key(this.p, this.dir)
    }

    data class Key(val p: Point, val dir: Dir)

    fun part1(list: List<String>): Int {
        val field: Array<Array<Char>> = list.parseToMap()

        var start = Point(0, 0)
        for (i in field.indices)
            for (j in field[0].indices)
                if (field[i][j] == 'S')
                    start = Point(i, j)


        val visited = mutableMapOf<Key, Int>()
        var result = Int.MAX_VALUE
        var resultPath = emptyList<Point>()
        val queue = mutableListOf<Reindeer>()
        queue += Reindeer(start, Dir.Right, 0, emptyList())

        while (queue.isNotEmpty()) {
            val r = queue.removeLast()
            if (field.get(r.p) == 'E') {
                if (r.points < result) {
                    println("New result: ${r.points}")
                    result = r.points
                    resultPath = r.path
                    continue
                }
            }

            val v = visited[r.toKey()]
            if (v != null) {
                if (v < r.points) {
//                    println("Cache hit")
                    continue
                }
            }

            visited[r.toKey()] = r.points

            // forward
            val pForward = r.p.move(r.dir)
            if (field.isPointInside(pForward) && field.get(pForward) != '#') {
                val r = Reindeer(pForward, r.dir, r.points + 1, r.path + pForward)
                queue += r
            }
            // clockwise
            val pClockwise = r.p.move(r.dir.rotateLeft())
            if (field.isPointInside(pClockwise) && field.get(pClockwise) != '#') {
                val r = Reindeer(pClockwise, r.dir.rotateLeft(), r.points + 1001, r.path + pClockwise)
                queue += r
            }
            // counterclockwise
            val pCounterClockwise = r.p.move(r.dir.rotateRight())
            if (field.isPointInside(pCounterClockwise) && field.get(pCounterClockwise) != '#') {
                val r = Reindeer(pCounterClockwise, r.dir.rotateRight(), r.points + 1001, r.path + pCounterClockwise)
                queue += r
            }
        }

        println("Path is: $resultPath")

        resultPath.forEach {
            if (field.get(it) == '#')
                error("www")
            field[it] = '*'
        }

        field.dumpWithAxis()

        return result
    }

    fun part2(list: List<String>): Int {
        val field = list.parseToMap()

        return 0
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day16.txt")
    val res1 = ReindeerMaze.part1(input)
    val res2 = ReindeerMaze.part2(input)

    checkResult(res1, 0) // 105508 high
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
