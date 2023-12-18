package me.underlow.advent2023

import me.underlow.Dir
import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import kotlin.math.min

object ClumsyCrucible {
    data class Beam(val point: Point, val dir: Dir, val steps: Int, val accHeat: Int/*, val path: List<Dir>*/) {
        fun nextMove(): List<Beam> {
            val dL = dir.rotateLeft()
            val l = Beam(point.move(dL), dL, 1, accHeat/*, path + dL*/)

            val dR = dir.rotateRight()
            val r = Beam(point.move(dR), dR, 1, accHeat/*, path + dR*/)

            val c = if (steps < 3) Beam(point.move(dir), dir, steps + 1, accHeat/*, path + dir*/) else null

            return listOf(l, r, c).filterNotNull()
        }

        fun nextMove2(): List<Beam> {
            if (steps < 4)
                return listOf(Beam(point.move(dir), dir, steps + 1, accHeat/*, path + dir*/))


            val dL = dir.rotateLeft()
            val l = Beam(point.move(dL), dL, 1, accHeat/*, path + dL*/)

            val dR = dir.rotateRight()
            val r = Beam(point.move(dR), dR, 1, accHeat/*, path + dR*/)

            val c = if (steps < 10) Beam(point.move(dir), dir, steps + 1, accHeat/*, path + dir*/) else null

            return listOf(l, r, c).filterNotNull()
        }

    }

    data class VisitedKey(val dir: Dir, val steps: Int)
    data class Cell(
        val heat: Int, val accMinHeat: MutableMap<VisitedKey, Int> = mutableMapOf(),
//        val path: MutableMap<Dir, Beam> = mutableMapOf()
    ) {
        init {
            accMinHeat[VisitedKey(Dir.Up, 1)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Up, 2)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Up, 3)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Down, 1)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Down, 2)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Down, 3)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Left, 1)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Left, 2)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Left, 3)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Right, 1)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Right, 2)] = Int.MAX_VALUE
            accMinHeat[VisitedKey(Dir.Right, 3)] = Int.MAX_VALUE
        }
    }

    private fun Array<Array<Cell>>.makeMove(beam: Beam): List<Beam> {
        if (beam.point == Point(this.size - 1, this[0].size - 1))
            return emptyList()

        val beams = beam.nextMove().asSequence()
            .filter { it.point.col in 0 until this[0].size && it.point.row in 0 until size }
            .filter {
                // point where we go
                val accHeat = this[it.point.row][it.point.col].heat + it.accHeat
                val h = this[it.point.row][it.point.col].accMinHeat[VisitedKey(it.dir, it.steps)]
                if (h == null)
                    return@filter true
                return@filter (h > accHeat)
            }.map {
                val accHeat = this[it.point.row][it.point.col].heat + it.accHeat
                this[it.point.row][it.point.col].accMinHeat[VisitedKey(it.dir, it.steps)] = accHeat
                return@map it.copy(accHeat = accHeat)
            }.toList()

        return beams
    }

    private fun Array<Array<Cell>>.makeMove2(beam: Beam): List<Beam> {
        if (beam.point == Point(this.size - 1, this[0].size - 1))
            return emptyList()

        val beams = beam.nextMove2().asSequence()
            .filter { it.point.col in 0 until this[0].size && it.point.row in 0 until size }
            .filter {
                // point where we go
                val accHeat = this[it.point.row][it.point.col].heat + it.accHeat
                val h = this[it.point.row][it.point.col].accMinHeat[VisitedKey(it.dir, it.steps)]
                if (h == null)
                    return@filter true
                return@filter (h > accHeat)
            }.map {
                val accHeat = this[it.point.row][it.point.col].heat + it.accHeat
                this[it.point.row][it.point.col].accMinHeat[VisitedKey(it.dir, it.steps)] = accHeat
                return@map it.copy(accHeat = accHeat)
            }.toList()

        return beams
    }

    fun part1(list: List<String>): Int {
        val field = parseInput(list)

        val beams = mutableListOf<Beam>().also {
            it.add(Beam(Point(0, 0), Dir.Right, 1, 0))
        }
        var minPath = 0
        var i = 0
        var j = 0
        while ((i + 1) < field.size && (j + 1) < field[0].size) {
            i++
            minPath += field[i][j].heat
            j++
            minPath += field[i][j].heat
        }

        while (beams.isNotEmpty()) {
            val beam = beams.last()
            beams.removeAt(beams.size - 1)
            val newBeams = field.makeMove(beam)

            val f = newBeams.filter { it.point == Point(field.size - 1, field[0].size - 1) }
            if (f.isNotEmpty()) {
                minPath = min(minPath, f.minOf { it.accHeat })
                println("New min: $minPath")
            }

            beams.addAll(newBeams.filter { it.accHeat < minPath })

            beams.sortBy { it.point.col * it.point.row }
        }

        return field[field.size - 1][field[0].size - 1].accMinHeat.map { it.value }.min()
    }

    fun part2(list: List<String>): Int {
        val field = parseInput(list)

        val beams = mutableListOf<Beam>().also {
            it.add(Beam(Point(0, 0), Dir.Right, 1, 0))
        }
        var minPath = 0
        var i = 0
        var j = 0
        while ((i + 1) < field.size && (j + 1) < field[0].size) {
            i++
            minPath += field[i][j].heat
            j++
            minPath += field[i][j].heat
        }

        while (beams.isNotEmpty()) {
            val beam = beams.last()
            beams.removeAt(beams.size - 1)
            val newBeams = field.makeMove2(beam)

            if (newBeams.any { it.point == Point(field.size - 1, field[0].size - 1) }) {
                println(newBeams)
            }


            val f = newBeams.filter { it.point == Point(field.size - 1, field[0].size - 1) && it.steps > 3 }
            if (f.isNotEmpty()) {
                minPath = min(minPath, f.minOf { it.accHeat })
                println("New min: $minPath")
            }

            beams.addAll(newBeams.filter { it.accHeat < minPath })

//            beams.sortBy { it.point.col * it.point.row }
        }

//        return field[field.size - 1][field[0].size - 1].accMinHeat.map { it.value }.min()
        return minPath
    }

    private fun parseInput(list: List<String>): Array<Array<Cell>> =
        list.map { it.toCharArray().map { Cell(it.digitToInt()) }.toTypedArray() }.toTypedArray()
}


fun main() {
    val input = readInput("$pathPrefix23/day17.txt")
//    val res1 = ClumsyCrucible.part1(input)
    val res2 = ClumsyCrucible.part2(input)

//    println("part 1: $res1")
    println("part 2: $res2")

//    checkResult(res1, 684) // 999 high
    checkResult(res2, 0) // 823 high
}
