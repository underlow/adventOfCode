import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24

typealias Field = Array<Array<Char>>

object CeresSearch {

    data class Point(val x: Int, val y: Int, val char: Char) {

    }

    data class T2(val p: List<Point>)

    fun allOptions(p: Point, field: Field): List<T2> {
        val l = listOf(
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x + 1, p.y, field.getValue(p.x + 1, p.y)),
                Point(p.x + 2, p.y, field.getValue(p.x + 2, p.y)),
                Point(p.x + 3, p.y, field.getValue(p.x + 3, p.y))
            ),
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x - 1, p.y, field.getValue(p.x - 1, p.y)),
                Point(p.x - 2, p.y, field.getValue(p.x - 2, p.y)),
                Point(p.x - 3, p.y, field.getValue(p.x - 3, p.y))
            ),
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x, p.y - 1, field.getValue(p.x, p.y - 1)),
                Point(p.x, p.y - 2, field.getValue(p.x, p.y - 2)),
                Point(p.x, p.y - 3, field.getValue(p.x, p.y - 3))
            ),
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x, p.y + 1, field.getValue(p.x, p.y + 1)),
                Point(p.x, p.y + 2, field.getValue(p.x, p.y + 2)),
                Point(p.x, p.y + 3, field.getValue(p.x, p.y + 3))
            ),
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x + 1, p.y + 1, field.getValue(p.x + 1, p.y + 1)),
                Point(p.x + 2, p.y + 2, field.getValue(p.x + 2, p.y + 3)),
                Point(p.x + 3, p.y + 3, field.getValue(p.x + 3, p.y + 3))
            ),
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x - 1, p.y + 1, field.getValue(p.x - 1, p.y + 1)),
                Point(p.x - 2, p.y + 2, field.getValue(p.x - 2, p.y + 2)),
                Point(p.x - 3, p.y + 3, field.getValue(p.x - 3, p.y + 3))
            ),
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x - 1, p.y - 1, field.getValue(p.x - 1, p.y - 1)),
                Point(p.x - 2, p.y - 2, field.getValue(p.x - 2, p.y - 2)),
                Point(p.x - 3, p.y - 3, field.getValue(p.x - 3, p.y - 3))
            ),
            listOf(
                p.copy(char = field.getValue(p.x, p.y)),
                Point(p.x + 1, p.y - 1, field.getValue(p.x + 1, p.y - 1)),
                Point(p.x + 2, p.y - 2, field.getValue(p.x + 2, p.y - 2)),
                Point(p.x + 3, p.y - 3, field.getValue(p.x + 3, p.y - 3))
            ),


            )

        return l.filter { it.all { it.x in (0 until field.size) && it.y in (0 until field[0].size) } }.map { T2(it) }
    }

    private fun Field.getValue(x: Int, y: Int) =
        if (x in (0 until size) && y in (0 until this[0].size)) this[x][y] else '!'

    // XMAS
    fun part1(list: List<String>): Int {
        val field = parseInput(list)
        val tasks = mutableListOf<T2>()

        var count = 0

        for (i in field.indices)
            for (j in field[0].indices) {
                if (field[i][j] == 'X')
                    tasks += allOptions(Point(i, j, ' '), field)
            }

        while (tasks.isNotEmpty()) {
            val t = tasks.removeLast()

            if (field[t.p[0].x][t.p[0].y] == 'X' &&
                field[t.p[1].x][t.p[1].y] == 'M' &&
                field[t.p[2].x][t.p[2].y] == 'A' &&
                field[t.p[3].x][t.p[3].y] == 'S'
            ) {
                println("Fount $t --- ${field[t.p[0].x][t.p[0].y]}${field[t.p[1].x][t.p[1].y]}${field[t.p[2].x][t.p[2].y]}${field[t.p[3].x][t.p[3].y]}")
                count++
            }
        }
        return count
    }

    fun part2(list: List<String>): Int {
        val field = parseInput(list)

        var count = 0

        for (i in field.indices) {
            for (j in field[0].indices) {
                if (findXMAS(field, i, j)) count++
            }
        }



        return count
    }

    private fun findXMAS(field: Array<Array<Char>>, i: Int, j: Int): Boolean {
        if (field.getValue(i, j) != 'A') return false

        val c1 = field.getValue(i - 1, j - 1)
        val d1 = field.getValue(i + 1, j + 1)

        val c2 = field.getValue(i - 1, j + 1)
        val d2 = field.getValue(i + 1, j - 1)

        return (setOf(c1, d1) == setOf('S', 'M')) && (setOf(c2, d2) == setOf('S', 'M'))
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.map { it.toCharArray().toTypedArray() }.toTypedArray()
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day04.txt")
    val res1 = CeresSearch.part1(input)
    val res2 = CeresSearch.part2(input)

    checkResult(res1, 2534)
    checkResult(res2, 1866)

    println(res1)
    println(res2)
}
