package me.underlow.advent2022.day17

import me.underlow.advent2022.d_17.Shape

enum class PyroclasticFlowFieldChar(val char: Char) {
    Empty('.'),
    Rock('#');
}

class PyroclasticFlowField {
    private val flowField = mutableListOf<Array<PyroclasticFlowFieldChar>>()

    private val numbersList = mutableListOf<Int>()

    private var purgedLines: Long = 0

    var currentTowerHeight: Long = 0
        private set
        get() {
            if (flowField.isEmpty()) {
                return 0 + purgedLines
            }
            // find first empty row
            for (i in 0 until flowField.size) {
                if (flowField[i].all { it == PyroclasticFlowFieldChar.Empty }) {
                    return i + purgedLines
                }
            }
            error("No empty row found")
        }


    val height: Long
        get() = flowField.size + purgedLines

    /**
     * function print field state selecting y in reverse order
     */
    fun dump() {
        var idx = flowField.size - 1
        flowField.reversed().forEach { row ->
            print("$idx ")
            row.forEach { cell ->
                print(cell.char)
            }
            println()
            idx--
        }
    }

    fun dumpWithShape(shape: Shape) {
        for (y in height - 1 downTo 0) {
            for (x in 0 until flowField.first().size) {
                val point = FlowPoint(x, y)
                if (shape.points.any { it == point }) {
                    print('@')
                } else {
                    print(flowField[y.toInt()][x].char)
                }
            }
            println()
        }
        println()
    }

    private fun addRow() {
        val newRow = Array(fieldWidth) { PyroclasticFlowFieldChar.Empty }
        flowField.add(flowField.size, newRow)
    }

    fun addRowsForNextStep(requiredHeight: Long) {
        if (height  < requiredHeight) {
            repeat((requiredHeight - height).toInt() ) {
                addRow()
            }
        }

        if (height  > requiredHeight) {
            repeat((height  - requiredHeight).toInt() ) {
                flowField.removeAt(flowField.size - 1)
            }
        }
        purgeFilledRows()
    }

    private fun purgeFilledRows() {
        val removeTill = firstFullyFilledLine()
        if (removeTill > 0) {
            repeat(removeTill - 1) {
                numbersList += flowField[0].toBinaryNumber()
                flowField.removeAt(0)
            }
            purgedLines += (removeTill - 1)
        }
    }

    // find first line filled with only Rocks
    private fun firstFullyFilledLine(): Int {
        for (i in 0 until flowField.size) {
            if (flowField[i].all { it == PyroclasticFlowFieldChar.Rock }) {
                return i
            }
        }
        return 0
    }

    /**
     * check if point inside field
     */
    private fun isPointInsideField(point: FlowPoint): Boolean =
        point.x in 0 until fieldWidth && point.y in 0 until flowField.size + purgedLines

    /**
     * check if there is no rock in point
     */
    private fun isPointEmpty(point: FlowPoint): Boolean =
        flowField[(point.y - purgedLines).toInt()][point.x] == PyroclasticFlowFieldChar.Empty

    /**
     * function checks if shape can be placed in field, it can be placed it is inside field and there is no rock
     */
    fun canPlaceShape(shape: Shape): Boolean = shape.points.all { point ->
        isPointInsideField(point) && isPointEmpty(point)
    }

    fun placeShape(shape: Shape) {
        shape.points.forEach { point ->
            flowField[(point.y - purgedLines).toInt()][point.x] = PyroclasticFlowFieldChar.Rock
        }
    }

    companion object {
        const val fieldHeightForANewShape = 3
    }
}

private fun Array<PyroclasticFlowFieldChar>.toBinaryNumber(): Int {
    var result = 0
    for (i in indices.reversed()) {
        if (this[i] == PyroclasticFlowFieldChar.Rock) {
            result += 1 shl (this.size - 1 - i)
        }
    }
    return result
}

private const val fieldWidth = 7

