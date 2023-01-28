package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20KtTest {
    @Test
    fun testSolvePart1() {
        val result = GrovePositioningSystem.solvePart1(input.split("\n"))
        assertEquals(3, result)
    }

    @Test
    fun testSolvePart2() {
        val result = GrovePositioningSystem.solvePart2(input.split("\n"), 811589153)
        assertEquals(1623178306, result)
    }
}

private val input = """
    1
    2
    -3
    3
    -2
    0
    4
""".trimIndent()


class CircularListTest {

    @Test
    fun testMoveDefault() {
        val list = GrovePositioningSystem.CircularList(listOf(1, 2, 3, -19, 4))
        list.moveNode(3, -19)
        val result = list.toList(1)

        assertEquals(listOf(1, 2, 3, 4, -19).map { it.toLong() }, result)

    }

    @Test
    fun testMoveDefault3() {
        val list = GrovePositioningSystem.CircularList(listOf(1, 2, 3, 19, 4))
        list.moveNode(3, 19)
        val result = list.toList(1)

        assertEquals(listOf(1, 2, 19, 3, 4).map { it.toLong() }, result)

    }

    @Test
    fun testMoveDefault2() {
        val op = -6
        val list = GrovePositioningSystem.CircularList(listOf(1, 2, 3, op, 4).map { it.toLong() })
        list.moveNode(3, op.toLong())
        val result = list.toList(1)

        assertEquals(listOf(1, op, 2,  3, 4).map { it.toLong() }, result)

    }
}
