import me.underlow.advent2024.MonkeyMarket
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day22Test {
    @Test
    fun testPart1() {
        val result = MonkeyMarket.part1(input.split("\n"))
        assertEquals(37327623, result)
    }

    @Test
    fun newSecretTest() {
        assertEquals(37, MonkeyMarket.mix(42, 15))
        assertEquals(16113920, MonkeyMarket.prune(100000000))

        assertEquals(15887950, MonkeyMarket.newSecret(123))
        assertEquals(16495136, MonkeyMarket.newSecret(15887950))
    }

    @Test
    fun testPart2() {
        val result = MonkeyMarket.part2(input.split("\n"))
        assertEquals(23, result)
    }
}

private val input = """
 1
 10
 100
 2024
""".trimIndent()
private val input2 = """
 1
 2
 3
 2024
""".trimIndent()
