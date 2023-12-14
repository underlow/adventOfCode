package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PointofIncidenceTest {
    @Test
    fun possibleTest() {
        val mirrors = PointofIncidence.parseInput(input.split("\n"))
        val columnCounts = PointofIncidence.columnCounts(mirrors[0])
        val rowCounts = PointofIncidence.rowCounts(mirrors[0])

        assertArrayEquals(columnCounts.toTypedArray(), arrayOf(4, 2, 5, 2, 3, 3, 2, 5, 2))
        assertArrayEquals(rowCounts.toTypedArray(), arrayOf(5, 4, 3, 3, 4, 4, 5))

        val pc = PointofIncidence.possibleMirrors(columnCounts)

        assertArrayEquals(pc.toTypedArray(), arrayOf(5))

        val pr = PointofIncidence.possibleMirrors(rowCounts)
        assertArrayEquals(pr.toTypedArray(), arrayOf())
    }

    @Test
    fun testPart1() {
        val result = PointofIncidence.part1(input.split("\n"))
        assertEquals(405, result)
    }

    @Test
    fun testPart2() {
        val result = PointofIncidence.part2(input.split("\n"))
        assertEquals(400, result)
    }

    @Test
    fun testPart22() {
        val result = PointofIncidence.part2(input2.split("\n"))
        assertEquals(1300, result)
    }
}

private val input = """
    #.##..##.
    ..#.##.#.
    ##......#
    ##......#
    ..#.##.#.
    ..##..##.
    #.#.##.#.

    #...##..#
    #....#..#
    ..##..###
    #####.##.
    #####.##.
    ..##..###
    #....#..#
""".trimIndent()

private val input2 = """
#..#..#.###
###....####
##..##.#.#.
##..##.#.#.
###....####
#..#..#.###
##.##..##.#
#...#.....#
...##.###.#
####.#.##..
.#.###.#...
####..#...#
#.#.#####..
#.#.#####..
####..#....
.#.###.#...
####.#.##..
""".trimIndent()
