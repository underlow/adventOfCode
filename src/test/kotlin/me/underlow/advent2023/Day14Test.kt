package me.underlow.advent2023

import me.underlow.advent2023.ParabolicReflectorDish.dump
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParabolicReflectorDishTest {
    @Test
    fun findCycleTest() {
        val l = listOf(1, 2, 3, 4, 5, 6, 7, 4, 5, 6, 7)
        val r = ParabolicReflectorDish.findCycle(l)
        assertEquals(3 to 4, r)
        val l2 = listOf(4, 5, 6, 7, 4, 5, 6, 7)
        val r2 = ParabolicReflectorDish.findCycle(l2)
        assertEquals(0 to 4, r2)
        val l3 = listOf(1, 2, 4, 5, 6, 7, 4, 5, 6, 7)
        val r3 = ParabolicReflectorDish.findCycle(l3)
        assertEquals(2 to 4, r3)
    }

    @Test
    fun testPart1() {
        val result = ParabolicReflectorDish.part1(input.split("\n"))
        assertEquals(136, result)
    }

    @Test
    fun testPart2() {
        val result = ParabolicReflectorDish.part2(input.split("\n"))
        assertEquals(64, result)
    }

    @Test
    fun testPart22() {
        val input = ParabolicReflectorDish.parseInput(input.split("\n"))
        val output = ParabolicReflectorDish.parseInput(c1.split("\n"))
        for (i in 0 until 1) {
            println("start")
            input.dump()
            ParabolicReflectorDish.north(input)
            println("north")
            input.dump()
            ParabolicReflectorDish.west(input)
            println("west")
            input.dump()
            ParabolicReflectorDish.south(input)
            println("south")
            input.dump()
            ParabolicReflectorDish.east(input)
            println("east")
            input.dump()

        }
        input.dump()
        println()
        output.dump()
        assertArrayEquals(output, input)
    }

    @Test
    fun findCycle() {
    }
}

private val input = """
    O....#....
    O.OO#....#
    .....##...
    OO.#O....O
    .O.....O#.
    O.#..O.#.#
    ..O..#O..O
    .......O..
    #....###..
    #OO..#....
""".trimIndent()
private val c1 = """
    .....#....
    ....#...O#
    ...OO##...
    .OO#......
    .....OOO#.
    .O#...O#.#
    ....O#....
    ......OOOO
    #...O###..
    #..OO#....
""".trimIndent()
