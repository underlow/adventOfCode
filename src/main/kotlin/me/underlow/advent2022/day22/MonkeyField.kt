package me.underlow.advent2022.day22

import me.underlow.advent2022.Point
import me.underlow.advent2022.day22.Teleport.findTeleport

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
    operator fun get(p: Point) = get(p.x, p.y)
    operator fun set(x: Int, y: Int, c: Char) {
        map[x][y] = FieldCell.fromString(c)
    }

    operator fun set(x: Int, y: Int, c: FieldCell) {
        map[x][y] = c
    }

    fun findStart(): Point {
        for (y in 0 until sizeY)
            if (get(0, y) == FieldCell.Empty) return Point(0, y)

        error("Cannot find start")
    }

    // find next point (either requested step or wrap around tha map) or null if move is impossble
    fun nextPossibleStep(point: Point, direction: Point): Point? {
        fun step(p: Point, d: Point) =
            Point((sizeX + p.x + d.x) % sizeX, (sizeY + p.y + d.y) % sizeY)

        var nextPoint = step(point, direction)

        while (get(nextPoint) == FieldCell.OuterWall)
            nextPoint = step(nextPoint, direction)

        require(get(nextPoint) != FieldCell.OuterWall) {
            "We got outside of the field"
        }

        if (get(nextPoint) == FieldCell.Wall)
            return null // cannot move
        // can move
        return nextPoint
    }

    // part 2
    fun nextPossibleStepOnCube(point: Point, direction: Point): Pair<Point, List<MonkeyOp>>? {
        fun step(p: Point, d: Point) =
            Point((sizeX + p.x + d.x) % sizeX, (sizeY + p.y + d.y) % sizeY)

        var nextPoint = step(point, direction)

        if (get(nextPoint) == FieldCell.OuterWall) {
            val teleportData = findTeleport(point)
            val retPoint = teleportData.op(point)

            require(get(retPoint) != FieldCell.OuterWall) {
                "We got outside of the field"
            }
            return retPoint to teleportData.rotation
        }

        require(get(nextPoint) != FieldCell.OuterWall) {
            "We got outside of the field"
        }

        if (get(nextPoint) == FieldCell.Wall)
            return null // cannot move
        // can move
        return nextPoint to emptyList()
    }
}


