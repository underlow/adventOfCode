import me.underlow.advent2024.KeypadConundrum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KeypadConundrumTest {
    @Test
    fun testPart1() {
        val result = KeypadConundrum.part1(input.split("\n"))
     assertEquals(126384, result)
    }

    @Test
    fun testPart2() {
        val result = KeypadConundrum.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
 029A: <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
 980A: <v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A
 179A: <v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
 456A: <v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A
 379A: <v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
""".trimIndent()
