package me.underlow.advent2022


// https://adventofcode.com/2022/day/17
object PyroclasticFlowInput {
    data class FlowPoint(val x: Int, val y: Long) {
        fun down() = FlowPoint(x, y - 1)
        fun left() = FlowPoint(x - 1, y)
        fun right() = FlowPoint(x + 1, y)

        operator fun plus(other: FlowPoint) = FlowPoint(x + other.x, y + other.y)
    }

    private enum class Direction {
        LEFT, RIGHT
    }

    private fun parseInput(list: String): List<Direction> {
        return list.map {
            when (it) {
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> error("Invalid direction $it")
            }
        }
    }

    private fun makeSteps(count: Long, directions: List<Direction>): Long {
        val field = PyroclasticFlowField()
        val shapesProvider = CircularProvider(listOf(minusShape, plusShape, lShape, verticalMinusShape, squareShape))
        val hotGasProvider = CircularProvider(directions)

        val heights = mutableListOf<Long>()

        for (i in 0 until 10000L) {
            var shape = shapesProvider.nextShape()
            field.addRowsForNextStep(requiredHeight = field.currentTowerHeight + shape.height + PyroclasticFlowField.fieldHeightForANewShape)
            var nextShape = shape.moveTo(FlowPoint(2, field.height - 1))

            while (field.canPlaceShape(nextShape)) {
                shape = nextShape
                val possibleMove = when (hotGasProvider.nextShape()) {
                    Direction.LEFT -> nextShape.left()
                    Direction.RIGHT -> nextShape.right()
                }
                if (field.canPlaceShape(possibleMove)) {
                    shape = possibleMove
                }
                nextShape = shape.down()
            }

            field.placeShape(shape)
            heights.add(field.currentTowerHeight)

        }

        val heightsDiff = heights.zipWithNext().map { it.second - it.first }

        return tryFindPattern(heightsDiff, heights, count)
    }

    private fun tryFindPattern(list: List<Number>, heights: List<Long>, rounds: Long): Long {
        var startingPoint = 0
        var patternLength = 15

        while (startingPoint + patternLength * 3 < list.size) {
            val pattern = list.subList(startingPoint, startingPoint + patternLength)
            val nextPattern = list.subList(startingPoint + patternLength, startingPoint + patternLength * 2)
            if (pattern == nextPattern) {
                println("Found pattern $pattern at $startingPoint, length $patternLength")

                val patternCount = (rounds - (startingPoint - 1)) / patternLength
                val patternRemainder = (rounds - (startingPoint - 1)) % patternLength

                heights[startingPoint - 1]
                val heightPattern = pattern.sumOf { it.toLong() }
                val heightReminder = heights[(startingPoint - 1 + patternRemainder - 1).toInt()]

                return heightPattern * patternCount + heightReminder
            }
            if (startingPoint + (patternLength + 1) * 3 >= list.size) {
                startingPoint++
                patternLength = 15
            } else {
                patternLength++
            }
        }
        error("No pattern found")


    }

    fun solvePart1(list: String, rounds: Long): Long {
        val directions = parseInput(list)
        return makeSteps(rounds, directions)
    }

    class CircularProvider<T>(private val shapes: List<T>) {
        private var currentShapeIndex = 0

        fun nextShape(): T {
            val shape = shapes[currentShapeIndex]
            currentShapeIndex = (currentShapeIndex + 1) % shapes.size
            return shape
        }
    }


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
            if (height < requiredHeight) {
                repeat((requiredHeight - height).toInt()) {
                    addRow()
                }
            }

            if (height > requiredHeight) {
                repeat((height - requiredHeight).toInt()) {
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

}

fun main() {
    val input = readInputAsString("${pathPrefix}/day17.txt")
    val res1 = PyroclasticFlowInput.solvePart1(input, 2022)
    val res2 = PyroclasticFlowInput.solvePart1(input, 1000000000000L)

    println(res1)
    println(res2)

    checkResult(res1, 3124)
    checkResult(res2, 1561176470569L)
}
