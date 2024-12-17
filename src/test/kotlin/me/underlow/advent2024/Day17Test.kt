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
        ChronospatialComputer.debug = false
        val result = ChronospatialComputer.part2(input2.split("\n"))
        assertEquals(117440, result)
    }

    @Test
    fun testPart21() {
        val result = ChronospatialComputer.part1(input21.split("\n"))
        assertEquals("0,3,5,4,3,0", result)
    }
}

private val input = """
 Register A: 729
 Register B: 0
 Register C: 0

 Program: 0,1,5,4,3,0
""".trimIndent()

private val input2 = """
    Register A: 2024
    Register B: 0
    Register C: 0

    Program: 0,3,5,4,3,0
""".trimIndent()
private val input21 = """
    Register A: 117440
    Register B: 0
    Register C: 0

    Program: 0,3,5,4,3,0
""".trimIndent()
