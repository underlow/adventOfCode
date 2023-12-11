package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object HauntedWasteland {

    class Node(val name: String, var left: Node?, var right: Node?) {
        override fun toString(): String {
            return "Node(name='$name', left=${left?.name}, right=${right?.name})"
        }
    }

    data class Input(val route: String, val nodes: MutableMap<String, Node>)

    fun part1(list: List<String>): Int {
        val input = parseInput(list)

        var count = 0
        var current = input.nodes["AAA"]!!
        val finish = input.nodes["ZZZ"]!!

        while (current != finish) {
            val turn = input.route[count % input.route.length]
            when (turn) {
                'L' -> current = current.left!!
                'R' -> current = current.right!!
            }
            count++
        }

        return count
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)



        return 0
    }

    private fun parseInput(list: List<String>): Input {
        val route = list[0]

        val map = mutableMapOf<String, Node>()

        for (i in 2 until list.size) {
            val split = list[i].split(" ", "(", ",", "=").filter { it.isNotBlank() }
            val name = split[0]
            val left = split[1]
            val right = split[2]
            map[name] = Node(name, null, null)
        }

        for (i in 2 until list.size) {
            val split = list[i].split(" ", "(", ",", "=", ")").filter { it.isNotBlank() }
            val name = split[0]
            val left = split[1]
            val right = split[2]
            map[name]!!.left = map[left]
            map[name]!!.right = map[right]
        }


        return Input(route, map)
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day08.txt")
    val res1 = HauntedWasteland.part1(input)
    val res2 = HauntedWasteland.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 18157)
    checkResult(res2, 0)
}
