package me.underlow.advent2022.day24

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AirNumberTest {
    @Test
    fun `test convertion from 5base to 10base`() {
        val list = listOf(
            "1=-0-2" to 1747,
            "12111" to 906,
            "2=0=" to 198,
            "21" to 11,
            "2=01" to 201,
            "111" to 31,
            "20012" to 1257,
            "112" to 32,
            "1=-1=" to 353,
            "1-12" to 107,
            "12" to 7,
            "1=" to 3,
            "122" to 37
        )
        list.forEach { (from ,to) ->
            assertEquals(to, AirNumber(from).toDecimal())

        }
    }


}
