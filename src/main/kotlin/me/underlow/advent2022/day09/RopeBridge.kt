package me.underlow.advent2022.day09

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines
import kotlin.math.abs

// https://adventofcode.com/2022/day/9

data class Position(val x: Int, val y: Int) {
    operator fun minus(left: Position) = Position(this.x - left.x, this.y - left.y)
    operator fun plus(left: Position) = Position(this.x + left.x, this.y + left.y)
}

enum class Command {
    L,
    R,
    U,
    D;

    fun apply(position: Position): Position = when (this) {
        L -> position.copy(x = position.x - 1)
        R -> position.copy(x = position.x + 1)
        U -> position.copy(y = position.y + 1)
        D -> position.copy(y = position.y - 1)
    }
}

fun parseInput(input: List<String>): List<Command> {
    val list = mutableListOf<Command>()
    for (i in input) {
        val split = i.split(" ")
        val command = Command.valueOf(split[0])
        repeat(split[1].toInt()) {
            list.add(command)
        }
    }
    return list
}

fun moveTail(head: Position, tail: Position): Position {
    when {
        head.x == tail.x && head.y == tail.y + 2 -> return tail.copy(y = tail.y + 1)
        head.x == tail.x && head.y == tail.y - 2 -> return tail.copy(y = tail.y - 1)

        head.y == tail.y && head.x == tail.x + 2 -> return tail.copy(x = tail.x + 1)
        head.y == tail.y && head.x == tail.x - 2 -> return tail.copy(x = tail.x - 1)
        /*------------------------------------*/
        tail - head == Position(-1, -2) -> return tail + Position(1, 1)
        tail - head == Position(-2, -1) -> return tail + Position(1, 1)

        tail - head == Position(-2, 1) -> return tail + Position(1, -1)
        tail - head == Position(-1, 2) -> return tail + Position(1, -1)

        tail - head == Position(2, 1) -> return tail + Position(-1, -1)
        tail - head == Position(1, 2) -> return tail + Position(-1, -1)

        tail - head == Position(2, -1) -> return tail + Position(-1, 1)
        tail - head == Position(1, -2) -> return tail + Position(-1, 1)
        /*------------------*/
        abs(head.x - tail.x) < 2 && abs(head.y - tail.y) < 2 -> return tail

        else -> error("Incorrect input")
    }
}

fun moveTail2(head: Position, tail: Position): Position {
    when {
        head.x == tail.x && head.y == tail.y + 2 -> return tail.copy(y = tail.y + 1)
        head.x == tail.x && head.y == tail.y - 2 -> return tail.copy(y = tail.y - 1)

        head.y == tail.y && head.x == tail.x + 2 -> return tail.copy(x = tail.x + 1)
        head.y == tail.y && head.x == tail.x - 2 -> return tail.copy(x = tail.x - 1)
        /*------------------------------------*/
        tail - head == Position(-1, -2) -> return tail + Position(1, 1)
        tail - head == Position(-2, -1) -> return tail + Position(1, 1)

        tail - head == Position(-2, 1) -> return tail + Position(1, -1)
        tail - head == Position(-1, 2) -> return tail + Position(1, -1)

        tail - head == Position(2, 1) -> return tail + Position(-1, -1)
        tail - head == Position(1, 2) -> return tail + Position(-1, -1)

        tail - head == Position(2, -1) -> return tail + Position(-1, 1)
        tail - head == Position(1, -2) -> return tail + Position(-1, 1)
        /*------------------*/
        tail - head == Position(2, 2) -> return tail + Position(-1, -1)
        tail - head == Position(-2, -2) -> return tail + Position(1, 1)
        tail - head == Position(2, -2) -> return tail + Position(-1, 1)
        tail - head == Position(-2, 2) -> return tail + Position(1, -1)


        abs(head.x - tail.x) < 2 && abs(head.y - tail.y) < 2 -> return tail

        else -> error("Incorrect input")
    }
}

fun processCommands(commands: List<Command>): Set<Position> {
    var head = Position(0, 0)
    var tail = Position(0, 0)

    val set = mutableSetOf<Position>()

    commands.forEach { p ->
        set += tail
        head = p.apply(head)
        tail = moveTail(head, tail)
    }
    return set
}

fun processCommandsForArray(commands: List<Command>): Set<Position> {
    val knots = MutableList(10) { _ -> Position(0, 0) }

    val set = mutableSetOf<Position>()

    for (idx in commands.indices) {
        val command = commands[idx]
        set += knots[9]
        knots[0] = command.apply(knots[0])
        for (i in 0..8) {
            knots[i + 1] = moveTail2(knots[i], knots[i + 1])
            set += knots[9] // to not lost last move ( won't get to upper line on last move
        }
    }
    return set
}

fun solution1(input: List<String>): Int {
    val commands = parseInput(input)
    val set = processCommands(commands)
    return set.size + 1
}

fun solution2(input: List<String>): Int {
    val commands = parseInput(input)
    val set = processCommandsForArray(commands)
    return set.size
}

fun main() {
    val input = getResourceAsLines("/09 - RopeBridgeInput.txt")
    val result = solution1(input)
    val result2 = solution2(input)

    println(result)
    println(result2)

    checkResult(result, 6271)
    checkResult(result2, 2458)
}
