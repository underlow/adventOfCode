import me.underlow.Dir
import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2023.ParabolicReflectorDish.dump
import me.underlow.advent2024.pathPrefix24
import me.underlow.get
import me.underlow.set

object WarehouseWoes {

    fun part1(list: List<String>): Int {
        val (field, directions) = parseInput(list)

        var robot = Point(0, 0)

        for (i in field.indices) {
            for (j in field[0].indices)
                if (field[i][j] == '@')
                    robot = Point(i, j)
        }

        for (direction in directions) {
            print("Move " + direction + " : ")
            robot = robot.move(direction, field, '@') ?: robot
            println("robot at $robot")
        }

        field.dump()

        var sum = 0
        for (i in field.indices) {
            for (j in field[0].indices)
                if (field[i][j] == 'O') {
                    println("Counting at $i,$j: ${100 * i + j}")
                    sum += 100 * i + j
                }
        }


        return sum
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): Pair<Array<Array<Char>>, List<Dir>> {
        val field =
            list.joinToString("\n").split("\n\n")[0].split("\n").map { it.toCharArray().toTypedArray() }.toTypedArray()
        val moves = list.joinToString("\n").split("\n\n")[1].toCharArray().toList().filter { it != '\n' }.map {
            when (it) {
                '>' -> Dir.Right
                '<' -> Dir.Left
                '^' -> Dir.Up
                'v' -> Dir.Down
                else -> error("Oops")
            }
        }
        return field to moves
    }
}

private fun Point.move(dir: Dir, field: Array<Array<Char>>, char: Char): Point? {
    val newPoint = this.move(dir)
    if (field.get(newPoint) == '.') {
        field[newPoint] = char
        field[this] = '.'
        return newPoint
    }

    //cannot move
    if (field.get(newPoint) == '#')
        return null

    // cannot move but can try move box
    val newSpace = newPoint.move(dir, field, field.get(newPoint))

    if (newSpace != null) {
        field[newPoint] = char
        field[this] = '.'
        return newPoint
    }
    return null
}


fun main() {
    val input = readInput("$pathPrefix24/day15.txt")
    val res1 = WarehouseWoes.part1(input)
    val res2 = WarehouseWoes.part2(input)

    checkResult(res1, 1527563)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
