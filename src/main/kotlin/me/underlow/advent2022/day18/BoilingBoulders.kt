package me.underlow.advent2022.day18

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.getResourceAsLines
import kotlin.math.abs

// https://adventofcode.com/2022/day/18

private data class Point(val x: Int, val y: Int, val z: Int) {
    fun findNeighbours(): List<Point> {
        return listOf(
            Point(x - 1, y, z),
            Point(x + 1, y, z),
            Point(x, y - 1, z),
            Point(x, y + 1, z),
            Point(x, y, z - 1),
            Point(x, y, z + 1),
        )
    }

    fun shift(p: Point): Point {
        return Point(x + p.x, y + p.y, z + p.z)
    }

    fun shift(x: Int, y: Int, z: Int): Point {
        return Point(this.x + x, this.y + y, this.z + z)
    }

    fun inRange(range: Point): Boolean {
        return x >= 0 && y >= 0 && z >= 0 && range.x > x && range.y > y && range.z > z
    }
}

private data class Cube(
    val point: Point,
    var sidesExposed: Int = 6,
    var visible: Boolean = false,
    val invisibleSides: MutableSet<Point> = mutableSetOf(
        Point(0, 0, 1),
        Point(0, 0, -1),
        Point(0, 1, 0),
        Point(0, -1, 0),
        Point(1, 0, 0),
        Point(-1, 0, 0),
    )
)

private fun distance(p1: Point, p2: Point): Int {
    return abs(p1.x - p2.x) + abs(p1.y - p2.y) + abs(p1.z - p2.z)
}

fun solvePart1(list: List<String>): Long {
    val cubes = parseInput(list)

    cubes.findExposedSurface()

    return cubes.sumOf { it.sidesExposed.toLong() }
}

private fun List<Cube>.findExposedSurface() {
    for (i in indices) {
        for (j in indices) {
            if (i == j) continue
            if (distance(this[i].point, this[j].point) == 1) {
                this[i].sidesExposed--
            }
        }
    }
}


fun solvePart2(list: List<String>): Long {
    val cubes = parseInput(list)
    cubes.findExposedSurface()
    val bounds = BouldersArea(cubes)
    bounds.findVisibleCubes()
    return cubes.sumOf { 6 - it.invisibleSides.size.toLong() }
}

private fun parseInput(list: List<String>): List<Cube> {
    return list.map { line ->
        val (x, y, z) = line.split(",").map { it.toInt() }
        Cube(Point(x, y, z))
    }
}


private class BouldersArea(val cubes: List<Cube>) {
    enum class AreaState {
        Empty, Visited, Cube
    }

    private var area: Array<Array<Array<AreaState>>>

    private val shiftX: Int
    private val shiftY: Int
    private val shiftZ: Int

    private val range: Point
    private val shift: Point

    init {
        val (min, max) = cubes.map { it.point }.findSurroundingArea()

        // +2 required to keep the area big enough to fit all the cubes and keep 1 empty cube plane in each direction
        range = Point(max.x - min.x + 4, max.y - min.y + 4, max.z - min.z + 4)

        area = Array(range.x) { Array(range.y) { Array(range.z) { AreaState.Empty } } }

        shiftX = min.x - 1
        shiftY = min.y - 1
        shiftZ = min.z - 1
        shift = Point(shiftX, shiftY, shiftZ)

        cubes.forEach { cube ->
            area[cube.point.x - shiftX][cube.point.y - shiftY][cube.point.z - shiftZ] = AreaState.Cube
            println("Cube at ${cube.point} become ${cube.point.shift(-shiftX, -shiftY, -shiftZ)}")
        }
    }

    private fun List<Point>.findSurroundingArea(): Pair<Point, Point> {
        val minX = this.minOf { it.x }
        val minY = this.minOf { it.y }
        val minZ = this.minOf { it.z }
        val maxX = this.maxOf { it.x }
        val maxY = this.maxOf { it.y }
        val maxZ = this.maxOf { it.z }
        val min = Point(minX, minY, minZ)
        val max = Point(maxX, maxY, maxZ)
        return Pair(min, max)
    }

    fun findVisibleCubes() {
        data class PointStep(val from: Point, val to: Point)
        val queue = mutableListOf<PointStep>()
        // won't work for any input, (0,0,0) can be isolated from other free zones.
        queue.add(PointStep(Point(0, 0, 0), Point(0, 0, 0)))
        while (queue.isNotEmpty()) {
            val point = queue.removeFirst()

//            print("Visiting $point")

            if (area[point.to.x][point.to.y][point.to.z] == AreaState.Visited) {
//                println(" - Visited, skipping")
                continue
            }

            if (area[point.to.x][point.to.y][point.to.z] == AreaState.Cube) {
                if (point.to == Point(3, 2, 2)) {
                    println("Come to ${point.to} from ${point.from}")
                }
                val cube = cubes.find { it.point == point.to.shift(shift) }
                val removed = cube?.invisibleSides?.removeIf {
                    point.from.shift(it) == point.to
                }
                println(" - Cube at ${cube?.point}, ${if (removed != null && removed) "removed by " + point.from.shift(shift) else "NOT removed"}")
                continue
            }


            area[point.to.x][point.to.y][point.to.z] = AreaState.Visited
            if (point.to.shift(shift) == Point(4, 4, 4)) {
                println(" $point")
            }
            val neighbours = point.to.findNeighbours()
            neighbours.forEach { neighbour ->
                if (neighbour.inRange(range) && area[neighbour.x][neighbour.y][neighbour.z] != AreaState.Visited) {
                    queue.add(PointStep(point.to, neighbour))
                }
            }
//            println()
        }
    }

}


fun main() {
    val input = getResourceAsLines("/18 - BoilingBouldersInput.txt")
    val res1 = solvePart1(input)
    val res2 = solvePart2(input)

    println(res1)
    println(res2)

    checkResult(res1, 3530)
    checkResult(res2, 2000)

}
