import me.underlow.advent2024.GardenGroups
import me.underlow.advent2024.GardenGroups.extractGroups
import me.underlow.parseToMap
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
        val result = GardenGroups.part2(input2.split("\n"))
        assertEquals(1206, result)
    }

    @Test
    fun testFencesUp() {
        val result = GardenGroups.fencesUp(inputFences.split("\n").parseToMap())
        assertEquals(6, result)
    }

    @Test
    fun testFencesUpForGroup() {
        val charField = inputFences.split("\n").parseToMap()
        val grouped = extractGroups(charField)

        val result = GardenGroups.fencesUpForGroup(charField, grouped[0]!!.map { it.point }.toSet())
        assertEquals(1, result)
        val result2 = GardenGroups.fencesUpForGroup(charField, grouped[2]!!.map { it.point }.toSet())
        assertEquals(2, result2)
    }

    @Test
    fun testFencesUp2() {
        val result = GardenGroups.fencesUp(inputFences2.split("\n").parseToMap())
        assertEquals(5, result)
    }

    @Test
    fun testFencesDown() {
        val result = GardenGroups.fencesUp(inputFences.split("\n").parseToMap())
        assertEquals(6, result)
    }

    @Test
    fun testFencesDown2() {
        val result = GardenGroups.fencesUp(inputFences2.split("\n").parseToMap())
        assertEquals(5, result)
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

private val input2 = """
    AAAAAA
    AAABBA
    AAABBA
    ABBAAA
    ABBAAA
    AAAAAA
""".trimIndent()

private val inputFences = """
    AAAA
    BBCD
    BBCC
    EEEC
""".trimIndent()
private val inputFences2 = """
    EEEEE
    EXXXX
    EEEEE
    EXXXX
    EEEEE
""".trimIndent()
