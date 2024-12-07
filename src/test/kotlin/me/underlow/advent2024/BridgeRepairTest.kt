import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BridgeRepairTest {
    @Test
    fun testPart1() {
        val result = BridgeRepair.part1(input.split("\n"))
        assertEquals(3749, result)
    }

    @Test
    fun testPart12() {
        val result = BridgeRepair.part1(input2.split("\n"))
        assertEquals(2, result)
    }

    @Test
    fun testPart2() {
        val result = BridgeRepair.part2(input.split("\n"))
        assertEquals(11387, result)
    }
}

private val input = """
190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
21037: 9 7 18 13
292: 11 6 16 20
""".trimIndent()

private val input2 = """
2: 2 2
2: 2 1 
""".trimIndent()
