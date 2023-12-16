package me.underlow.advent2023

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {
    @Test
    fun testPart1() {
        val result = TheFloorWillBeLava.part1(input.split("\n"))
        assertEquals(46, result)
    }

    @Test
    fun testPart2() {
        val result = TheFloorWillBeLava.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
    .|...\....
    |.-.\.....
    .....|-...
    ........|.
    ..........
    .........\
    ..../.\\..
    .-.-/..|..
    .|....-|.\
    ..//.|....
""".trimIndent()
