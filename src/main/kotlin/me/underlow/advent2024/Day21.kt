package me.underlow.advent2024

import me.underlow.*
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object KeypadConundrum {

    private val numericKeypad = """
        789
        456
        123
        #0A
    """.trimIndent().split("\n").parseToMap()

    private val directionalKeypad = """
        #^A
        <v>
    """.trimIndent().split("\n").parseToMap()

    // now let's find and store all shortest paths on directionalKeypad from one button to another on numericKeypad
    data class Move(val from: Point, val to: Point)

    val numericPaths: MutableMap<Move, List<String>> = findAllNumericShortPaths(numericKeypad)
    val directionalToDirectional: MutableMap<Move, List<String>> = findAllDirectionalToDirectional(
        directionalKeypad
    )

    fun findAllNumericShortPaths(
        numeric: Array<Array<Char>>
    ): MutableMap<Move, List<String>> {
        val ret = mutableMapOf<Move, List<String>>()
        for (i1 in numeric.indices) {
            for (j1 in numeric[0].indices) {
                for (i2 in numeric.indices) {
                    for (j2 in numeric[0].indices) {
                        val from = Point(i1, j1)
                        val to = Point(i2, j2)
                        if (from == to)
                            continue
                        // for each coord pair
                        val findAllShortestPaths = numeric.findAllShortestPaths(from, to).map { path ->
                            val fulllPath = listOf(from) + path
                            val ret = mutableListOf<Char>()
                            for (i in 0 until fulllPath.size - 1) {
                                val p1 = fulllPath[i]
                                val p2 = fulllPath[i + 1]
                                ret += when {
                                    p1.move(Dir.Up) == p2 -> '^'
                                    p1.move(Dir.Down) == p2 -> 'v'
                                    p1.move(Dir.Left) == p2 -> '<'
                                    p1.move(Dir.Right) == p2 -> '>'
                                    else -> error("wldskjfglkaj")
                                }
                            }
                            return@map ret.joinToString("")
                        }



                        ret += (Move(from, to) to findAllShortestPaths)

                    }
                }
            }
        }
        return ret
    }

    // todo: dupe
    fun findAllDirectionalToDirectional(
        directional: Array<Array<Char>>
    ): MutableMap<Move, List<String>> {
        val ret = mutableMapOf<Move, List<String>>()
        for (i1 in directional.indices) {
            for (j1 in directional[0].indices) {
                for (i2 in directional.indices) {
                    for (j2 in directional[0].indices) {
                        val from = Point(i1, j1)
                        val to = Point(i2, j2)
//                        if (from == to)
//                            continue
                        // for each coord pair
                        val findAllShortestPaths = directional.findAllShortestPaths(from, to).map { path ->
                            val fulllPath = listOf(from) + path
                            val ret = mutableListOf<Char>()
                            for (i in 0 until fulllPath.size - 1) {
                                val p1 = fulllPath[i]
                                val p2 = fulllPath[i + 1]
                                ret += when {
                                    p1.move(Dir.Up) == p2 -> '^'
                                    p1.move(Dir.Down) == p2 -> 'v'
                                    p1.move(Dir.Left) == p2 -> '<'
                                    p1.move(Dir.Right) == p2 -> '>'
                                    else -> error("wldskjfglkaj")
                                }
                            }
                            return@map ret.joinToString("")
                        }



                        ret += (Move(from, to) to findAllShortestPaths)

                    }
                }
            }
        }
        return ret
    }

    private fun shortestPath(code: String): String {
        // start on A, but than
        var start = numericKeypad.findFirst('A')

        val numericPaths: List<List<String>> = code.map { char ->
            val to = numericKeypad.findFirst(char)
            val pathsOnNumeric: List<String> = numericPaths[Move(start, to)]!!.map { it + 'A' }
            start = to
            pathsOnNumeric
        }

        val allNumericPaths = joinAll(numericPaths, 0)

        val directionalPaths: List<List<String>> = allNumericPaths.map { s ->
            start = directionalKeypad.findFirst('A')
            val map = s.map { char ->
                val to = directionalKeypad.findFirst(char)
                val pathsOnDirectional: List<String> = directionalToDirectional[Move(start, to)]!!.map { it + 'A' }
                start = to
                pathsOnDirectional
            }
            joinAll(map, 0)
            //
        }

        val allDirectionPaths = directionalPaths.flatten()//joinAll(directionalPaths, 0)
        val secondDirectionalPaths: List<List<String>> = allDirectionPaths.map { s ->
            start = directionalKeypad.findFirst('A')
            val map = s.map { char ->
                // for each char we have to move to it and get back and press A button
                // get all paths from A to char and then find all paths to print it by directional keypad
                val to = directionalKeypad.findFirst(char)
                val pathsOnDirectional: String = directionalToDirectional[Move(start, to)]!!.map { it + 'A' }[0]
                start = to
                pathsOnDirectional
            }
//            joinAll(map, 0)
            map
            //
        }

        val all2DirectionPaths = secondDirectionalPaths.map { it.joinToString("") }// joinAll(secondDirectionalPaths, 0)

        return all2DirectionPaths.minBy { it.length }
    }

    private fun joinAll(directions: List<List<String>>, start: Int): List<String> {
        if (directions.size == 1)
            return directions[0]

        return directions[0].map { first ->
            val joinAll = joinAll(directions.subList(1, directions.size), start + 1)
            joinAll.map { first + it }
        }.flatten()
    }

    fun part1(codes: List<String>): Int {

        val list = codes.map { code ->
            println("Looking for $code")
            val shortestPath = shortestPath(code)
            println("Shortest path for $code is ${shortestPath}")
            val numericCode = code.filter { it.isDigit() }.toInt()
            return@map shortestPath.length * numericCode
        }

        return list.sum()
    }

    fun part2(codes: List<String>): Int {
        return 0
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day21.txt")
    val res1 = KeypadConundrum.part1(input)
    val res2 = KeypadConundrum.part2(input)

    checkResult(res1, 188398)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
