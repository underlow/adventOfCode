package me.underlow.advent2015

import me.underlow.advent2022.checkResult

object AllInASingleNight {

    fun part1(list: List<String>): Int {
        val paths = parseInput(list)

        fun List<Path>.findMin(current: String, visited: List<String>, currentPath: Int): Int {
            if (visited.size == this.map { it.to }.distinct().size) {
                println("Fount path $currentPath length $visited ")
                return currentPath
            }

            return filter { it.to !in visited }.map { p ->
                if (p.from != current || p.to in visited)
                    return@map Int.MAX_VALUE

                return@map findMin(p.to, visited + p.to, currentPath + p.distance)
            }.min()
        }

        val min = paths.map { path ->
            paths.findMin(path.from, listOf(path.from), 0)
        }.min()

        return min
    }

    fun part2(list: List<String>): Int {
        val paths = parseInput(list)

        fun List<Path>.findMax(current: String, visited: List<String>, currentPath: Int): Int {
            if (visited.size == this.map { it.to }.distinct().size) {
                println("Fount path $currentPath length $visited ")
                return currentPath
            }

            return filter { it.to !in visited }.map { p ->
                if (p.from != current || p.to in visited)
                    return@map Int.MIN_VALUE

                return@map findMax(p.to, visited + p.to, currentPath + p.distance)
            }.max()
        }

        val max = paths.maxOfOrNull { path ->
            paths.findMax(path.from, listOf(path.from), 0)
        }

        return max!!
    }

    private fun parseInput(list: List<String>): List<Path> {
        return list.map { s ->
            val from = s.substringBefore(" to ")
            val to = s.substringAfter(" to ").substringBefore(" = ")
            val distance = s.substringAfter(" = ").toInt()
            return@map listOf(Path(from, to, distance), Path(to, from, distance))
        }.flatten()

    }

    data class Path(val from: String, val to: String, val distance: Int)

}


fun main() {
    val input = readInput("$pathPrefix/day09.txt")
    val res1 = AllInASingleNight.part1(input)
    val res2 = AllInASingleNight.part2(input)

    checkResult(res1, 251)
    checkResult(res2, 898)

    println(res1)
    println(res2)
}
