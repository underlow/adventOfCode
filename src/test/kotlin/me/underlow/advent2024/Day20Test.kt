import me.underlow.advent2024.RaceCondition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RaceConditionTest {
    @Test
    fun testPart1() {
        val result = RaceCondition.part1(input.split("\n"))
        assertEquals(42, result) // ??
    }

    @Test
    fun testPart2() {
        val result = RaceCondition.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
###############
#...#...#.....#
#.#.#.#.#.###.#
#S#...#.#.#...#
#######.#.#.###
#######.#.#...#
#######.#.###.#
###..E#...#...#
###.#######.###
#...###...#...#
#.#####.#.###.#
#.#...#.#.#...#
#.#.#.#.#.#.###
#...#...#...###
###############
""".trimIndent()
