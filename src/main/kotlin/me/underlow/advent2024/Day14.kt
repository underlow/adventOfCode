package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object RestroomRedoubt {

    data class Point(val x: Int, val y: Int, val fieldX: Int, val fieldY: Int) {
        fun move(velocity: Point): Point {
            return Point(
                x = (fieldX + this.x + velocity.x) % fieldX,
                y = (fieldY + this.y + velocity.y) % fieldY,
                fieldX = this.fieldX,
                fieldY = this.fieldY
            )
        }
    }


    fun part1(list: List<String>, fieldX: Int, fieldY: Int): Int {

        val robots = parseInput(list, fieldX, fieldY)

        var r = robots.toList()

        repeat(100) {
            r = r.map { it.copy(point = it.point.move(it.velocity)) }
        }

        val res =
            r.filter { it.point.x in (0 until fieldX / 2) && it.point.y in (0 until fieldY / 2) }.size *
                    r.filter { it.point.x in (0 until fieldX / 2) && it.point.y in (fieldY / 2 + 1 until fieldY) }.size *
                    r.filter { it.point.x in (fieldX / 2 + 1 until fieldX) && it.point.y in (0 until fieldY / 2) }.size *
                    r.filter { it.point.x in (fieldX / 2 + 1 until fieldX) && it.point.y in (fieldY / 2 + 1 until fieldY) }.size

        return res
    }

    fun part2(list: List<String>, fieldX: Int, fieldY: Int): Int {


        val robots = parseInput(list, fieldX, fieldY)

        var r = robots.toList()

        var res = 0
        do {
            r = r.map { it.copy(point = it.point.move(it.velocity)) }
            res++
        } while (
        // wild guess. have no idea how to solve it without looking to visualization
            r.map { it.point }.toSet().size != r.size
        )

        return res
    }

    private fun parseInput(list: List<String>, fieldX: Int, fieldY: Int): List<Robot> {
        return list.mapIndexed { idx, line ->
            val startX = line.split(" ")[0].removePrefix("p=").split(',')[0].toInt()
            val startY = line.split(" ")[0].removePrefix("p=").split(',')[1].toInt()

            val vX = line.split(" ")[1].removePrefix("v=").split(',')[0].toInt()
            val vY = line.split(" ")[1].removePrefix("v=").split(',')[1].toInt()

            return@mapIndexed Robot(idx, Point(startX, startY, fieldX, fieldY), Point(vX, vY, fieldX, fieldY))
        }
    }

    data class Robot(val id: Int, val point: Point, val velocity: Point)

}


fun main() {
    val input = readInput("$pathPrefix24/day14.txt")
    val res1 = RestroomRedoubt.part1(input, 101, 103)
    val res2 = RestroomRedoubt.part2(input, 101, 103)

    checkResult(res1, 223020000)
    checkResult(res2, 7338)

    println(res1)
    println(res2)
}
