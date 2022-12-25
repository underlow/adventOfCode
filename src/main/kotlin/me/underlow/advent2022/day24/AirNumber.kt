package me.underlow.advent2022.day24

import kotlin.math.min
import kotlin.math.pow

/**
 * Number with base 5 and numbers -2, -1, 0 , 1, 2
 */

class AirNumber(input: String) {
    enum class Digit(val c: Char, val v: Int) {
        SHarp('=', -2), Minus('-', -1), Zero('0', 0), One('1', 1), Two('2', 2);

        companion object {
            fun fromChar(c: Char) = values().find { it.c == c } ?: error("Incorrect input $c")
        }
    }

    private val base = 5

    private fun base(pos: Int) = base.toDouble().pow(pos).toInt()

    private val digits: List<Digit>

    init {
        val d = mutableListOf<Digit>()
        input.forEach { c ->
            d += Digit.fromChar(c)
        }
        digits = d.reversed()
    }

    fun toDecimal(): Int = digits.foldIndexed(0) { idx, total, item ->
        total + digits[idx].v * base(idx)
    }

    override fun toString(): String {
        return "${digits.reversed().joinToString("")}[${toDecimal()}]"
    }

    operator fun plus(right: AirNumber): AirNumber {
        var take = 0
        val lenght = min(this.digits.size, right.digits.size)

        for (n in 0 until lenght) {

        }
        return AirNumber("1")
    }

}
