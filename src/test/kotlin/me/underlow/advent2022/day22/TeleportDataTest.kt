package me.underlow.advent2022.day22

import me.underlow.advent2022.Point
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TeleportDataTest{
    @Test
    fun testTeleport(){
        val points = listOf(
            Point(150, 0) to Point(0 , 50),
            Point(199, 1) to Point(0, 101),
            Point(150, 49) to Point(149, 50),
            Point(0, 50) to Point(0, 50),
            Point(101, 0) to Point(48, 50),
        )


        points.forEach { (from, to) ->
            val tp = Teleport.findTeleport(from)
            assertEquals(to, tp.op(from))
        }
    }
}
