package me.underlow.advent2022

// https://adventofcode.com/2022/day/8


object TreetopTreeHouseInput {
// rows and cols in this code always messed up, but it doesn't break solution

    fun parseInput(list: List<String>): Array<Array<Int>> {
        // parse rows
        val rows = list.map { it.toCharArray().map { Integer.parseInt(it.toString()) } }

        return rows.map { it.toTypedArray() }.toTypedArray()
    }

    fun checkVisibility(rows: Array<Array<Int>>, i: Int, j: Int): Boolean {
        // check row
        val row = checkRowVisible(rows, i, j)
        val col = checkColVisible(rows, i, j)

        return col || row
    }

    fun calculateView(rows: Array<Array<Int>>, i: Int, j: Int): Int {
        // check row
        val row = calcColView(rows, i, j)
        val col = calcRowView(rows, i, j)

        return col * row
    }

    fun checkColVisible(rows: Array<Array<Int>>, i: Int, j: Int): Boolean {
        var max = rows[i][0]
        for (row in 0 until j) {
            if (rows[i][row] > max) {
                max = rows[i][row]
            }
        }
        if (max < rows[i][j])
            return true
        max = rows[i][j + 1]

        for (row in j + 1 until rows.size) {
            if (rows[i][row] > max) {
                max = rows[i][row]
            }
        }
        if (max < rows[i][j])
            return true
        return false
    }

    fun calcRowView(rows: Array<Array<Int>>, i: Int, j: Int): Int {
        var view1 = 0
        var row = j - 1
        do {
            view1++
            if (rows[i][row] >= rows[i][j]) break
            row--
        } while (row >= 0)

        var view2 = 0
        row = j + 1
        do {
            view2++
            if (rows[i][row] >= rows[i][j]) break
            row++
        } while (row < rows[0].size)

        return view1 * view2
    }

    fun calcColView(rows: Array<Array<Int>>, i: Int, j: Int): Int {
        val currentValue = rows[i][j]

        var view1 = 0
        var row = i - 1
        do {
            view1++
            if (rows[row][j] >= currentValue) break
            row--
        } while (row >= 0)

        var view2 = 0
        row = i + 1
        do {
            view2++
            val comparingValue = rows[row][j]
            if (comparingValue >= currentValue) break
            row++
        } while (row < rows.size)

        return view1 * view2
    }

    fun checkRowVisible(rows: Array<Array<Int>>, i: Int, j: Int): Boolean {
        var max = rows[0][j]
        for (col in 0 until i) {
            if (rows[col][j] > max) {
                max = rows[col][j]
            }
        }
        if (max < rows[i][j])
            return true

        max = rows[i + 1][j]
        for (col in i + 1 until rows.size) {
            if (rows[col][j] > max) {
                max = rows[col][j]
            }
        }
        if (max < rows[i][j])
            return true
        return false
    }

    fun solution1(list: List<String>): Int {
        val input = parseInput(list)
        var count = 0
        for (i in 1 until input[0].size - 1)
            for (j in 1 until input.size - 1)
                if (checkVisibility(input, i, j)) {
                    count++
                    println("${input[i][j]} at position $i, $j is visible ")
                }


        return count + (list.size + list[0].length) * 2 - 4
    }

    fun solution2(list: List<String>): Int {
        val input = parseInput(list)
        var count = 0
        for (i in 1 until input[0].size - 1)
            for (j in 1 until input.size - 1) {
//            if (i == 14 && j == 58) {
                val visibleCoef = calculateView(input, i, j)

                if (count < visibleCoef) {
                    count = visibleCoef
                    println("$visibleCoef at position $i, $j ")
                }
            }
//        }


        return count
    }

}

fun main() {
    val input = readInput("${pathPrefix22}/day08.txt")
    val count = TreetopTreeHouseInput.solution1(input)
    val views = TreetopTreeHouseInput.solution2(input)

    println(count)
    println(views)

    checkResult(count, 1832)
    checkResult(views, 157320)
}
