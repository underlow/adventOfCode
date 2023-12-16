package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object TheFloorWillBeLava {
    enum class Dir { Up, Down, Left, Right }
    data class Beam(val row: Int, val col: Int, val dir: Dir) {
        fun move(dir: Dir): Beam {
            return when (dir) {
                Dir.Up -> Beam(row = row - 1, col = col, dir = dir)
                Dir.Down -> Beam(row = row + 1, col = col, dir = dir)
                Dir.Left -> Beam(row = row, col = col - 1, dir = dir)
                Dir.Right -> Beam(row = row, col = col + 1, dir = dir)
            }
        }

        fun split(dir: Dir): Beam {
            return when (dir) {
                Dir.Up -> Beam(row = row - 1, col = col, dir = dir)
                Dir.Down -> Beam(row = row + 1, col = col, dir = dir)
                Dir.Left -> Beam(row = row, col = col - 1, dir = dir)
                Dir.Right -> Beam(row = row, col = col + 1, dir = dir)
            }
        }
    }

    data class Cell(val char: Char, val visited: MutableSet<Dir>) {
        fun visit(beam: Beam): List<Beam> {
            if (beam.dir in visited)
                return emptyList()

            visited += beam.dir

            // empty space -> just go on
            if (char == '.') {
                return listOf(beam.move(beam.dir))
            }
            // mirrors
            if (char == '/') {
                val newDir = when (beam.dir) {
                    Dir.Up -> Dir.Right
                    Dir.Down -> Dir.Left
                    Dir.Left -> Dir.Down
                    Dir.Right -> Dir.Up
                }
                return listOf(beam.move(newDir))
            }
            if (char == '\\') {
                val newDir = when (beam.dir) {
                    Dir.Up -> Dir.Left
                    Dir.Down -> Dir.Right
                    Dir.Left -> Dir.Up
                    Dir.Right -> Dir.Down
                }
                return listOf(beam.move(newDir))
            }
            // splitters
            if (char == '-') {
                if (beam.dir == Dir.Right || beam.dir == Dir.Left)
                    return listOf(beam.move(beam.dir))

                if (beam.dir == Dir.Down || beam.dir == Dir.Up)
                    return listOf(beam.split(Dir.Right), beam.split(Dir.Left))
            }
            // splitters
            if (char == '|') {
                if (beam.dir == Dir.Up || beam.dir == Dir.Down)
                    return listOf(beam.move(beam.dir))

                if (beam.dir == Dir.Left || beam.dir == Dir.Right)
                    return listOf(beam.split(Dir.Up), beam.split(Dir.Down))
            }
            error("Oops")
        }
    }


    fun part1(list: List<String>): Int {
        val field = parseInput(list)
        val beams = mutableListOf(Beam(0, 0, Dir.Right))

        while (beams.isNotEmpty()) {
            val beam = beams.first()
            beams.removeAt(0)
            val newBeams = field.makeMove(beam)
            beams.addAll(newBeams)
//            field.dump()
        }

        return field.sumOf {
            it.count { it.visited.isNotEmpty() }
        }
    }

    fun part2(list: List<String>): Int {
        val field = parseInput(list)

        val starting =
            (0 until field[0].size).map {
                listOf(
                    Beam(0, it, Dir.Down),
                    Beam(field.size - 1, it, Dir.Up),
                )
            }.flatten() +
                    (0 until field.size).map {
                        listOf(
                            Beam(it, 0, Dir.Left),
                            Beam(it, field[0].size - 1, Dir.Left),
                        )
                    }.flatten()

        val max = starting.map { beam ->
            val f = parseInput(list)
            val beams = mutableListOf(beam)
            while (beams.isNotEmpty()) {
                val beam = beams.first()
                beams.removeAt(0)
                val newBeams = f.makeMove(beam)
                beams.addAll(newBeams)
//            field.dump()
            }

            return@map f.sumOf {
                it.count { it.visited.isNotEmpty() }
            }
        }

        return max.max()
    }

    private fun parseInput(list: List<String>): Array<Array<Cell>> =
        list.map { it.toCharArray().map { Cell(it, mutableSetOf()) }.toTypedArray() }.toTypedArray()

    fun Array<Array<Cell>>.dump() {
        println()
        for (row in this.indices) {
            for (column in this[0].indices) {
                print(if (this[row][column].visited.isNotEmpty()) "#" else '.')
            }
            println()
        }
    }


    private fun Array<Array<Cell>>.makeMove(beam: Beam): List<Beam> {
        val beams = this[beam.row][beam.col].visit(beam)

        return beams.filter { it.col in 0 until this[0].size && it.row in 0 until this.size }
    }


}


fun main() {
    val input = readInput("$pathPrefix23/day16.txt")
    val res1 = TheFloorWillBeLava.part1(input)
    val res2 = TheFloorWillBeLava.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 6994)
    checkResult(res2, 7488)
}
