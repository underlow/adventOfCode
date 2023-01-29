package me.underlow.advent2015

import me.underlow.advent2022.checkResult

object SomeAssemblyRequired {

    sealed interface Value
    data class Link(val node: String) : Value
    data class Number(val value: UInt) : Value

    sealed interface Operation

    data class And(val left: Value, val right: Value) : Operation
    data class Or(val left: Value, val right: Value) : Operation
    data class LShift(val node: Value, val shift: Int) : Operation
    data class RShift(val node: Value, val shift: Int) : Operation
    data class Not(val operand: Value) : Operation
    data class NodeLink(val node: Value) : Operation

    data class Node(val name: String, var input: UInt?, var op: Operation?)

    private fun wireGraph(graph: List<Node>, finalNode: String): UShort {
        val aNode = graph.find { it.name == finalNode }!!

        fun Value.value(): UInt? {
            return when (this) {
                is Link -> graph.find { it.name == this.node }?.input
                is Number -> this.value
            }
        }

        while (aNode.input == null) {
            graph.forEach { n ->
                when (val op = n.op) {
                    is And -> {
                        val l = op.left.value()
                        val r = op.right.value()

                        if (l != null && r != null) {
                            n.input = l and r
                        }
                    }

                    is LShift -> {
                        val node = op.node.value()

                        if (node != null) {
                            n.input = node shl op.shift
                        }
                    }

                    is Not -> {
                        val node = op.operand.value()

                        if (node != null) {
                            n.input = node.inv()
                        }
                    }

                    is Or -> {
                        val l = op.left.value()
                        val r = op.right.value()

                        if (l != null && r != null) {
                            n.input = l or r
                        }
                    }

                    is RShift -> {
                        val node = op.node.value()

                        if (node != null) {
                            n.input = node shr op.shift
                        }
                    }

                    is NodeLink -> {
                        val number = op.node.value()

                        if (number != null) {
                            n.input = number
                        }
                    }

                    null -> {}// do nothing
                }
            }
        }
        return aNode.input!!.toUShort()
    }


    fun part1(list: List<String>, finalNode: String): UShort {
        val graph = parseInput(list)
        return wireGraph(graph, finalNode)
    }

    fun part2(list: List<String>, s: String, part1Result: UShort): UShort {
        val graph = parseInput(list)
        graph.find { it.name == "b" }!!.input = part1Result.toUInt()
        graph.find { it.name == "b" }!!.op = null
        return wireGraph(graph, s)
    }

    /**
     * 123 -> x
     * iw AND ix -> iz
     * jp RSHIFT 5 -> js
     * lb OR la -> lc
     * NOT cn -> co
     * kh LSHIFT 1 -> lb
     */
    private fun parseInput(list: List<String>): List<Node> {
        return list.map { item ->
            val split = item.split(" ")
            return@map when {
                item.contains("AND") -> Node(
                    split[4],
                    null,
                    And(left = parseValue(split[0]), right = parseValue(split[2]))
                )

                item.contains("OR") -> Node(
                    split[4],
                    null,
                    Or(left = parseValue(split[0]), right = parseValue(split[2]))
                )

                item.contains("NOT") -> Node(split[3], null, Not(operand = parseValue(split[1])))
                item.contains("RSHIFT") -> Node(
                    split[4],
                    null,
                    RShift(node = parseValue(split[0]), shift = split[2].toInt())
                )

                item.contains("LSHIFT") -> Node(
                    split[4],
                    null,
                    LShift(node = parseValue(split[0]), shift = split[2].toInt())
                )

                else -> {
                    Node(split[2], null, NodeLink(parseValue(split[0])))
                }
            }
        }
    }

    private fun parseValue(input: String): Value = when {
        input.toIntOrNull() != null -> Number(input.toUInt())
        else -> Link(input)
    }
}


fun main() {
    val input = readInput("$pathPrefix/day07.txt")
    val res1 = SomeAssemblyRequired.part1(input, "a")
    val res2 = SomeAssemblyRequired.part2(input, "a", res1)

    println(res1)
    println(res2)

    checkResult(res1, 956u)
    checkResult(res2, 40149u)

}
