import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HoofItTest {
    @Test
    fun testPart1() {
        val result = HoofIt.part1(input.split("\n"))
        assertEquals(36, result)
    }

    @Test
    fun testPart2() {
        val result = HoofIt.part2(input.split("\n"))
        assertEquals(36, result)
    }
}

private val input = """
89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
""".trimIndent()
