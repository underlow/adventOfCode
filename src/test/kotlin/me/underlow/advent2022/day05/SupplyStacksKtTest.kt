package me.underlow.advent2022.day05

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SupplyStacksKtTest {

    @Test
    fun parseCommands() {
        val c = parseCommands("move 6 from 4 to 3")
        assertEquals(StackCommand(4,3,6), c[0])
    }
}
