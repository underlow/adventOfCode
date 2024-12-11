import me.underlow.advent2024.PlutonianPebbles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PlutonianPebblesTest {
    @Test
    fun testPart1() {
        val result = PlutonianPebbles.part1(input)
        assertEquals(55312, result)
    }

    @Test
    fun testPart2() {
        val result = PlutonianPebbles.part2(input)
        assertEquals(0, result)
    }
}

private val input = """
125 17
""".trimIndent()
