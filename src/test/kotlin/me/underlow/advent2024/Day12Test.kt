import me.underlow.advent2024.GardenGroups
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GardenGroupsTest {
    @Test
    fun testPart1() {
        val result = GardenGroups.part1(input.split("\n"))
        assertEquals(1930, result)
    }

    @Test
    fun testPart2() {
        val result = GardenGroups.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
 RRRRIICCFF
 RRRRIICCCF
 VVRRRCCFFF
 VVRCCCJFFF
 VVVVCJJCFE
 VVIVCCJJEE
 VVIIICJJEE
 MIIIIIJJEE
 MIIISIJEEE
 MMMISSJEEE
""".trimIndent()
