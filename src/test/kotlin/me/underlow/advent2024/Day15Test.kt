import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class WarehouseWoesTest {
    @Test
    fun testPart1() {
        val result = WarehouseWoes.part1(input.split("\n"))
        assertEquals(10092, result)
    }

    @Test
    fun testPart12() {
        val result = WarehouseWoes.part1(input2.split("\n"))
        assertEquals(2028, result)
    }

    @Test
    fun testPart2() {
        val result = WarehouseWoes.part2(input.split("\n"))
        assertEquals(0, result)
    }
}

private val input = """
 ##########
 #..O..O.O#
 #......O.#
 #.OO..O.O#
 #..O@..O.#
 #O#..O...#
 #O..O..O.#
 #.OO.O.OO#
 #....O...#
 ##########

 <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
 vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
 ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
 <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
 ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
 ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
 >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
 <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
 ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
 v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
""".trimIndent()

private val input2 = """
    ########
    #..O.O.#
    ##@.O..#
    #...O..#
    #.#.O..#
    #...O..#
    #......#
    ########

    <^^>>>vv<v>>v<<
""".trimIndent()
