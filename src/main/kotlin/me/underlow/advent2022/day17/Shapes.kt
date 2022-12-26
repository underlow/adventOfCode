package me.underlow.advent2022.d_17

import me.underlow.advent2022.day17.FlowPoint

data class Shape(val points: List<FlowPoint>, val height: Int) {
    fun left(): Shape = Shape(points.map { it.left() }, height)
    fun right(): Shape = Shape(points.map { it.right() }, height)
    fun down(): Shape = Shape(points.map { it.down() }, height)

    fun moveTo(point: FlowPoint): Shape = Shape(points.map { it + point }, height)

}

val minusShape = Shape(
    listOf(FlowPoint(0, 0), FlowPoint(1, 0), FlowPoint(2, 0), FlowPoint(3, 0)),
    1
)
val plusShape =
    Shape(
        listOf(FlowPoint(0, -1), FlowPoint(1, 0), FlowPoint(1, -1), FlowPoint(1, -2), FlowPoint(2, -1)),
        3
    )
val lShape = Shape(
    listOf(FlowPoint(0, -2), FlowPoint(1, -2), FlowPoint(2, 0), FlowPoint(2, -1), FlowPoint(2, -2)),
    3
)
val verticalMinusShape = Shape(
    listOf(FlowPoint(0, 0), FlowPoint(0, -1), FlowPoint(0, -2), FlowPoint(0, -3)),
    4
)
val squareShape = Shape(
    listOf(FlowPoint(0, 0), FlowPoint(0, -1), FlowPoint(1, 0), FlowPoint(1, -1)),
    2
)
