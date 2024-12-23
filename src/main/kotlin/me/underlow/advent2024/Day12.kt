package me.underlow.advent2024

import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object GardenGroups {

    data class Plant(val point: Point, val char: Char, val groupId: Int, val fence: List<Point>) {
        fun perimeter(field: Array<Array<Char>>) =
            fence.count { !field.isPointInside(it) } + fence.count { field.isPointInside(it) && field[it.row][it.col] != this.char }

        fun fences(field: Array<Array<Char>>): List<Fence> {
            return Dir.values().map { d ->
                val np = point.move(d)
                if (!field.isPointInside(np))
                    return@map Fence(this.point, d)

                if (field[np.row][np.col] != this.char)
                    return@map Fence(this.point, d)

                return@map null
            }.filterNotNull()
        }
    }

    data class Fence(val p: Point, val dir: Dir)


    fun part1(list: List<String>): Int {
        val charField = list.parseToMap()
        val grouped = extractGroups(charField)


        val s = grouped.entries.map { (_, points) ->
            val perimeter = points.sumOf { it.perimeter(charField) }
            val area = points.size
            return@map perimeter * area
        }


        return s.sum()
    }

    private fun findPoints(field: Array<Array<Char>>, i: Int, j: Int, visited: MutableSet<Point>): Set<Point> {
        val c = field[i][j]
        val p = Point(i, j)
        visited += p
        val candidates = p.around()
            .filter { field.isPointInside(it) }
            .filter { field[it.row][it.col] == c }
            .filter { it !in visited }
//        println("Candidates for $p: ${candidates.joinToString { it.toString() +" c: " + field[it.row][it.col] }}")
        return (candidates + listOf(p) + candidates.map { findPoints(field, it.row, it.col, visited) }
            .flatten()).toSet()
    }

    fun part2(list: List<String>): Int {
        val charField = list.parseToMap()

        val grouped = extractGroups(charField)

        val fcUp = grouped.map { (k, points) ->
            val up = fenceCount(points, charField, Dir.Up)
            val down = fenceCount(points, charField, Dir.Down)
            val left = fenceCountVertical(points, charField, Dir.Left)
            val right = fenceCountVertical(points, charField, Dir.Right)
            val price = up + down + left + right
            val count = points.size
            println("Group ${charField.get(points[0].point)} -> $count * $price")
            return@map price * count
        }.sum()

        return fcUp
    }

    private fun fenceCount(
        points: List<Plant>,
        charField: Array<Array<Char>>,
        dir: Dir
    ): Int {
        val fences = points.map { it.fences(charField) }
        var fenceCount = 0
        // combine Up fences
        for (i in charField.indices) {
            var last = '0'
            for (j in charField[0].indices) {
                val p = fences.flatMap { it.map { it } }.filter { it.dir == dir }.find { it.p == Point(i, j) }
                if (p != null) {
                    val c = charField.get(p.p)
                    if (last == '0') {
                        last = c
                        fenceCount++
                    }
                    if (c == last) {
                        continue
                    }
                } else {
                    last = '0'
                }
            }

        }
//        println("Fences: $k -> $fenceCount")
        return fenceCount
    }

    private fun fenceCountVertical(
        points: List<Plant>,
        charField: Array<Array<Char>>,
        dir: Dir
    ): Int {
        val fences = points.map { it.fences(charField) }
        var fenceCount = 0
        // combine Up fences
        for (i in charField.indices) {
            var last = '0'
            for (j in charField[0].indices) {
                val p = fences.flatMap { it.map { it } }.filter { it.dir == dir }.find { it.p == Point(j, i) }
                if (p != null) {
                    val c = charField[p.p.col][p.p.row]
                    if (last == '0') {
                        last = c
                        fenceCount++
                    }
                    if (c == last) {
                        continue
                    }
                } else {
                    last = '0'
                }
            }

        }
//        println("Fences: $k -> $fenceCount")
        return fenceCount
    }

    fun extractGroups(charField: Array<Array<Char>>): MutableMap<Int, List<Plant>> {
        val field = Array<Array<Plant?>>(charField.size) {
            Array(charField[0].size) { null }
        }
        var gId = 0
        // find out groups
        for (i in charField.indices) {
            for (j in charField[0].indices) {
                if (field[i][j] != null)
                    continue
                //                println("Searching for : ${Point(i,j)}")
                val visited = mutableSetOf(Point(i, j))
                val groupPoints = findPoints(charField, i, j, visited)
                groupPoints.forEach { p ->
                    field[p.row][p.col] =
                        Plant(
                            p,
                            charField[p.row][p.col],
                            gId,
                            p.around()
                        )
                }
                gId++

            }
        }

        val grouped = mutableMapOf<Int, List<Plant>>()

        for (i in field.indices) {
            for (j in field[0].indices) {
                val p = field[i][j]!!
                grouped[p.groupId] = grouped.getOrElse(p.groupId) { emptyList() } + listOf(p)
            }
        }
        return grouped
    }

    fun fencesUp(charField: Array<Array<Char>>): Int {
        var fences = 0
        for (i in charField.indices) {
            // go by line
            var lastFence = '0'
            for (j in charField[0].indices) {

                val p = Point(i, j)
                val up = p.move(Dir.Up)
                val left = p.move(Dir.Left)

                val upSame = when {
                    !charField.isPointInside(up) -> false
                    else -> charField.get(up) == charField.get(p)
                }
                val leftSame = when {
                    !charField.isPointInside(left) -> false
                    else -> charField.get(left) == charField.get(p)
                }


                // not fence
                if (upSame) {
                    continue
                }
                // fence but a part of already counted fence
                if (!upSame && leftSame && lastFence == charField.get(p)) {
                    continue
                }

                // new fence in case left the same letter but not fence
                if (lastFence != charField.get(p)) {
                    fences++
                    lastFence = charField.get(p)
                }

            }

            println("Row: $i   Fences: $fences")

        }
        return fences
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day12.txt")
    val res1 = GardenGroups.part1(input)
    val res2 = GardenGroups.part2(input)

    checkResult(res1, 1522850)
    checkResult(res2, 953738)

    println(res1)
    println(res2)
}
