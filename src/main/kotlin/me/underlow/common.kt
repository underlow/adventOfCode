package me.underlow

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

enum class Dir {
    Up, Down, Left, Right;

    fun rotateLeft() = when (this) {
        Up -> Left
        Down -> Right
        Left -> Down
        Right -> Up
    }

    fun rotateRight() = when (this) {
        Up -> Right
        Down -> Left
        Left -> Up
        Right -> Down
    }

}

data class Point(val row: Int, val col: Int) {
    fun move(dir: Dir): Point {
        return when (dir) {
            Dir.Up -> Point(row = row - 1, col = col)
            Dir.Down -> Point(row = row + 1, col = col)
            Dir.Left -> Point(row = row, col = col - 1)
            Dir.Right -> Point(row = row, col = col + 1)
        }
    }

    fun move(dir: Dir, steps: Int): Point {
        return when (dir) {
            Dir.Up -> Point(row = row - steps, col = col)
            Dir.Down -> Point(row = row + steps, col = col)
            Dir.Left -> Point(row = row, col = col - steps)
            Dir.Right -> Point(row = row, col = col + steps)
        }
    }

    fun around(): List<Point> {
        return listOf(
            Point(row = row - 1, col = col),
            Point(row = row + 1, col = col),
            Point(row = row, col = col - 1),
            Point(row = row, col = col + 1)
        )
    }

    operator fun plus(other: Point) = Point(row + other.row, col + other.col)

    companion object {
        val ZERO = Point(0, 0)
    }

}

fun List<String>.parseToMap(): Array<Array<Char>> =
    map { it.toCharArray().map { it }.toTypedArray() }.toTypedArray()

fun Array<Array<Char>>.findFirst(c: Char): Point {
    for (i in this.indices) {
        for (j in this[0].indices) {
            if (this[i][j] == c)
                return Point(i, j)
        }
    }
    error("Char $c hasn't found")

}

fun <T> Array<Array<T>>.countElements(c: T): Int {
    var count = 0
    for (i in this.indices) {
        for (j in this[0].indices) {
            if (this[i][j] == c)
                count++
        }
    }
    return count
}

fun <T> Array<Array<T>>.isPointInside(p: Point): Boolean {
    return p.row in this.indices && p.col in this[0].indices
}

fun <T> Array<Array<T>>.get(p: Point): T {
    return this[p.row][p.col]
}

operator fun <T> Array<Array<T>>.set(p: Point, value: T) {
    this[p.row][p.col] = value
}


// '5' -> 5 as int
fun Char.asInt() = code - '0'.code

@OptIn(ExperimentalTime::class)
inline fun speed(block: () -> Unit) {
    val t = measureTime {
        block()
    }
    println("Execution time: $t")

}

fun gcd(x: Long, y: Long): Long {
    return if (y == 0L) x else gcd(y, x % y)
}

fun gcd(x: Int, y: Int): Int {
    return if (y == 0) x else gcd(y, x % y)
}

private val xAxis = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"

fun Array<Array<Char>>.dumpWithAxis() {
    println("")
    println("  " + xAxis.take(this[0].size))
    for (row in this.indices) {
        print("${row % 10} ")
        for (column in this[0].indices) {
            print(this[row][column])
        }
        println()
    }
}


fun Array<Array<Char>>.dump() {
    println()
    for (row in this.indices) {
        for (column in this[0].indices) {
            print(this[row][column])
        }
        println()
    }
}
