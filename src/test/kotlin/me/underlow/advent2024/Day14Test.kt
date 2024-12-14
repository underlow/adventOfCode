import me.underlow.advent2024.RestroomRedoubt
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RestroomRedoubtTest {
    @Test
    fun testPart1() {
        val result = RestroomRedoubt.part1(input.split("\n"), 11, 7)
        assertEquals(12, result)
    }
}

private val input = """
p=0,4 v=3,-3
p=6,3 v=-1,-3
p=10,3 v=-1,2
p=2,0 v=2,-1
p=0,0 v=1,3
p=3,0 v=-2,-2
p=7,6 v=-1,-3
p=3,0 v=-1,-2
p=9,3 v=2,3
p=7,3 v=-1,2
p=2,4 v=2,-3
p=9,5 v=-3,-3
""".trimIndent()
