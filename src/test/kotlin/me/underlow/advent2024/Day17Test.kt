import me.underlow.advent2024.ChronospatialComputer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChronospatialComputerTest {
    @Test
    fun testPart1() {
        val result = ChronospatialComputer.part1(input.split("\n"))
        assertEquals("4,6,3,5,6,3,5,2,1,0", result)
    }

    @Test
    fun testPart2() {
        val result = ChronospatialComputer.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
 Register A: 729
 Register B: 0
 Register C: 0

 Program: 0,1,5,4,3,0
""".trimIndent()
