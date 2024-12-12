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
        val result = GardenGroups.part2(input.split("\n"))
        assertEquals(1206, result)
    }

    @Test
    fun testPart21() {
        val result = GardenGroups.part2(inputFences.split("\n"))
        assertEquals(80, result)
    }

    @Test
    fun testPart22() {
        val result = GardenGroups.part2(input22.split("\n"))
        assertEquals(436, result)
    }

    @Test
    fun testPart23() {
        val result = GardenGroups.part2(input23.split("\n"))
        assertEquals(236, result)
    }

    @Test
    fun testPart24() {
        val result = GardenGroups.part2(input24.split("\n"))
        assertEquals(368, result)
    }

    @Test
    fun testFencesUp() {
        val result = GardenGroups.fencesUp(inputFences.split("\n").parseToMap())
        assertEquals(6, result)
    }

    @Test
    fun testFencesUp2() {
        val result = GardenGroups.fencesUp(inputFences2.split("\n").parseToMap())
        assertEquals(5, result)
    }

    @Test
    fun extractGroupsTest() {
        val charField = inputFences.split("\n").parseToMap()
        val grouped = extractGroups(charField)

        assertEquals(5, grouped.size)

        assertEquals(true, grouped.entries.all { it.value.map { it.char }.toSet().size == 1 })
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
private val input22 = """
    OOOOO
    OXOXO
    OOOOO
    OXOXO
    OOOOO
""".trimIndent()
private val input23 = """
EEEEE
EXXXX
EEEEE
EXXXX
EEEEE
""".trimIndent()
private val input24 = """
AAAAAA
AAABBA
AAABBA
ABBAAA
ABBAAA
AAAAAA""".trimIndent()
