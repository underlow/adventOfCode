import me.underlow.advent2024.LANParty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LANPartyTest {
    @Test
    fun testPart1() {
        val result = LANParty.part1(input.split("\n"))
        assertEquals(7, result)
    }

    @Test
    fun testPart2() {
        val result = LANParty.part2(input.split("\n"))
        assertEquals("co,de,ka,ta", result)
    }
}

private val input = """
kh-tc
qp-kh
de-cg
ka-co
yn-aq
qp-ub
cg-tb
vc-aq
tb-ka
wh-tc
yn-cg
kh-ub
ta-co
de-co
tc-td
tb-wq
wh-td
ta-ka
td-qp
aq-cg
wq-ub
ub-vc
de-ta
wq-aq
wq-vc
wh-yn
ka-de
kh-ta
co-tc
wh-qp
tb-vc
td-yn
""".trimIndent()
