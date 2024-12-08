import me.underlow.Point
import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24
import me.underlow.parseToMap
import kotlin.math.max
import kotlin.math.min

object ResonantCollinearity {

    fun part1(list: List<String>): Int {
        val field = parseInput(list)

        // map antenna(char) to list of coords
        val antennasMap: MutableMap<Char, List<Point>> = mutableMapOf()

        for (i in field.indices) {
            for (j in field[0].indices) {
                if (field[i][j] == '.')
                    continue

                val p = antennasMap[field[i][j]] ?: emptyList()
                antennasMap[field[i][j]] = p + listOf(Point(i, j))
            }
        }

        var sum = 0
        val mS = mutableSetOf<Point>()
        // for each antenna char
        for (antenna in antennasMap) {
            // for each pair of antennas find antinodes
            val antennas = antenna.value

            for (i in antennas.indices) {
                for (j in i until antennas.size) {
                    if (i == j)
                        continue

                    val p1 = antennas[i]
                    val p2 = antennas[j]
                    // there are just 4 cases:

                    println("Checking $p1 $p2")

                    if (p1.row == p2.row) {
                        val minCol = min(p1.col, p2.col)
                        val maxCol = max(p1.col, p2.col)
                        val colDif = maxCol - minCol

                        if (field.isPointInside(p1.copy(col = minCol - colDif))) {
                            println("Point: ${p1.copy(col = minCol - colDif)}")
                            mS += p1.copy(col = minCol - colDif)
                        }
                        if (field.isPointInside(p1.copy(col = minCol + colDif))) {
                            println("Point: ${p1.copy(col = minCol + colDif)}")
                            mS += p1.copy(col = minCol + colDif)
                        }

                        sum += field.isPointInside(p1.copy(col = minCol - colDif)).toInt()
                        sum += field.isPointInside(p1.copy(col = minCol + colDif)).toInt()

//                        sum += 2
                        continue
                    }

                    if (p1.col == p2.col) {
                        val minRow = min(p1.row, p2.row)
                        val maxRow = max(p1.row, p2.row)
                        val rowDif = maxRow - minRow

                        if (field.isPointInside(p1.copy(row = minRow - rowDif))) {
                            println("Point: ${p1.copy(row = minRow - rowDif)}")
                            mS += p1.copy(row = minRow - rowDif)
                        }

                        if (field.isPointInside(p1.copy(row = minRow + rowDif))) {
                            println("Point: ${p1.copy(row = minRow + rowDif)}")
                            mS += p1.copy(row = minRow + rowDif)
                        }


                        sum += field.isPointInside(p1.copy(row = minRow - rowDif)).toInt()
                        sum += field.isPointInside(p1.copy(row = minRow + rowDif)).toInt()

//                        sum += 2
                        continue
                    }

                    if (p1.col > p2.col) {
                        val minRow = min(p1.row, p2.row)
                        val maxRow = max(p1.row, p2.row)
                        val minCol = min(p1.col, p2.col)
                        val maxCol = max(p1.col, p2.col)

                        val rowDif = maxRow - minRow
                        val colDif = maxCol - minCol



                        if (field.isPointInside(p1.copy(row = minRow - rowDif, col = maxCol + colDif))) {
                            println("Point: ${p1.copy(row = minRow - rowDif, col = maxCol + colDif)}")
                            mS += p1.copy(row = minRow - rowDif, col = maxCol + colDif)
                        }
                        if (field.isPointInside(p1.copy(row = maxRow + rowDif, col = minCol - colDif))) {
                            println("Point: ${p1.copy(row = maxRow + rowDif, col = minCol - colDif)}")
                            mS += p1.copy(row = maxRow + rowDif, col = minCol - colDif)
                        }


                        sum += field.isPointInside(p1.copy(row = minRow - rowDif, col = maxCol + colDif)).toInt()
                        sum += field.isPointInside(p1.copy(row = maxRow + rowDif, col = minCol - colDif)).toInt()


//                        sum += 2
                        continue
                    }

                    if (p1.col < p2.col) {
                        val minRow = min(p1.row, p2.row)
                        val maxRow = max(p1.row, p2.row)
                        val minCol = min(p1.col, p2.col)
                        val maxCol = max(p1.col, p2.col)

                        val rowDif = maxRow - minRow
                        val colDif = maxCol - minCol

                        if (field.isPointInside(p1.copy(row = minRow - rowDif, col = minCol - colDif))) {
                            println("Point: ${p1.copy(row = minRow - rowDif, col = minCol - colDif)}")
                            mS += p1.copy(row = minRow - rowDif, col = minCol - colDif)
                        }
                        if (field.isPointInside(p1.copy(row = maxRow + rowDif, col = maxCol + colDif))) {
                            println("Point: ${p1.copy(row = maxRow + rowDif, col = maxCol + colDif)}")
                            mS += p1.copy(row = maxRow + rowDif, col = maxCol + colDif)
                        }

                        sum += field.isPointInside(p1.copy(row = minRow - rowDif, col = minCol - colDif)).toInt()
                        sum += field.isPointInside(p1.copy(row = maxRow + rowDif, col = maxCol + colDif)).toInt()


//                        sum += 2
                        continue
                    }

//                    val x1 = (p1.row + p2.row) - (p1.row - p2.row)
                }
            }


        }



        println("mS: ${mS.size} $mS")
        return mS.size
    }

    fun part2(list: List<String>): Int {
        val field = parseInput(list)

        // just take a lot
        val cycles = 100

        // map antenna(char) to list of coords
        val antennasMap: MutableMap<Char, List<Point>> = mutableMapOf()

        for (i in field.indices) {
            for (j in field[0].indices) {
                if (field[i][j] == '.')
                    continue

                val p = antennasMap[field[i][j]] ?: emptyList()
                antennasMap[field[i][j]] = p + listOf(Point(i, j))
            }
        }

        var sum = 0
        val mS = mutableSetOf<Point>()
        // for each antenna char
        for (antenna in antennasMap) {
            // for each pair of antennas find antinodes
            val antennas = antenna.value

            for (i in antennas.indices) {
                for (j in i until antennas.size) {
                    if (i == j)
                        continue

                    val p1 = antennas[i]
                    val p2 = antennas[j]
                    // there are just 4 cases:

                    println("Checking $p1 $p2")

                    if (p1.row == p2.row) {
                        val minCol = min(p1.col, p2.col)
                        val maxCol = max(p1.col, p2.col)
                        val colDif = maxCol - minCol

                        for (step in 1 until cycles) {
                            if (field.isPointInside(p1.copy(col = minCol - colDif * step))) {
                                println("Point: ${p1.copy(col = minCol - colDif * step)}")
                                mS += p1.copy(col = minCol - colDif * step)
                            }
                            if (field.isPointInside(p1.copy(col = minCol + colDif * step))) {
                                println("Point: ${p1.copy(col = minCol + colDif * step)}")
                                mS += p1.copy(col = minCol + colDif * step)
                            }

                            sum += field.isPointInside(p1.copy(col = minCol - colDif * step)).toInt()
                            sum += field.isPointInside(p1.copy(col = minCol + colDif * step)).toInt()
                        }
                        mS += p1
                        mS += p2
                        continue
                    }

                    if (p1.col == p2.col) {
                        val minRow = min(p1.row, p2.row)
                        val maxRow = max(p1.row, p2.row)
                        val rowDif = maxRow - minRow
                        for (step in 1 until cycles) {

                            if (field.isPointInside(p1.copy(row = minRow - rowDif * step))) {
                                println("Point: ${p1.copy(row = minRow - rowDif * step)}")
                                mS += p1.copy(row = minRow - rowDif * step)
                            }

                            if (field.isPointInside(p1.copy(row = minRow + rowDif * step))) {
                                println("Point: ${p1.copy(row = minRow + rowDif * step)}")
                                mS += p1.copy(row = minRow + rowDif * step)
                            }


                            sum += field.isPointInside(p1.copy(row = minRow - rowDif * step)).toInt()
                            sum += field.isPointInside(p1.copy(row = minRow + rowDif * step)).toInt()
                        }
                        mS += p1
                        mS += p2

                        continue
                    }

                    if (p1.col > p2.col) {
                        val minRow = min(p1.row, p2.row)
                        val maxRow = max(p1.row, p2.row)
                        val minCol = min(p1.col, p2.col)
                        val maxCol = max(p1.col, p2.col)

                        val rowDif = maxRow - minRow
                        val colDif = maxCol - minCol

                        for (step in 1 until cycles) {


                            if (field.isPointInside(
                                    p1.copy(
                                        row = minRow - rowDif * step,
                                        col = maxCol + colDif * step
                                    )
                                )
                            ) {
                                println("Point: ${p1.copy(row = minRow - rowDif * step, col = maxCol + colDif * step)}")
                                mS += p1.copy(row = minRow - rowDif, col = maxCol + colDif * step)
                            }
                            if (field.isPointInside(
                                    p1.copy(
                                        row = maxRow + rowDif * step,
                                        col = minCol - colDif * step
                                    )
                                )
                            ) {
                                println("Point: ${p1.copy(row = maxRow + rowDif * step, col = minCol - colDif * step)}")
                                mS += p1.copy(row = maxRow + rowDif * step, col = minCol - colDif * step)
                            }


                            sum += field.isPointInside(
                                p1.copy(
                                    row = minRow - rowDif * step,
                                    col = maxCol + colDif * step
                                )
                            ).toInt()
                            sum += field.isPointInside(
                                p1.copy(
                                    row = maxRow + rowDif * step,
                                    col = minCol - colDif * step
                                )
                            ).toInt()
                        }
                        mS += p1
                        mS += p2

                        continue
                    }

                    if (p1.col < p2.col) {
                        val minRow = min(p1.row, p2.row)
                        val maxRow = max(p1.row, p2.row)
                        val minCol = min(p1.col, p2.col)
                        val maxCol = max(p1.col, p2.col)

                        val rowDif = maxRow - minRow
                        val colDif = maxCol - minCol
                        for (step in 1 until cycles) {

                            if (field.isPointInside(
                                    p1.copy(
                                        row = minRow - rowDif * step,
                                        col = minCol - colDif * step
                                    )
                                )
                            ) {
                                println("Point: ${p1.copy(row = minRow - rowDif * step, col = minCol - colDif * step)}")
                                mS += p1.copy(row = minRow - rowDif * step, col = minCol - colDif * step)
                            }
                            if (field.isPointInside(
                                    p1.copy(
                                        row = maxRow + rowDif * step,
                                        col = maxCol + colDif * step
                                    )
                                )
                            ) {
                                println("Point: ${p1.copy(row = maxRow + rowDif * step, col = maxCol + colDif * step)}")
                                mS += p1.copy(row = maxRow + rowDif * step, col = maxCol + colDif * step)
                            }

                            sum += field.isPointInside(
                                p1.copy(
                                    row = minRow - rowDif * step,
                                    col = minCol - colDif * step
                                )
                            ).toInt()
                            sum += field.isPointInside(
                                p1.copy(
                                    row = maxRow + rowDif * step,
                                    col = maxCol + colDif * step
                                )
                            ).toInt()
                        }
                        mS += p1
                        mS += p2

                        continue
                    }

//                    val x1 = (p1.row + p2.row) - (p1.row - p2.row)
                }
            }


        }



        println("mS: ${mS.size} $mS")
        return mS.size
    }

    private fun parseInput(list: List<String>): Array<Array<Char>> {
        return list.parseToMap()
    }

    fun <T> Array<Array<T>>.isPointInside(p: Point): Boolean {
        return p.row in this.indices && p.col in this[0].indices
    }

    fun Boolean.toInt() = if (this) 1 else 0
}


fun main() {
    val input = readInput("$pathPrefix24/day08.txt")
    val res1 = ResonantCollinearity.part1(input)
    val res2 = ResonantCollinearity.part2(input)

    checkResult(res1, 259)//274, 537 high
    checkResult(res2, 0) // 918 low

    println(res1)
    println(res2)
}
