import me.underlow.Dir
import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
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
//            print("Move " + direction + " : ")
            robot = robot.move(direction, field, '@') ?: robot
//            println("robot at $robot")
        }

//        field.dumpWithAxis()

        var sum = 0
        for (i in field.indices) {
            for (j in field[0].indices)
                if (field[i][j] == 'O') {
//                    println("Counting at $i,$j: ${100 * i + j}")
                    sum += 100 * i + j
                }
        }


        return sum
    }

    fun part2(list: List<String>): Int {
        val (field, directions) = parseInput2(list)
//        field.dumpWithAxis()

        var robot = Point(0, 0)

        for (i in field.indices) {
            for (j in field[0].indices)
                if (field[i][j] == '@')
                    robot = Point(i, j)
        }

        for (direction in directions) {
//            print("Move " + direction + " : ")
            robot = robot.move2(direction, field, '@') ?: robot
//            field.dumpWithAxis()
//            println("robot at $robot")
        }

//        field.dumpWithAxis()

        var sum = 0
        for (i in field.indices) {
            for (j in field[0].indices)
                if (field[i][j] == '[') {
//                    println("Counting at $i,$j: ${100 * i + j}")
                    sum += 100 * i + j
                }
        }


        return sum
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

    private fun parseInput2(list: List<String>): Pair<Array<Array<Char>>, List<Dir>> {
        val field =
            list.joinToString("\n")
                .split("\n\n")[0]
                .split("\n")
                .map { it.toCharArray().toTypedArray() }
                .toTypedArray()
                .map {
                    it.map {
                        when (it) {
                            '#' -> listOf('#', '#')
                            'O' -> listOf('[', ']')
                            '.' -> listOf('.', '.')
                            '@' -> listOf('@', '.')
                            else -> error("PPPPP")
                        }
                    }.flatten().toTypedArray()
                }.toTypedArray()
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

private fun Point.move2(dir: Dir, field: Array<Array<Char>>, char: Char): Point? {
    val newPoint = this.move(dir)
    if (field.get(newPoint) == '.') {
        field[newPoint] = char
        field[this] = '.'
        return newPoint
    }

    //cannot move
    if (field.get(newPoint) == '#')
        return null

    if (dir == Dir.Left || dir == Dir.Right) {
        val newSpace = newPoint.move(dir, field, field.get(newPoint))

        if (newSpace != null) {
            field[newPoint] = char
            field[this] = '.'
            return newPoint
        }
        return null
    } else {

        // cannot move but can try to move box
        when {
            field.get(newPoint) == '[' -> {
                val l = newPoint.movePart(dir, field, '[')
                val r = newPoint.move(Dir.Right).movePart(dir, field, ']')
                if (l.isNotEmpty() && r.isNotEmpty()) {
                    movePoints(
                        field,
                        l + r + listOf(
                            newPoint to newPoint.move(dir),
                            newPoint.move(Dir.Right) to newPoint.move(Dir.Right).move(dir)
                        )
                    )
                    field[newPoint] = char
                    field[this] = '.'
                    return newPoint
                }
            }

            field.get(newPoint) == ']' -> {
                val l = newPoint.move(Dir.Left).movePart(dir, field, '[')
                val r = newPoint.movePart(dir, field, ']')
                if (l.isNotEmpty() && r.isNotEmpty()) {
                    movePoints(
                        field,
                        l + r + listOf(
                            newPoint to newPoint.move(dir),
                            newPoint.move(Dir.Left) to newPoint.move(Dir.Left).move(dir)
                        )
                    )
                    field[newPoint] = char
                    field[this] = '.'
                    return newPoint
                }

            }
        }
    }

    return null

}

fun movePoints(field: Array<Array<Char>>, pairs: List<Pair<Point, Point>>) {
//    println("Moving pairs: $pairs")
    val mapFrom = mutableMapOf<Point, Char>()

    pairs.forEach { (from, _) ->
        mapFrom[from] = field.get(from)
    }

    pairs.forEach { (from, to) ->
        field[from] = '.'
    }
    pairs.forEach { (from, to) ->
        field[to] = mapFrom[from]!!
    }

}

private fun Point.movePart(dir: Dir, field: Array<Array<Char>>, char: Char): List<Pair<Point, Point>> {
    val newPoint = this.move(dir)
    if (field.get(newPoint) == '.') {
        return listOf(this to newPoint)
    }

    //cannot move
    if (field.get(newPoint) == '#')
        return emptyList()

    // cannot move but can try to move box
    if (field.get(newPoint) == '[') {
        val l = newPoint.movePart(dir, field, '[')
        val r = newPoint.move(Dir.Right).movePart(dir, field, ']')
        if (l.isNotEmpty() && r.isNotEmpty()) {
            return l + r + listOf(
                newPoint to newPoint.move(dir),
                newPoint.move(Dir.Right) to newPoint.move(Dir.Right).move(dir)
            )
        }
    } else if (field.get(newPoint) == ']') {
        val l = newPoint.move(Dir.Left).movePart(dir, field, '[')
        val r = newPoint.movePart(dir, field, ']')
        if (l.isNotEmpty() && r.isNotEmpty()) {
            return l + r + listOf(
                newPoint to newPoint.move(dir),
                newPoint.move(Dir.Left) to newPoint.move(Dir.Left).move(dir)
            )
        }
    } else {
        error("WOW")
    }

//    val newSpace = newPoint.move2(dir, field, field.get(newPoint))

    return emptyList()
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
