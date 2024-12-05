import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RedNosedReportsTest {
    @Test
    fun testPart1() {
        val result = RedNosedReports.part1(input.split("\n"))
        assertEquals(2, result)
    }

    @Test
    fun testPart2() {
        val result = RedNosedReports.part2(input.split("\n"))
        assertEquals(4, result)
    }
}

private val input = """
 7 6 4 2 1
 1 2 7 8 9
 9 7 6 2 1
 1 3 2 4 5
 8 6 4 4 1
 1 3 6 7 9
""".trimIndent()
