package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object LANParty {

    data class Node(val id: String, val nodes: MutableList<Node>) {
        override fun toString(): String {
            return "Node(id='$id', nodes=${nodes.map { it.id }})"
        }
    }

    // looking for exactly 3 node cycle
    data class Cycle(val id1: String, val id2: String, val id3: String) {
        fun hasT() = id1.startsWith("t") || id2.startsWith("t") || id3.startsWith("t")
    }

    fun part1(list: List<String>): Int {
        val graph = parseInput(list)

        val cycles = mutableSetOf<Cycle>()

        for (node in graph) {
            val c = findCycle(node, graph)
            if (c.isNotEmpty())
                cycles += c
        }

        return cycles.filter { it.hasT() }.count()
    }

    fun findCycle(node: Node, graph: List<Node>): List<Cycle> {
        val result = mutableListOf<Cycle>()
        val first = node
        node.nodes.forEach { second ->
            second.nodes.forEach { third ->
                if (third.nodes.firstOrNull { it.id == first.id } != null) {
                    val nodesListSorted = listOf(first, second, third).sortedBy { it.id }
                    result += Cycle(nodesListSorted[0].id, nodesListSorted[1].id, nodesListSorted[2].id)
                }
            }
        }

        return result
    }

    data class Pointer(val start: Node, val count: Int, val visited: List<String>)

    fun findCycleT(node: Node, graph: List<Node>): List<String> {
        var result = mutableListOf<String>()
        val first = node

        node.nodes.forEach { second ->
            val cycle = runPointer(Pointer(first, 1, listOf(first.id)), graph, first)
            if (cycle.isNotEmpty())
                result = cycle.toMutableList()
        }

        return result
    }

    private fun runPointer(pointer: Pointer, graph: List<Node>, current: Node): List<String> {
//        if (pointer.count == size) {
        var result = emptyList<String>()
        // if next is the start
//        if (pointer.start == current && pointer.count > 1)
//            return pointer.visited/*+ current.id*/

//            return emptyList()
//        }

        // if not found and not in the end of the cycle
        val notInVisited = current.nodes.filter { it.id !in pointer.visited }
        for (node in notInVisited) {
            // if we can get from node to any already visited
            val lll = node.nodes.map { it.id }
            if (pointer.visited.all { it in lll }) {
                val newPointer = pointer.copy(count = pointer.count + 1, visited = pointer.visited + node.id)
                val found = runPointer(newPointer, graph, node)
                if (found.isNotEmpty() && result.size <= found.size) {
                    result = found + node.id
                } else {
                    if (result.isEmpty())
                        result = listOf(node.id)
                }
            }
        }

        return result
    }


    fun part2(list: List<String>): String {
        val graph = parseInput(list)

        var cycle = emptyList<String>()

        val result = emptyList<String>()

//        for (i in 4..graph.size) {
        for (node in graph) {
//                if (node.id != "co")
//                    continue
            val c = listOf(node.id) + runPointer(Pointer(node, 1, listOf(node.id)), graph, node)
//            val c = findCycleT(node, graph)

            if (c.isNotEmpty() && cycle.size < c.size) {
                println("Cycle for $node is $c")
                cycle = c

//                    if (i == 3) {
//                        println("For 3 found $c")
//                    }
            }
//            }
        }

        return cycle.sortedBy { it }.joinToString(",")
    }

    private fun parseInput(list: List<String>): List<Node> {
        val graph = mutableListOf<Node>()
        list.forEach { s ->
            val s1 = s.split('-')[0]
            val s2 = s.split('-')[1]
            if (s1 == s2) {
                error("slkdfjhgkr")
            }
            val n1 = graph.firstOrNull { it.id == s1 } ?: Node(s1, mutableListOf()).also { graph.add(it) }
            val n2 = graph.firstOrNull { it.id == s2 } ?: Node(s2, mutableListOf()).also { graph.add(it) }
            n1.nodes.add(n2)
            n2.nodes.add(n1)

        }
        return graph
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day23.txt")
    val res1 = LANParty.part1(input)
    val res2 = LANParty.part2(input)

    checkResult(res1, 1046)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
