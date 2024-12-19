import me.underlow.advent2024.LinenLayout
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LinenLayoutTest {
    @Test
    fun testPart1() {
        val result = LinenLayout.part1(input.split("\n"))
        assertEquals(6, result)
    }

    @Test
    fun testPart2() {
        val result = LinenLayout.part2(input.split("\n"))
        assertEquals(16, result)
    }
}

private val input = """
 r, wr, b, g, bwu, rb, gb, br

 brwrr
 bggr
 gbbr
 rrbgbr
 ubwu
 bwurrg
 brgr
 bbrgwb
""".trimIndent()
