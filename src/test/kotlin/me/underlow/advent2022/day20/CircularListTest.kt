package me.underlow.advent2022.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CircularListTest {

    @Test
    fun testMoveDefault() {
        val list = CircularList(listOf(1, 2, 3, -19, 4))
        list.moveNode(3, -19)
        val result = list.toList(1)

        assertEquals(listOf(1, 2, 3, 4, -19).map { it.toLong() }, result)

    }

    @Test
    fun testMoveDefault3() {
        val list = CircularList(listOf(1, 2, 3, 19, 4))
        list.moveNode(3, 19)
        val result = list.toList(1)

        assertEquals(listOf(1, 2, 19, 3, 4).map { it.toLong() }, result)

    }

    @Test
    fun testMoveDefault2() {
        val op = -6
        val list = CircularList(listOf(1, 2, 3, op, 4).map { it.toLong() })
        list.moveNode(3, op.toLong())
        val result = list.toList(1)

        assertEquals(listOf(1, op, 2,  3, 4).map { it.toLong() }, result)

    }
}
