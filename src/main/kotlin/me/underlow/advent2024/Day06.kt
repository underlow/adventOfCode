import me.underlow.Dir
import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24
import me.underlow.parseToMap

object GuardGallivant {

    fun part1(list: List<String>): Int {
        val directions = parseInput(list)

        var start: Point? = null
        var dir: Dir? = null
        for (i in list.indices) {
            for (j in list[0].indices)
                when {
                    list[i][j] == '^' -> {
                        start = Point(i, j)
                        dir = Dir.Up
                    }

                    list[i][j] == '>' -> {
                        start = Point(i, j)
                        dir = Dir.Right
                    }

                    list[i][j] == 'v' -> {
                        start = Point(i, j)
                        dir = Dir.Down
                    }

                    list[i][j] == '<' -> {
                        start = Point(i, j)
                        dir = Dir.Left
                    }
                }
        }

        val visited = mutableSetOf<Point>()
        visited += start!!

        while (start!!.row in list.indices && start.col in list[0].indices) {
            //make a step
            val next = start.move(dir!!)

            if (!(next.row in list.indices && next.col in list[0].indices)) {
                break
            }

            if (list[next.row][next.col] != '#') {
                start = next
                visited += start
//                println("${visited.size} Step to $start")
                continue
            }

            dir = dir.rotateRight()
//            println("Rotate to $dir")
        }


        return visited.size
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)

        var start: Point? = null
        var dir: Dir? = null
        for (i in list.indices) {
            for (j in list[0].indices)
                when {
                    list[i][j] == '^' -> {
                        start = Point(i, j)
                        dir = Dir.Up
                    }

                    list[i][j] == '>' -> {
                        start = Point(i, j)
                        dir = Dir.Right
                    }

                    list[i][j] == 'v' -> {
                        start = Point(i, j)
                        dir = Dir.Down
                    }

                    list[i][j] == '<' -> {
                        start = Point(i, j)
                        dir = Dir.Left
                    }
                }
        }

        var sum = 0

        for (i in list.indices) {
            for (j in list[0].indices) {
                if (i == start!!.col && j == start.row) {
                    continue
                }
                if (directions[i][j] == '#') {
                    continue
                }

                val saved = directions[i][j]
                directions[i][j] = '#'
//                println("Try: $i $j")
                sum += if (loop(start, directions, dir)) 1 else 0

                directions[i][j] = saved


            }
        }

        return sum
    }

    private fun loop(
        start: Point?,
        list: Array<Array<Char>>,
        dir: Dir?
    ): Boolean {
        var start1 = start
        var dir1 = dir
        val visited = mutableSetOf<Pair<Point, Dir>>()
        visited += (start1!! to dir1!!)

        while (start1!!.row in list.indices && start1.col in list[0].indices) {
            //make a step
            val next = start1.move(dir1!!)

            if ((next to dir1) in visited) {
                // loop
//                println("Loop: $next to $dir of $visited")
                return true
            }

            if (!(next.row in list.indices && next.col in list[0].indices)) {
                break
            }

            if (list[next.row][next.col] != '#') {
                start1 = next
                visited += start1 to dir1
//                println("${visited.size} Step to $start1")
                continue
            }

            dir1 = dir1.rotateRight()
//            println("Rotate to $dir1")
        }
        return false
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.parseToMap()
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day06.txt")
    val res1 = GuardGallivant.part1(input)
    val res2 = GuardGallivant.part2(input)

    checkResult(res1, 4722)
    checkResult(res2, 1602)

    println(res1)
    println(res2)
}
