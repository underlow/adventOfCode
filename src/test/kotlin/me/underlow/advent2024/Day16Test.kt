import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReindeerMazeTest {
    @Test
    fun testPart1() {
        val result = ReindeerMaze.part1(input.split("\n"))
        assertEquals(7036, result)
    }

    @Test
    fun testPart12() {
        val result = ReindeerMaze.part1(input2.split("\n"))
        assertEquals(2012, result)
    }

    @Test
    fun testPart2() {
        val result = ReindeerMaze.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
 ###############
 #.......#....E#
 #.#.###.#.###.#
 #.....#.#...#.#
 #.###.#####.#.#
 #.#.#.......#.#
 #.#.#####.###.#
 #...........#.#
 ###.#.#####.#.#
 #...#.....#.#.#
 #.#.#.###.#.#.#
 #.....#...#.#.#
 #.###.#.#.#.#.#
 #S..#.....#...#
 ###############
""".trimIndent()
private val input2 = """
 #############
 #..........E#
 #.#.#.#.###.#
 #S..#.......#
 #############
""".trimIndent()
