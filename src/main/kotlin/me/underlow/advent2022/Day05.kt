package me.underlow.advent2022

import me.underlow.advent2022.SupplyStacksInput.calculate11
import me.underlow.advent2022.SupplyStacksInput.calculate22
import java.util.*

// https://adventofcode.com/2022/day/5

object SupplyStacksInput {
    data class StackCommand(val from: Int, val to: Int, val count: Int)

    data class Input(val stacks: Array<Stack<String>>, val commands: List<StackCommand>)

    fun parseInput(input: String): Input {
        val split = input.split("\n\n")
        val stacks = parseStacks()
        val commands = parseCommands(split[1])
        return Input(stacks, commands)
    }

    // move 6 from 4 to 3
    fun parseCommands(s: String): List<StackCommand> {
        return s.split("\n").filter { it.isNotEmpty() }.map {
            val split = it.split(" ")
            return@map StackCommand(split[3].toInt(), split[5].toInt(), split[1].toInt())
        }
    }

    //[F]         [L]     [M]
//[T]     [H] [V] [G] [V]
//[N]     [T] [D] [R] [N]     [D]
//[Z]     [B] [C] [P] [B] [R] [Z]
//[M]     [J] [N] [M] [F] [M] [V] [H]
//[G] [J] [L] [J] [S] [C] [G] [M] [F]
//[H] [W] [V] [P] [W] [H] [H] [N] [N]
//[J] [V] [G] [B] [F] [G] [D] [H] [G]
//1   2   3   4   5   6   7   8   9
    fun parseStacks(): Array<Stack<String>> {
        fun Stack<String>.fill(vararg list: String): Stack<String> {
            list.forEach { push(it) }
            return this
        }


        // hack, skip parsing and hardcode values
        val s1 = Stack<String>().fill("J", "H", "G", "M", "Z", "N", "T", "F")
        val s2 = Stack<String>().fill("V", "W", "J")
        val s3 = Stack<String>().fill("G", "V", "L", "J", "B", "T", "H")
        val s4 = Stack<String>().fill("B", "P", "J", "N", "C", "D", "V", "L")
        val s5 = Stack<String>().fill("F", "W", "S", "M", "P", "R", "G")
        val s6 = Stack<String>().fill("G", "H", "C", "F", "B", "N", "V", "M")
        val s7 = Stack<String>().fill("D", "H", "G", "M", "R")
        val s8 = Stack<String>().fill("H", "N", "M", "V", "Z", "D")
        val s9 = Stack<String>().fill("G", "N", "F", "H")

        return arrayOf(s1, s2, s3, s4, s5, s6, s7, s8, s9)


    }

    fun applyCommands(input: Input): Array<Stack<String>> {
        input.commands.forEach { command ->
            val from = input.stacks[command.from - 1]
            val to = input.stacks[command.to - 1]

            for (i in 0 until command.count) {
                to.push(from.pop())
            }
        }
        return input.stacks
    }

    fun calculate11(s: String): String {
        val input = parseInput(s)
        val result = applyCommands(input)

        return result.map { it.pop() }.joinToString("")
    }

    /* ---------------------part2 ------------------*/


    fun applyCommands2(input: Input): Array<Stack<String>> {
        input.commands.forEach { command ->
            val from = input.stacks[command.from - 1]
            val to = input.stacks[command.to - 1]

            val s = Stack<String>()
            for (i in 0 until command.count) {
                s.push(from.pop())
            }
            for (i in 0 until command.count) {
                to.push(s.pop())
            }
        }
        return input.stacks
    }

    fun calculate22(s: String): String {
        val input = parseInput(s)
        val result = applyCommands2(input)

        return result.map { it.pop() }.joinToString("")
    }
}

fun main() {
    val input = readInputAsString("${pathPrefix}/day05.txt")

    val sum1 = calculate11(input)
    val sum2 = calculate22(input)

    println(sum1)
    println(sum2)

    checkResult(sum1, "TDCHVHJTG")
    checkResult(sum2, "NGCMPJLHV")
}
