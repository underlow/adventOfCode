package me.underlow.advent2022.day22

import me.underlow.advent2022.Point
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TeleportDataTest {
    @Test
    fun testTeleport() {
        val points = listOf(
            Point(150, 0) to Point(-1, 50),
            Point(199, 0) to Point(-1, 99),
            Point(199, 49) to Point(150, 99),
            Point(0, 51) to Point(151, -1),
            Point(149, 0) to Point(0, 49),
            Point(100, 0) to Point(49, 49),
            Point(101, 0) to Point(48, 49),
        )
        val teleport = Task2Teleport()

        points.forEach { (from, to) ->
            val tp = teleport.findTeleport(from, Direction.Name.Right)
            assertEquals(to, tp.op(from), "Fail $from -> $to")
        }
    }

    @Test
    fun testCubeTransform() {
        val points = listOf(
            Point(102, 1) to Point(101, 47),
            Point(102, 47) to Point(148, 48),
            Point(100, 0) to Point(100, 49),
//            Point(101, 0) to Point(48, 50),
        )
        val cRear = Teleport.Cube("right", Point(2, 0), 50)
        // hardcode for speed
        points.forEach { p ->
            assertEquals(p.second, cRear.rotateCW(p.first))
        }


        points.forEach { p ->
            var rotated = p.first
            repeat(4) { rotated = cRear.rotateCW(rotated) }
            assertEquals(p.first, rotated)
        }


    }

    @Test
    fun testCubeTransformOnTestData() {
        val points = listOf(
            Point(4, 4) to Point(4, 7),
            Point(4, 7) to Point(7, 7),
            Point(7, 7) to Point(7, 4),
            Point(7, 4) to Point(4, 4),

            Point(5, 5) to Point(5, 6),
            Point(5, 6) to Point(6, 6),
            Point(6, 6) to Point(6, 5),
            Point(6, 5) to Point(5, 5),

            Point(5, 4) to Point(4, 6),
            Point(4, 6) to Point(6, 7),

            Point(4, 7) to Point(7, 7),
        )
        val cRear = Teleport.Cube("front", Point(1, 1), 4)
        // hardcode for speed
        points.forEach { p ->
            assertEquals(p.second, cRear.rotateCW(p.first), "Fail ${p.first} -> ${p.second}")
        }


        points.forEach { p ->
            var rotated = p.first
            repeat(4) { rotated = cRear.rotateCW(rotated) }
            assertEquals(p.first, rotated)
        }
    }
    @Test
    fun testTeleportOnTestRunData() {
        val pointsMove = listOf(
            Point(5, 11) to Point(7, 14),
        )
        val pointsRotate = listOf(
            Point(5, 11) to Point(7, 10),
        )

        val ctBottom = Teleport.Cube("bottom", Point(1, 2), 4)

        pointsRotate.forEach { p ->
            assertEquals(p.second, ctBottom.rotateCW(p.first),"Fail ${p.first} -> ${p.second}")
        }

        val teleport = TestTeleport()

        pointsMove.forEach { (from, to) ->
            val tp = teleport.findTeleport(from, Direction.Name.Right)
            assertEquals(to, tp.op(from), "Fail $from -> $to")
        }
    }

}
