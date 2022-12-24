package me.underlow.advent2022.day22

import me.underlow.advent2022.Point

sealed interface MonkeyOp
object Clockwise : MonkeyOp
object CounterClockwise : MonkeyOp
data class Move(val steps: Int) : MonkeyOp

class Direction(start: Int = 1){
    private val directions = listOf(
        Point(-1, 0),  // go up
        Point(0, +1),  // go right
        Point(+1, 0),  // go down
        Point(0, -1),  // go left
    )

    private var currentDirection = 1

    fun rotateCounterClockwise() {
        currentDirection = (directions.size + currentDirection - 1) % (directions.size)
    }

    fun rotateClockwise() {
        currentDirection = (currentDirection + 1) % (directions.size)
    }
    val current
        get() = directions[currentDirection]

    val code
        get () = (directions.size + currentDirection - 1) % directions.size
}

class Monkey(val field: MonkeyField, val isCube: Boolean = false) {
    private var position = field.findStart()
    private val direction = Direction()

    private fun applyOp(op: MonkeyOp) {
        when (op) {
            Clockwise -> direction.rotateClockwise()
            CounterClockwise -> direction.rotateCounterClockwise()
            is Move -> move(op)
        }
    }


    private fun move(move: Move) {
//        print("Move from $currentPosition with direction ${directions[currentDirection]}")
        repeat(move.steps) {
            position = if (isCube) {
                val next = field.nextPossibleStepOnCube(position, direction.current)
                if (next != null){
                    next.second.forEach { applyOp(it) }
                    next.first
                } else position
            } else
                field.nextPossibleStep(position, direction.current) ?: position
        }
//        println(" to $currentPosition")
    }

    fun followPath(path: MonkeyPath): Position {
        var step = path.nextMove()

        while (step != null) {
            applyOp(step)
            step = path.nextMove()
        }
        return finalPosition()
    }

    data class Position(val x: Int, val y: Int, val facingCode: Int)

    private fun finalPosition(): Position {
        return Position(
            position.x,
            position.y,
            direction.code
        )
    }
}

class MonkeyPath(input: String) {
    private val ops = mutableListOf<MonkeyOp>()
    private var idx: Int = 0

    init {
        var i = 0
        while (i < input.length) {
            if (input[i] == 'R') {
                ops.add(Clockwise)
                i++
                continue
            }
            if (input[i] == 'L') {
                ops.add(CounterClockwise)
                i++
                continue
            }
            // assume input always correct
            var j = i
            while (j < input.length && input[j].isDigit()) j++
            ops.add(Move(input.substring(i, j).toInt()))
            i = j
        }
    }

    fun nextMove() = if (idx < ops.size) ops[idx++] else null
}
