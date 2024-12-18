import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {
    @Test
    fun testPart1() {
        val result = GuardGallivant.part1(input.split("\n"))
        assertEquals(41, result)
    }

    @Test
    fun testPart2() {
        val result = GuardGallivant.part2(input.split("\n"))
        assertEquals(6, result)
    }
}

private val input = """
 ....#.....
 .........#
 ..........
 ..#.......
 .......#..
 ..........
 .#..^.....
 ........#.
 #.........
 ......#...
""".trimIndent()
