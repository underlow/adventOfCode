import me.underlow.advent2024.PlutonianPebbles
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PlutonianPebblesTest {
    @Test
    fun testPart1() {
        val result = PlutonianPebbles.part1(input.split("\n"))
        assertEquals(55312, result)
    }

    @Test
    fun testPart2() {
        val result = PlutonianPebbles.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
Initial arrangement:
125 17

After 1 blink:
253000 1 7

After 2 blinks:
253 0 2024 14168

After 3 blinks:
512072 1 20 24 28676032

After 4 blinks:
512 72 2024 2 0 2 4 2867 6032

After 5 blinks:
1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32

After 6 blinks:
2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2
""".trimIndent()
