import me.underlow.advent2024.RaceCondition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RaceConditionTest {
    @Test
    fun testPart1() {
        val result = RaceCondition.part1(input.split("\n"), 1)
        assertEquals(44, result) // ??
    }

    @Test
    fun testPart21() {
        val result = RaceCondition.part2(input.split("\n"), 1)
        assertEquals(44, result) // ??
    }

    @Test
    fun testPart2() {
        val result = RaceCondition.part2(input.split("\n"), 50)
        assertEquals(285, result)
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
