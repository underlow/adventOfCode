package me.underlow.advent2015

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun testPart1() {

        assertEquals("11", ElvesLookElvesSay.gameRound("1"))
        assertEquals("21", ElvesLookElvesSay.gameRound("11"))
        assertEquals("1211", ElvesLookElvesSay.gameRound("21"))
        assertEquals("111221", ElvesLookElvesSay.gameRound("1211"))
        assertEquals("312211", ElvesLookElvesSay.gameRound("111221"))
    }

}
