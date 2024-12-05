import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CeresSearchTest {
    @Test
    fun testPart1() {
        val result = CeresSearch.part1(input.split("\n"))
        assertEquals(18, result)
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
