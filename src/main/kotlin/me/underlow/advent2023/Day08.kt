package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import java.util.*


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

    fun part2(list: List<String>): Long {
        val input = parseInput(list)
        var c = mutableSetOf<Long>()
//        var count = 0L
        input.nodes.filterValues { it.name.endsWith('A') }.values.toList().forEach { node ->
            println()
            print(node)
            var count = 0
            var current = node
            val finish = node

            do {
                val turn = input.route[count % input.route.length]
                when (turn) {
                    'L' -> current = current.left!!
                    'R' -> current = current.right!!
                }
                count++

                if (current.name.endsWith("Z"))
                    print(" $count")
            } while (!current.name.endsWith("Z"))
            print(" $count")
            c.add(count.toLong())

        }
        return lcm(c.toList())
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

    private fun gcd(x: Long, y: Long): Long {
        return if (y == 0L) x else gcd(y, x % y)
    }

    fun gcd(vararg numbers: Long): Long {
        return Arrays.stream(numbers).reduce(
            0
        ) { x: Long, y: Long -> gcd(x, y) }
    }

    fun lcm(numbers: List<Long>): Long {
        return Arrays.stream(numbers.toTypedArray()).reduce(
            1
        ) { x: Long, y: Long -> x * (y / gcd(x, y)) }
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day08.txt")
    val res1 = HauntedWasteland.part1(input)
    val res2 = HauntedWasteland.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 18157)
    checkResult(res2, 14299763833181L)
}
