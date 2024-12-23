import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {
    @Test
    fun testPart1() {
        val result = MullItOver.part1(input.split("\n"))
        assertEquals(161, result)
    }

    @Test
    fun testPart2() {
        val result = MullItOver.part2(input2.split("\n"))
        assertEquals(48, result)
    }
}

private val input = """
xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
""".trimIndent()

private val input2 = """
    xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
""".trimIndent()
