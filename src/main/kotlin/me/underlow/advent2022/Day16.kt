package me.underlow.advent2022


// https://adventofcode.com/2022/day/16
object ProboscideaVolcaniumInput {
    private data class Node(
        val name: String,
        val flow: Int,
        val connections: Set<String>
    )

    private data class NodePath(
        val node: Node,
        var opened: Boolean,
        var currentPath: Int,
        var visited: Boolean = false
    )

    private fun parseInput(list: List<String>): List<Node> {
        return list.map {
            val name = it.substring(6, 9).trim()
            val flow = it.substring(23, it.indexOfFirst { it == ';' }).toInt()
            val nodesSubstring = if (it.contains("to valves"))
                it.substring(it.indexOf("to valves ") + 10, it.length).split(",").map { it.trim() }
            else
                it.substring(it.indexOf("to valve ") + 9, it.length).split(",").map { it.trim() }

            return@map Node(name, flow, nodesSubstring.toSet())
        }
    }

    private data class Opener(val pressure: Int, val tick: Int, val node: Node)


    private fun solveBruteForce(nodes: List<Node>, totalTime: Int): Int {
        fun List<Opener>.totalPressure() = sumOf { it.pressure * (totalTime + 1 - it.tick) }
        val list = nodes.map { NodePath(it, false, 0) }

        val startNode = list.first { it.node.name == "AA" }

        val allPaths = calculateAllPaths(list)


        val allPermutations = findAllPermutations(list.filter { it.node.flow != 0 }, allPaths, totalTime, startNode)
        println("Found ${allPermutations.size} allPermutations")
        val p = allPermutations.map { p ->
            val perm = listOf(startNode) + p
            val pressure = mutableListOf<Opener>()
            var count = 1
            for (i in 1 until perm.size) {
                val path = allPaths[perm[i - 1].node.name]!![perm[i].node.name]!!
                count += (path + 1)
                if (count <= totalTime) {
                    pressure.add(Opener(pressure = perm[i].node.flow, tick = count, node = perm[i].node))
                }
            }
            if (pressure.totalPressure() == 1376) {
                println(pressure)
            }

            return@map pressure.totalPressure()
        }

//    val map = p.map { it.filter { it.tick <= 30 } }.sortedBy { it.totalPressure() }
//    println("Last: ${map.last()} : ${map.last().totalPressure()}")
//    return map.maxOf { it.totalPressure() }
        return p.maxOrNull()!!
    }

    private fun solveBruteForce2(nodes: List<Node>, totalTime: Int): Int {
        fun List<Opener>.totalPressure() = sumOf { it.pressure * (totalTime + 1 - it.tick) }
        val list = nodes.map { NodePath(it, false, 0) }

        val startNode = list.first { it.node.name == "AA" }

        val allPaths = calculateAllPaths(list)


        val allPermutations = findAllPermutations(list.filter { it.node.flow != 0 }, allPaths, totalTime, startNode)
        println("Found ${allPermutations.size} allPermutations")
        val p = allPermutations.map { p ->
            val perm = listOf(startNode) + p
            val pressure = mutableListOf<Opener>()
            var count = 1
            for (i in 1 until perm.size) {
                val path = allPaths[perm[i - 1].node.name]!![perm[i].node.name]!!
                count += (path + 1)
                if (count <= totalTime) {
                    pressure.add(Opener(pressure = perm[i].node.flow, tick = count, node = perm[i].node))
                }
            }


            return@map pressure.filter { it.node.name !in setOf("HS", "EG", "TJ", "HO", "IQ") }.totalPressure()
//        return@map PressureSet(pressure.totalPressure(), pressure.map { it.node.name }.toSet())
        }


//    return p.maxOf { it.total }
        return p.maxOrNull()!!
    }

    private fun calculateAllPaths(list: List<NodePath>): Map<String, Map<String, Int>> {
        val map = mutableMapOf<String, MutableMap<String, Int>>()

        for (i in list.indices) {
            val paths = list.calculatePaths(list[i])
            map[list[i].node.name] = paths.associate { it.node.name to it.currentPath }.toMutableMap()
        }
        for (i in list) {
            map[i.node.name]!![i.node.name] = 0
        }
//    map[list[0].node.name]!![list[0].node.name] = 0
        return map
    }

    private fun findAllPermutations(
        filter: List<NodePath>,
        allPaths: Map<String, Map<String, Int>>,
        countRemaining: Int,
        begin: NodePath
    ): List<List<NodePath>> {

        if (filter.size == 1) return listOf(filter)
        val permutations = mutableListOf<List<NodePath>>()
        for (i in filter.indices) {
            val node = filter[i]
            val pathToNode = allPaths[begin.node.name]!![node.node.name]!!

            if (countRemaining - pathToNode < 0) {
                permutations.add(listOf(node))
            } else {
                val remaining = filter.toMutableList()
                remaining.removeAt(i)
                val remainingPermutations = findAllPermutations(remaining, allPaths, countRemaining - pathToNode, node)
                for (permutation in remainingPermutations) {
                    permutations.add(listOf(node) + permutation)
                }
            }
        }
        return permutations
    }


    private fun List<NodePath>.calculatePaths(start: NodePath): List<NodePath> {

        val set = mutableSetOf<NodePath>().apply { add(start) }
        forEach { it.currentPath = 0 }
        while (set.isNotEmpty()) {
            val newSet = set.map { node ->
                node.makeStep(this)
            }.flatten()
            set.clear()
            set.addAll(newSet)
        }
        return this
    }

    // returns all newly visited nodes
    private fun NodePath.makeStep(allNodes: List<NodePath>): List<NodePath> {
        return this.node.connections.map { connection ->
            // find node
            val np = allNodes.first { it.node.name == connection }

            if (np.currentPath == 0) {
                np.currentPath = this.currentPath + 1
                return@map np
            } else {
                if (this.currentPath + 1 > np.currentPath) {
                    return@map null // was there already
                } else {
                    np.currentPath = this.currentPath + 1
                    return@map np
                }
            }
        }.filterNotNull()

    }


    fun solution1(list: List<String>): Int {
        val nodes = parseInput(list)
        return solveBruteForce(nodes, 30)
//    return solveHeuristic(nodes)
    }

    fun solution2(list: List<String>): Int {
        val nodes = parseInput(list)
        return solveBruteForce2(nodes, 26)
    }
}

fun main() {
    val input = readInput("${pathPrefix}/day16.txt")
    val result = ProboscideaVolcaniumInput.solution1(input)
    println(result)

    // 1048 is solution1 for 26 ticks, idea here is that we take not intercepting ways, won't work for another input
    val result2 = ProboscideaVolcaniumInput.solution2(input) + 1048
    println(result2)

    checkResult(result, 1376)
    checkResult(result2, 1933)
}
