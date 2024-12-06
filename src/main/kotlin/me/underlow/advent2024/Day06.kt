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
                println("${visited.size} Step to $start")
                continue
            }

            dir = dir.rotateRight()
            println("Rotate to $dir")
        }


        return visited.size
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
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
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
