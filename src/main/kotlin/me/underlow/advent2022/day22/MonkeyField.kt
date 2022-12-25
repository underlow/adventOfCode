package me.underlow.advent2022.day22

import me.underlow.advent2022.Point

enum class FieldCell(val c: Char) {
    OuterWall(' '), Wall('#'), Empty('.');

    companion object {
        fun fromString(c: Char) = values().find { it.c == c } ?: error("Incorrect input")
    }

}

class MonkeyField(input: Array<Array<Char>>) {
    private val map: Array<Array<FieldCell>>
    private val sizeX: Int
    private val sizeY: Int

    init {
        sizeX = input.size
        sizeY = input.maxOf { it.size }
        map = Array(sizeX) { Array(sizeY) { FieldCell.OuterWall } }

        for (x in 0 until sizeX)
            for (y in 0 until sizeY) {
                val c = if (y < input[x].size) input[x][y] else ' '
                map[x][y] = FieldCell.fromString(c)
            }
    }

    operator fun get(x: Int, y: Int) = map[x][y]
    operator fun get(p: Point): FieldCell {
        if (p.x !in 0 until sizeX || p.y !in 0 until sizeY)
            return FieldCell.OuterWall
        return get(p.x, p.y)
    }

    fun findStart(): Point {
        for (y in 0 until sizeY)
            if (get(0, y) == FieldCell.Empty) return Point(0, y)

        error("Cannot find start")
    }

    fun step(p: Point, d: Point) =
        Point((sizeX + p.x + d.x) % sizeX, (sizeY + p.y + d.y) % sizeY)

    fun step2(p: Point, d: Point) =
        Point(p.x + d.x, p.y + d.y)

}


