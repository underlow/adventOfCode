import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CeresSearchTest {
    @Test
    fun testPart1() {
        val result = CeresSearch.part1(input.split("\n"))
        assertEquals(18, result)
    }

    @Test
    fun testPart2() {
        val result = CeresSearch.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
""".trimIndent()
