package me.underlow.advent2022.day21

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines

object MonkeyMath {
    enum class Branch { Left, Right }

    enum class Operation {
        Plus, Minus, Multiply, Divide;

        companion object {
            fun fromString(s: String): Operation = when (s) {
                "+" -> Plus
                "-" -> Minus
                "*" -> Multiply
                "/" -> Divide
                else -> error("Incorrect input")
            }
        }
    }


    data class MonkeyInput(val name: String, val op: String)


    sealed interface MonkeyYell {
        val id: String

        fun calculateValue(): Long {
            return when (this) {
                is MonkeyNumber -> this.n
                is MonkeyOp -> {
                    return when (this.op) {
                        Operation.Plus -> left.calculateValue() + right.calculateValue()
                        Operation.Minus -> left.calculateValue() - right.calculateValue()
                        Operation.Multiply -> left.calculateValue() * right.calculateValue()
                        Operation.Divide -> left.calculateValue() / right.calculateValue()
                    }
                }
            }
        }

        fun contains(id: String): Boolean = when (this) {
            is MonkeyNumber -> this.id == id
            is MonkeyOp -> this.id == id || left.contains(id) || right.contains(id)
        }

        fun find(id: String): MonkeyYell? {
            if (this.id == id) return this

            if (this is MonkeyOp) {
                return this.left.find(id) ?: this.right.find(id)
            }
            return null
        }
    }

    data class MonkeyNumber(override val id: String, var n: Long) : MonkeyYell
    data class MonkeyOp(
        override val id: String,
        var left: MonkeyYell,
        var right: MonkeyYell,
        val op: Operation
    ) : MonkeyYell

    fun solvePart1(list: List<String>): Long {
        val rootNode = parseInput(list)

        return rootNode.calculateValue()
    }

    fun solvePart2(list: List<String>): Long {
        val rootNode = parseInput(list) as MonkeyOp // true for input data
        val you = "humn"

        val youNode = rootNode.find(you) as MonkeyNumber // true by task conditions

        val branchWithYou = if (rootNode.left.contains(you)) rootNode.left else rootNode.right
        val otherBranchValue =
            if (rootNode.left.contains(you)) rootNode.right.calculateValue() else rootNode.left.calculateValue()


        return findDesiredValue(branchWithYou as MonkeyOp, youNode, otherBranchValue)
    }

    private fun findDesiredValue(rootNode: MonkeyOp, youNode: MonkeyNumber, desiredNumber: Long): Long {
        val otherBranchValue = when {
            rootNode.left.contains(youNode.id) -> rootNode.right.calculateValue()
            else -> rootNode.left.calculateValue()
        }
        val youSide = when {
            rootNode.left.contains(youNode.id) -> Branch.Left
            else -> Branch.Right
        }

        val d = when (rootNode.op) {
            Operation.Plus -> desiredNumber - otherBranchValue
            Operation.Minus -> if (youSide == Branch.Left) desiredNumber + otherBranchValue else otherBranchValue - desiredNumber
            Operation.Multiply -> desiredNumber / otherBranchValue
            Operation.Divide -> if (youSide == Branch.Left) desiredNumber * otherBranchValue else otherBranchValue / desiredNumber
        }

        if (rootNode.left == youNode || rootNode.right == youNode)
            return d

        val branchWithYou =
            if (rootNode.left.contains(youNode.id))
                rootNode.left as MonkeyOp
            else
                rootNode.right as MonkeyOp

        return findDesiredValue(branchWithYou, youNode, d)
    }

    private fun parseInput(list: List<String>): MonkeyYell {
        val input = list.map {
            val split = it.split(":")
            return@map MonkeyInput(split[0], split[1].trim())
        }

        return parseMonkey(input, "root")
    }

    private fun parseMonkey(input: List<MonkeyInput>, id: String): MonkeyYell {
        val s = input.first { it.name == id }

        if (s.op.toIntOrNull() != null) return MonkeyNumber(s.name, s.op.toLongOrNull()!!)

        val split = s.op.split(" ")
        val op = Operation.fromString(split[1].trim())
        val left = parseMonkey(input, split[0])
        val right = parseMonkey(input, split[2])

        return MonkeyOp(s.name, left, right, op)
    }

}


fun main() {
    val input = getResourceAsLines("/21 - MonkeyMathInput.txt")
    val res1 = MonkeyMath.solvePart1(input)
    val res2 = MonkeyMath.solvePart2(input)

    checkResult(res1, 22382838633806)
    checkResult(res2, 3099532691300)

    println(res1)
    println(res2)
}

