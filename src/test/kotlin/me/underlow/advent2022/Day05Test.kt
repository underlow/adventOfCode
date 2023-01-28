package me.underlow.advent2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day05Test {

    @Test
    fun parseCommands() {
        val c = SupplyStacksInput.parseCommands("move 6 from 4 to 3")
        assertEquals(SupplyStacksInput.StackCommand(4, 3, 6), c[0])
    }
}
