package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

object LANParty {

    data class Node(val id: String, val nodes: MutableList<Node>, val nodeIds: MutableSet<String>) {
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

    private fun runPointer(visited: Set<String>, current: Node): List<String> {
        var result = emptyList<String>()

        // if not found and not in the end of the cycle
        val notInVisited = current.nodes.filter { it.id !in visited }
        for (node in notInVisited) {
            if (node.id in result)
                continue
            // if we can get from this node to any already visited
            if (visited.all { it in node.nodeIds }) {
                val found = runPointer(visited + node.id, node)

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

        for (node in graph) {
            val c = listOf(node.id) + runPointer(setOf(node.id), node)

            if (c.isNotEmpty() && cycle.size < c.size) {
                println("Cycle for $node is ${c.sortedBy { it }}")
                cycle = c

            }
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
            val n1 =
                graph.firstOrNull { it.id == s1 } ?: Node(s1, mutableListOf(), mutableSetOf()).also { graph.add(it) }
            val n2 =
                graph.firstOrNull { it.id == s2 } ?: Node(s2, mutableListOf(), mutableSetOf()).also { graph.add(it) }
            n1.nodes.add(n2)
            n2.nodes.add(n1)

        }
        graph.forEach {
            it.nodeIds += it.nodes.map { it.id }
        }
        return graph
    }
}


@OptIn(ExperimentalTime::class)
fun main() {
    val input = readInput("$pathPrefix24/day23.txt")
    val time = measureTime {
        val res1 = LANParty.part1(input)
        checkResult(res1, 1046)
        println(res1)
    }
    println("Time: $time")

    val time2 = measureTime {
        val res2 = LANParty.part2(input)
        checkResult(res2, "de,id,ke,ls,po,sn,tf,tl,tm,uj,un,xw,yz")
        println(res2)
    }
    println("Time: $time2")

}
