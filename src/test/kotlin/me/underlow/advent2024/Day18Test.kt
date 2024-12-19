import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RAMRunTest {
    @Test
    fun testPart1() {
        val result = RAMRun.part1(input.split("\n"), steps = 12, x = 7, y = 7)
        assertEquals(22, result)
    }

}

private val input = """
 5,4
 4,2
 4,5
 3,0
 2,1
 6,3
 2,4
 1,5
 0,6
 3,3
 2,6
 5,1
 1,2
 5,5
 2,5
 6,5
 1,4
 0,4
 6,4
 1,1
 6,1
 1,0
 0,5
 1,6
 2,0
""".trimIndent()
