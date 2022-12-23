package me.underlow.advent2022

// I don't care if fail
fun getResourceAsString(path: String): String =
    object {}.javaClass.getResource(path)?.readText()!!.trim()

fun getResourceAsLines(path: String): List<String> =
    object {}.javaClass.getResource(path)?.readText()!!.split("\n").filter { it.isNotEmpty() }

fun <T> Array<Array<T>>.dump(op: (T) -> String = { it.toString() }) {
    for (i in indices) {
        for (j in this[0].indices) {
            print(op(this[i][j]))
        }
        println()
    }
}


data class Point(val x: Int, val y: Int) {
    operator fun plus(right: Point) = Point(this.x + right.x, this.y + right.y)
//    fun up() = Point(x + 1, y )
//    fun down() = Point(x - 1, y)
//    fun left() = Point(x , y - 1)
//    fun right() = Point(x , y + 1)
}

fun <T> checkResult(actual: T, expected: T) {
    check(actual == expected) { "$actual is incorrect answer" }
}

class CircularProvider<T>(private val data: List<T>) {
    private var current = 0

    fun next(): T {
        val ret = data[(current++) % data.size]
        current %= data.size
        return ret
    }

    fun toList(): List<T> {
        val r = mutableListOf<T>(data[current])
        var i = (current + 1) % data.size
        while (i != current) {
            r.add(data[i])
            i = (i + 1) % data.size
        }
        return r
    }

}
