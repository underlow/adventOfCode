package me.underlow.advent2022


// https://adventofcode.com/2022/day/13
object DistressSignalInput {
    interface Element

    data class Num(val value: Int) : Element {
        override fun toString(): String {
            return "$value"
        }
    }

    data class Lst(val value: List<Element>) : Element {
        override fun toString(): String {
            return "[${value.joinToString(",")}]"
        }
    }

    // [[1],[2,3,4]]
    fun parseInput(list: List<String>): List<Element> =
        list
            .filter { it.isNotBlank() }
            .map { parseLine(it) }

    private fun parseLine(line: String): Element {
        return when {
            line[0] == '[' -> parseList(line)
            else -> parseNumber(line)
        }
    }

    fun parseNumber(line: String): Element {
        return Num(Integer.parseInt(line))
    }

    // [1],[2,3,4]
    fun parseList(line: String): Lst {
        var pos = 0

        val ret = mutableListOf<Element>()

        while (pos < line.length) {
            when {
                line[pos] == '[' -> {
                    val endIndex = line.endOfSquares(pos)
                    ret += Lst(value = parseList(line.substring(pos + 1, endIndex)).value)
                    pos = endIndex + 1
                }

                line[pos] == ']' -> {
                    pos++
                }

                line[pos] == ',' -> {
                    pos++
                }
                // number
                else -> {
                    val endIndex = line.endOfNumber(pos)
                    ret += Num(Integer.parseInt(line.substring(pos, endIndex)))
                    pos = endIndex + 1
                }
            }
        }

        return Lst(ret)
    }

    private fun String.endOfNumber(pos: Int): Int {
        var i = pos + 1

        while (i < this.length && this[i] != ',') {
            i++

        }
        return i
    }

    private fun String.endOfSquares(pos: Int): Int {
        var i = pos + 1
        var acc = 1

        while (i < this.length) {
            if (this[i] == '[') acc++
            if (this[i] == ']') acc--
            if (acc == 0) return i
            i++

        }
        return i
    }

    fun compareElements(left: Element, right: Element): Int {
        return when {
            left is Num && right is Num -> left.value.compareTo(right.value)
            left is Lst && right is Lst -> compareLists(left, right)
            left is Num && right is Lst -> compareLists(Lst(listOf(Num(left.value))), right)
            left is Lst && right is Num -> compareLists(left, Lst(listOf(Num(right.value))))
            else -> {
                error("Something wrong")
            }
        }
    }

    fun compareLists(left: Lst, right: Lst): Int {
        var i = 0
        while (i < left.value.size && i < right.value.size) {
            val res = compareElements(left.value[i], right.value[i])
            if (res != 0) {
                return res
            }
            i++
        }

        if (left.value.size == right.value.size)
            return 0

        if (i == left.value.size)
            return -1

        return 1
    }

    fun solution1(list: List<String>): Int {
        val input = parseInput(list)
        var acc = 0
        for (i in input.indices step 2) {
            if (compareElements(input[i], input[i + 1]) < 0) {
                println("Found correct pair $i: ${input[i]} vs ${input[i + 1]}")
                acc += i / 2 + 1
            } else {
                println("Found incorrect pair $i: ${input[i]} vs ${input[i + 1]}")
            }
        }
        return acc
    }

    fun solution2(list: List<String>): Int {
        val input = parseInput(list + listOf("[[2]]", "[[6]]"))

        val sorted = input.sortedWith { o1, o2 -> compareElements(o1, o2) }

        var six = 0
        var two = 0

        sorted.forEachIndexed { idx, e ->
            if (e == Lst(listOf(Lst(listOf(Lst(listOf(Num(2)))))))) two = idx + 1
            if (e == Lst(listOf(Lst(listOf(Lst(listOf(Num(6)))))))) six = idx + 1
        }
        return two * six
    }
}

fun main() {
    val input = readInput("${pathPrefix}/day13.txt")
    val s1 = DistressSignalInput.solution1(input)

    val s2 = DistressSignalInput.solution2(input)

    println(s1)
    println(s2)
    checkResult(s1, 5366)
    checkResult(s2, 23391)
}
