import me.underlow.advent2024.KeypadConundrum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KeypadConundrumTest {
    @Test
    fun testPart1() {
        val result = KeypadConundrum.part1(input.split("\n"))
        assertEquals(126384, result)
    }

    @Test
    fun testPart2() {
        val result = KeypadConundrum.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
029A
980A
179A
456A
379A
""".trimIndent()
