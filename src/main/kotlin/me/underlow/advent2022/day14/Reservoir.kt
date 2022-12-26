package me.underlow.advent2022.day14

import me.underlow.advent2022.day14.RegolithContent
import kotlin.math.max

/**
 * class which stores matrix with value of type Content
 * x - horizontal axis
 * y - vertical axis and goes down from 0
 *
 */
class Reservoir() {

    private val reservoir: Array<Array<RegolithContent>> = Array(1000) { Array(1000) { RegolithContent.Empty } }

    init {
        for (i in reservoir.indices) {
            for (j in reservoir[i].indices) {
                reservoir[i][j] = RegolithContent.Empty
            }
        }
    }

    val xSize = reservoir[0].size

    operator fun get(x: Int, y: Int): RegolithContent {
        return reservoir[y][x]
    }

    operator fun set(x: Int, y: Int, value: RegolithContent) {
        reservoir[y][x] = value
    }

    fun countSandInTheReservoir(): Int {
        var ret = 0
        for (i in reservoir.indices) {
            for (j in reservoir[i].indices) {
                if (reservoir[i][j] == RegolithContent.Sand) {
                    ret++
                }
            }
        }
        return ret
    }

    fun dump(x: IntRange, y: IntRange) {
        for (j in y) {
            for (i in x) {
                print(reservoir[j][i].c)
            }
            println()
        }
    }

    fun findNextStep(sand: Point): NextStepResult {
        var next = sand.down()

        // check if we are out of array
        if (!this.inBounds(next)) {
            return NextStepResult(next, isOut = true, stop = false)
        }

        // check if there's place in reservoir
        if (this[next.x, next.y] == RegolithContent.Empty) {
            return NextStepResult(next, isOut = false, stop = false)
        }

        // check if there's a wall on the down-left and down-right
        next = sand.down().left()

        // check if we are out of array
        if (!this.inBounds(next)) {
            return NextStepResult(next, isOut = true, stop = false)
        }

        if (this[next.x, next.y] == RegolithContent.Empty) {
            return NextStepResult(next, isOut = false, stop = false)
        }

        // check if there's a wall on the right-left and down-right
        next = sand.down().right()

        // check if we are out of array
        if (!this.inBounds(next)) {
            return NextStepResult(next, isOut = true, stop = false)
        }

        if (this[next.x, next.y] == RegolithContent.Empty) {
            return NextStepResult(next, isOut = false, stop = false)
        }
        // can't make a move
        return NextStepResult(sand, isOut = false, stop = true)
    }

    private fun inBounds(next: Point) =
        next.y in this.reservoir[0].indices
                && next.x in this.reservoir.indices

    // lovest Y is actually the max y when Rock is present
    fun lowestY(): Int {
        var ret = 0
        for (j in reservoir[0].indices) {
            ret = max(ret, if (reservoir[j].any { it == RegolithContent.Rock }) j else 0)
        }
        return ret
    }


    data class NextStepResult(val next: Point, val isOut: Boolean, val stop: Boolean)

    data class Point(val x: Int, val y: Int) {
        fun up() = Point(x, y - 1)
        fun down() = Point(x, y + 1)
        fun left() = Point(x - 1, y)
        fun right() = Point(x + 1, y)
    }

}
