package me.underlow.advent2022.day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class NoSpaceLeftOnDeviceKtTest {

    @Test
    fun splitToCommandTokens() {

        val tree = splitToCommandTokens(s.split("\n"))

        assertEquals(10, tree.size)
    }

    @Test
    fun processCommand() {
        val tree = splitToCommandTokens(s.split("\n"))
        val fs = processCommands(tree)

        assertEquals(4, fs.children().size)

    }

    @Test
    fun testCase1() {
        val tree = splitToCommandTokens(s.split("\n"))
        val fs = processCommands(tree)
        fs.calculateSizes()

        val total = fs.searchRequired(mutableSetOf())

        val totalSum = total.filter { it.size < 100000 }.sumOf { it.size }

        assertEquals(95437, totalSum)

    }
}

val s = """
            ${'$'} cd /
            ${'$'} ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            ${'$'} cd a
            ${'$'} ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            ${'$'} cd e
            ${'$'} ls
            584 i
            ${'$'} cd ..
            ${'$'} cd ..
            ${'$'} cd d
            ${'$'} ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
        """.trimIndent()
