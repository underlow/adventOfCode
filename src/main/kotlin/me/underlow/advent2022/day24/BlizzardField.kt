package me.underlow.advent2022.day24

import me.underlow.advent2022.Point
import me.underlow.advent2022.nesw
import kotlin.math.min

/**
 * simple brute force solution. Probably create 3d space where z is time axis would be simpler and faster.
 */
sealed interface CellState

// blizzard here on these steps
data class Blizzard(val step: Int, val div: Int)
data class Blizzards(val steps: MutableSet<Blizzard>, val visitedAt: MutableSet<Int>) : CellState
object Wall : CellState {
    override fun toString(): String = "Wall"
}


class BlizzardField(input: Array<Array<Char>>) {
    private val field: Array<Array<CellState>>
    private val sizeX: Int
    private val sizeY: Int

    private val fieldX: Int
    private val fieldY: Int

    // not sure if it is correct for any input
    private val start = Point(0, 1)
    private val finish: Point

    init {
        sizeX = input.size
        sizeY = input[0].size
        fieldX = sizeX - 2
        fieldY = sizeY - 2
        finish = Point(sizeX - 1, sizeY - 2)
        field = Array(sizeX) { Array(sizeY) { Blizzards(mutableSetOf(), mutableSetOf()) } }

        input.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, c ->
                when (c) {
                    '#' -> field[x][y] = Wall
                    '<' -> addLeftBlizzards(x, y)
                    '>' -> addRightBlizzards(x, y)
                    'v' -> addDownBlizzards(x, y)
                    '^' -> addTopBlizzards(x, y)

                }
            }

        }
    }

    data class RoundState(
        val round: Int,
        val point: Point/*, val steps: MutableList<Pair<Point,Point>> = mutableListOf()*/
    )

    private fun findOnePath(startPoint: Point, finishPoint: Point, round: Int): Int {
        val states = mutableListOf<RoundState>().apply { add(RoundState(round, startPoint)) }
        var currentMinRounds = sizeX * sizeY + round
        while (states.isNotEmpty()) {
            val s = states.removeAt(states.size - 1)
            if (s.round > currentMinRounds)
                continue

            if (s.point == finishPoint) {
                if (currentMinRounds > s.round) {
                    println("Current path ${s.round} - ${s.point}")
                }
                currentMinRounds = min(currentMinRounds, s.round)
                continue
            }

            val nextSteps = nextStep(s)
            nextSteps.forEach { states.add(0, it) }
        }
        return currentMinRounds - 1
    }

    fun findPath(): Int {
        return findOnePath(start, finish, 0)
    }


    fun findPath2part(): Int {
        val one = findOnePath(start, finish, 0)
        field.cleanVisitedAt()
        val two = findOnePath(finish, start, one + 1)
        field.cleanVisitedAt()
        return findOnePath(start, finish, two + 1)
    }

    private fun Array<Array<CellState>>.cleanVisitedAt() {
        forEach {
            it.forEach {
                if (it is Blizzards) it.visitedAt.clear()
            }
        }
    }


    private fun nextStep(state: RoundState): List<RoundState> {
        if ((field[state.point.x][state.point.y] as Blizzards).visitedAt.contains(state.round))
            return emptyList()

        val possibleSteps = (listOf(state.point) + nesw.map { it + state.point })
            .filter { it.x in 0 until sizeX }.filter { it.y in 0 until sizeY }
            .filter {
                when (val cellState = field[it.x][it.y]) {
                    is Wall -> false
                    is Blizzards -> !(cellState.steps.any { state.round % it.div == it.step })
                }
            }
        (field[state.point.x][state.point.y] as Blizzards).visitedAt.add(state.round)
        return possibleSteps.map {
            RoundState(
                state.round + 1,
                it
            )
        }
    }

    private fun addTopBlizzards(x: Int, y: Int) {
        for (xx in 1 until sizeX - 1) {
            val f = field[xx][y]
            if (f is Blizzards) f.steps.add(Blizzard((fieldX + x - xx) % fieldX, fieldX))
        }
//        println("Top [$x,$y] ${(0 until sizeX).map { field[it][y] }}")
    }

    private fun addLeftBlizzards(x: Int, y: Int) {
        for (yy in 1 until sizeY - 1) {
            val f = field[x][yy]
            if (f is Blizzards) f.steps.add(Blizzard((fieldY + y - yy) % fieldY, fieldY))
        }
//        println("Left [$x,$y] ${(0 until sizeY).map { field[x][it] }}")
    }

    private fun addRightBlizzards(x: Int, y: Int) {
        for (yy in 1 until sizeY - 1) {
            val f = field[x][yy]
            if (f is Blizzards) f.steps.add(Blizzard((fieldY - y + yy) % fieldY, fieldY))
        }
//        println("Right [$x,$y] ${(0 until sizeY).map { field[x][it] }}")
    }

    private fun addDownBlizzards(x: Int, y: Int) {
        for (xx in 1 until sizeX - 1) {
            val f = field[xx][y]
            if (f is Blizzards) f.steps.add(Blizzard((fieldX - x + xx) % fieldX, fieldX))
        }
//        println("Down [$x,$y] ${(0 until sizeX).map { field[it][y] }}")
    }

    @Suppress("unused")
    fun dump(round: Int) {
        for (i in field[0].indices) {
            for (j in field.indices) {
                val state = field[j][i]
                val c = when (state) {
                    is Wall -> "#"
                    is Blizzards -> {
                        val b = state.steps.filter { it.step % it.div == round }
                        if (b.isEmpty()) '.' else 'v'
                    }
                }
                print(c)
            }
            println()
        }
        println()
    }
}

