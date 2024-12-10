import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24
import me.underlow.asInt
import me.underlow.isPointInside
import me.underlow.parseToMap

object HoofIt {

    data class Trail(val start: Point, val way: List<Point>)
    data class Task(val current: Point, val height: Char, val start: Point, val way: List<Point>)

    fun part1(list: List<String>): Int {
        val field = parseInput(list)

        val queue = mutableListOf<Task>()

        var sum = 0
        for (i in field.indices)
            for (j in field[0].indices)
                if (field[i][j] == '0')
                    sum += findAllTrails(field, i, j)




        return sum
    }

    private fun findAllTrails(field: Array<Array<Char>>, i: Int, j: Int): Int {
        if (field[i][j] != '0')
            return 0

        var sum = mutableSetOf<Point>()

        val queue = mutableListOf<Task>()
        queue += Task(Point(i, j), '0', Point(i, j), listOf(Point(i, j)))

        while (queue.isNotEmpty()) {
            val t = queue.removeLast()


            if (t.height == '9') {
                sum += t.current
//                println(
//                    "Found a way from ${t.start}[${field[t.start.row][t.start.col]}] " +
//                            "to ${t.current}[${field[t.current.row][t.current.col]}]" +
//                            "\nWay: ${t.way}"
//                )
                continue
            }

            val points = t
                .current
                .around()
                .filter { field.isPointInside(it) }
                .filter {
                    val c = field[it.row][it.col].asInt()
                    return@filter t.height.asInt() + 1 == c
                }

            points.forEach { queue.add(Task(it, field[it.row][it.col], t.start, t.way + it)) }
        }

        return sum.size
    }

    private fun findAllTrails2(field: Array<Array<Char>>, i: Int, j: Int): Int {
        if (field[i][j] != '0')
            return 0

        var sum = 0

        val queue = mutableListOf<Task>()
        queue += Task(Point(i, j), '0', Point(i, j), listOf(Point(i, j)))

        while (queue.isNotEmpty()) {
            val t = queue.removeLast()


            if (t.height == '9') {
                sum += 1
//                println(
//                    "Found a way from ${t.start}[${field[t.start.row][t.start.col]}] " +
//                            "to ${t.current}[${field[t.current.row][t.current.col]}]" +
//                            "\nWay: ${t.way}"
//                )
                continue
            }

            val points = t
                .current
                .around()
                .filter { field.isPointInside(it) }
                .filter {
                    val c = field[it.row][it.col].asInt()
                    return@filter t.height.asInt() + 1 == c
                }

            points.forEach { queue.add(Task(it, field[it.row][it.col], t.start, t.way + it)) }
        }

        return sum
    }

    fun part2(list: List<String>): Int {
        val field = parseInput(list)

        val queue = mutableListOf<Task>()

        var sum = 0
        for (i in field.indices)
            for (j in field[0].indices)
                if (field[i][j] == '0')
                    sum += findAllTrails2(field, i, j)




        return sum
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.parseToMap()
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day10.txt")
    val res1 = HoofIt.part1(input)
    val res2 = HoofIt.part2(input)

    checkResult(res1, 709)
    checkResult(res2, 1326)

    println(res1)
    println(res2)
}
