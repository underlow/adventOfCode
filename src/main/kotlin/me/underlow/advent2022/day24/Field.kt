package me.underlow.advent2022.day24


sealed interface CellState

// blizzard here on these steps
data class Blizzards(val steps: MutableSet<Int>) : CellState
object Wall : CellState

class Field(input: Array<Array<Char>>) {
    private val field: Array<Array<CellState>>
    private val sizeX: Int
    private val sizeY: Int


    init {
        sizeX = input.size
        sizeY = input[0].size
        field = Array(sizeX) { Array(sizeY) { Blizzards(mutableSetOf()) } }

        input.forEachIndexed { x, chars ->
            chars.forEachIndexed { y, c ->
                when (c) {
                    '#' -> field[x][y] = Wall
                    '<' -> addLeftBlizzards()
                    '>' -> addRightBlizzards()
                    'v' -> addDownBlizzards()
                    '^' -> addTopBlizzards()

                }
            }

        }
    }

    private fun addTopBlizzards() {
        TODO("Not yet implemented")
    }

    private fun addLeftBlizzards() {
        TODO()
    }

    private fun addRightBlizzards() {
        TODO("Not yet implemented")
    }

    private fun addDownBlizzards() {
        TODO("Not yet implemented")
    }

}
