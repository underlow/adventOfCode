package me.underlow.advent2022.day11

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class MonkeyInTheMiddleKtTest {

    @Test
    fun solution1() {
        val result = solution1(monkeys)

        assertEquals(10605, result)
    }
    @Test
    fun testSolution2() {
        val result = solution2(monkeys2, 23*19*13*17)

        assertEquals(2713310158L, result)
    }
}

val monkeys = listOf(
    Monkey(mutableListOf(79, 98), { it * 19 }, { if (it % 23L == 0L) 2 else 3 }, 0),
    Monkey(mutableListOf(54, 65, 75, 74), { it + 6 }, { if (it % 19L == 0L) 2 else 0 }, 0),
    Monkey(mutableListOf(79, 60, 97), { it * it }, { if (it % 13L == 0L) 1 else 3 }, 0),
    Monkey(mutableListOf(74), { it + 3 }, { if (it % 17L == 0L) 0 else 1 }, 0),
)
val monkeys2 = listOf(
    Monkey(mutableListOf(79, 98), { it * 19 }, { if (it % 23L == 0L) 2 else 3 }, 0),
    Monkey(mutableListOf(54, 65, 75, 74), { it + 6 }, { if (it % 19L == 0L) 2 else 0 }, 0),
    Monkey(mutableListOf(79, 60, 97), { it * it }, { if (it % 13L == 0L) 1 else 3 }, 0),
    Monkey(mutableListOf(74), { it + 3 }, { if (it % 17L == 0L) 0 else 1 }, 0),
)

/*
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
 */
