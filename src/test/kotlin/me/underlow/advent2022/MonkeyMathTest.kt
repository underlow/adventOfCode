package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MonkeyMathTest {
    @Test
    fun testSolvePart1() {
        val result = MonkeyMath.solvePart1(input.split("\n"))
        assertEquals(152, result)
    }

    @Test
    fun testSolvePart2() {
        val result = MonkeyMath.solvePart2(input.split("\n"))
        assertEquals(301, result)
    }

    private val input = """
    root: pppw + sjmn
    dbpl: 5
    cczh: sllz + lgvd
    zczc: 2
    ptdq: humn - dvpt
    dvpt: 3
    lfqf: 4
    humn: 5
    ljgn: 2
    sjmn: drzm * dbpl
    sllz: 4
    pppw: cczh / lfqf
    lgvd: ljgn * ptdq
    drzm: hmdt - zczc
    hmdt: 32
""".trimIndent()

}
