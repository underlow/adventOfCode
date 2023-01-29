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
    data class NodeLink(val node: String) : Operation

    data class Node(val name: String, var input: UInt?, val op: Operation?)

    fun part1(list: List<String>, finalNode: String): UShort {
        val graph = parseInput(list)
        val aNode = graph.find { it.name == finalNode }!!
        while (aNode.input == null) {
            graph.forEach { n ->
                val op = n.op
                when (op) {
                    is And -> {
                        val l = when (op.left) {
                            is Link -> graph.find { it.name == op.left.node }?.input
                            is Number -> op.left.value
                        }
                        val r = when (op.right) {
                            is Link -> graph.find { it.name == op.right.node }?.input
                            is Number -> op.right.value
                        }

                        if (l != null && r != null) {
                            n.input = l and r
                        }
                    }

                    is LShift -> {
                        val node = when (op.node) {
                            is Link -> graph.find { it.name == op.node.node }?.input
                            is Number -> op.node.value
                        }


                        if (node != null) {
                            n.input = node shl op.shift
                        }
                    }

                    is Not -> {
                        val node = when (op.operand) {
                            is Link -> graph.find { it.name == op.operand.node }?.input
                            is Number -> op.operand.value
                        }


                        if (node != null) {
                            n.input = node.inv()
                        }
                    }

                    is Or -> {
                        val l = when (op.left) {
                            is Link -> graph.find { it.name == op.left.node }?.input
                            is Number -> op.left.value
                        }
                        val r = when (op.right) {
                            is Link -> graph.find { it.name == op.right.node }?.input
                            is Number -> op.right.value
                        }
                        if (l != null && r != null) {
                            n.input = l or r
                        }
                    }

                    is RShift -> {
                        val node = when (op.node) {
                            is Link -> graph.find { it.name == op.node.node }?.input
                            is Number -> op.node.value
                        }

                        if (node != null) {
                            n.input = node shr op.shift
                        }
                    }

                    is NodeLink -> {
                        val number = graph.find { it.name == op.node }?.input
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

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
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
                    if (split[0].toIntOrNull() != null)
                        Node(split[2], split[0].toUInt(), null)
                    else
                        Node(split[2], null, NodeLink(split[0]))

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
    val res2 = SomeAssemblyRequired.part2(input)

    checkResult(res1, 956)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
