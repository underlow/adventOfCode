import me.underlow.advent2024.RaceCondition
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RaceConditionTest {
    @Test
    fun testPart1() {
        val result = RaceCondition.part2(input.split("\n"), 1, cheatDistance = 2)
        assertEquals(44, result) // ??
    }

    @Test
    fun testPart2() {
        val result = RaceCondition.part2(input.split("\n"), 50, 20)
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
