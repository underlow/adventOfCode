package me.underlow.advent2022.day23

import me.underlow.advent2022.CircularProvider
import me.underlow.advent2022.Point
import kotlin.math.abs

data class Proposal(val id: String, val move: Point, val check: Set<Point>)

data class Elf(val id: String, var coord: Point)

class ElfContainer(private val elfList: List<Elf>, private val directions: CircularProvider<Proposal>) {
    private fun getElvesAtCoords(proposals: List<Point>): Set<Elf> {

        return proposals.map { p ->
            val x = xCoord[p.x] ?: emptySet()
            val y = yCoord[p.y] ?: emptySet()
            return@map x.intersect(y)
        }.flatten().toSet()

//        return elfList.filter { it.coord in proposals }.toSet()
    }

    private val xCoord: MutableMap<Int, MutableSet<Elf>> = mutableMapOf()
    private val yCoord: MutableMap<Int, MutableSet<Elf>> = mutableMapOf()

    init {
        elfList.forEach { elf ->
            xCoord.getOrPut(elf.coord.x) { mutableSetOf() }.add(elf)
            yCoord.getOrPut(elf.coord.y) { mutableSetOf() }.add(elf)
        }
    }

    fun makeRound(): Int {
        val proposals = elfList.mapNotNull {
            if (hasNoNeighbours(it))
                return@mapNotNull null
            val proposal = proposal(it)
                ?: return@mapNotNull null

            it to proposal
        }.toMap()

        val ableMove = proposals.filterSameTargetPoints()

        ableMove.entries.forEach { (elf, point) ->
            xCoord[elf.coord.x]!!.remove(elf)
            yCoord[elf.coord.y]!!.remove(elf)
            elf.coord += point
            xCoord.getOrPut(elf.coord.x) { mutableSetOf() }.add(elf)
            yCoord.getOrPut(elf.coord.y) { mutableSetOf() }.add(elf)
        }

        directions.next()

        require(elfList.map { it.coord }.toSet().size == elfList.size) {
            "Ops, some elves collided"
        }
//        println(elfList.filter { it.id == "g" }.firstOrNull())
        return ableMove.size
    }

    private fun Map<Elf, Point>.filterSameTargetPoints(): Map<Elf, Point> {
        val map = mutableMapOf<Point, MutableSet<Elf>>()

        forEach { (elf, point) ->
            val finalPoint = elf.coord + point
            map.getOrPut(finalPoint) { mutableSetOf() }.add(elf)
        }
        return map.filter { it.value.size == 1 }
            .map { it.value.first() to this@filterSameTargetPoints[it.value.first()]!! }.toMap()
    }

    private val neighbourPoints = listOf(
        Point(-1, -1),
        Point(-1, 0),
        Point(-1, 1),
        Point(0, -1),
        Point(0, 1),
        Point(1, -1),
        Point(1, 0),
        Point(1, 1),
    )

    private fun findElfByPoint(p: Point) : Elf? {
        val x = xCoord[p.x] ?: return null
        val y = yCoord[p.y]?: return null

        val int = x.intersect(y)
        require(int.size <= 1){
            "Seems like we have two elves at the same position"
        }
        return int.firstOrNull()
    }

    private fun hasNoNeighbours(elf: Elf): Boolean {
        val neighbours = neighbourPoints.map { it + elf.coord }
        return neighbours.mapNotNull { findElfByPoint(it) }.isEmpty()
    }

    fun calculateEmptySpace(): Int {
        val bounds = findMinField()

        return abs(bounds.second.x - bounds.first.x + 1) * abs(bounds.second.y - bounds.first.y + 1) - elfList.size
    }

    private fun proposal(elf: Elf): Point? {
        directions.toList().forEach { proposal ->
            if (getElvesAtCoords(proposal.check.map { it + elf.coord }).isEmpty()) {
                return proposal.move
            }
        }
        return null
    }

    private fun findMinField(): Pair<Point, Point> {
        val left = elfList.minOfOrNull { it.coord.y }!!
        val right = elfList.maxOfOrNull { it.coord.y }!!
        val top = elfList.minOfOrNull { it.coord.x }!!
        val bottom = elfList.maxOfOrNull { it.coord.x }!!

        return Point(top, left) to Point(bottom, right)
    }

    @Suppress("unused")
    fun dump() {
        val bounds = findMinField()
        val arr = Array(bounds.second.y - bounds.first.y + 1) { Array((bounds.second.x - bounds.first.y + 1)) { '.' } }

        elfList.forEach {

            arr[it.coord.y - bounds.first.y][it.coord.x - bounds.first.x] = it.id[0]

        }

        for (i in arr[0].indices) {
            for (j in arr.indices) {
                print(arr[j][i])
            }
            println()

        }
        println()
    }
}

