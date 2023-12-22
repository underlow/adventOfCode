package me.underlow

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
