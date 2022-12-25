package me.underlow.advent2022.day24

import kotlin.math.max
import kotlin.math.pow

/**
 * Number with base 5 and numbers -2, -1, 0 , 1, 2
 */

class AirNumber(input: List<Digit>) {
    enum class Digit(val c: Char, val v: Int) {
        SHarp('=', -2), Minus('-', -1), Zero('0', 0), One('1', 1), Two('2', 2);

        companion object {
            fun fromChar(c: Char) = values().find { it.c == c } ?: error("Incorrect input $c")
            fun fromInt(v: Int) = values().find { it.v == v } ?: error("Incorrect input $v")

        }
    }

    private val base = 5

    private fun base(pos: Int) = base.toDouble().pow(pos).toInt()

    private val digits: List<Digit> = input

    companion object {
        fun fromString(input: String): AirNumber {
            val d = mutableListOf<Digit>()
            input.forEach { c ->
                d += Digit.fromChar(c)
            }
            return AirNumber(d.reversed())
        }

    }

    fun toDecimal(): Int = digits.foldIndexed(0) { idx, total, item ->
        total + digits[idx].v * base(idx)
    }

    override fun toString(): String {
        val digits = digits.dropLastWhile { it.v == 0 }.reversed().joinToString(""){ it.c.toString()}
        return "$$digits[${toDecimal()}]"
    }

    operator fun plus(right: AirNumber): AirNumber {
        var take = 0
        var give = 0
        val lenght = max(this.digits.size, right.digits.size) + 1

        fun AirNumber.digitAt(p: Int) = if (p < this.digits.size) this.digits[p].v else 0

        val acc = mutableListOf<Digit>()
        for (n in 0 until lenght) {
            val leftDigit = this.digitAt(n) - take + give
            val rightDigit = right.digitAt(n)

            if (leftDigit + rightDigit in -2..2) {
                acc.add(Digit.fromInt(leftDigit + rightDigit))
                take = 0
                give = 0
                continue
            }

            if (leftDigit + rightDigit > -2) {
                acc.add(Digit.fromInt(leftDigit + rightDigit - 5))
                take = 0
                give = 1
                continue
            }

            if (leftDigit + rightDigit < -2) {
                acc.add(Digit.fromInt(leftDigit + rightDigit + 5))
                take = 1
                give = 0
                continue
            }
        }

        return AirNumber(acc)
    }

    fun to5thString(): String = this.digits.dropLastWhile { it.v == 0 }.reversed().joinToString("") { it.c.toString() }

}
