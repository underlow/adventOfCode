package me.underlow.advent2022.day11

import me.underlow.advent2022.checkResult
import kotlin.math.floor

data class NextMonkey(val number: Int, val worryLevel: Long)

data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val condition: (Long) -> Int,
    var inspectsCount: Long
) {
    // returns monkey number
    fun inspectAndThrow(itemNumber: Int): NextMonkey {
        val item = this.items[itemNumber]
        val worryLevel = floor(operation(item).toDouble() / 3).toLong()
        val nextMonkey = condition(worryLevel)
        inspectsCount++
        println("Item with worry level $worryLevel is thrown to monkey $nextMonkey")
        return NextMonkey(nextMonkey, worryLevel)
    }

    fun inspectAndThrow2(itemNumber: Int, divider: Long): NextMonkey {
        val item = this.items[itemNumber]
        val worryLevel = operation(item) % divider
        val nextMonkey = condition(worryLevel)
        inspectsCount++
//        println("Item with worry level $worryLevel is thrown to monkey $nextMonkey")
        return NextMonkey(nextMonkey, worryLevel)
    }
}

fun makeRound(monkeys: List<Monkey>): List<Monkey> {
    for ((idx, monkey) in monkeys.withIndex()) {
        println("Monkey $idx:")
        val newMonkeys = mutableListOf<NextMonkey>()
        for (i in monkey.items.indices) {
            val newMonkey = monkey.inspectAndThrow(i)
            newMonkeys += newMonkey
        }
        newMonkeys.forEachIndexed() { idx, newMonkey ->
            monkeys[newMonkey.number].items += newMonkey.worryLevel
        }
        monkey.items.clear()
    }
    return monkeys
}

fun makeRound2(monkeys: List<Monkey>, divider: Long): List<Monkey> {
    for ((idx, monkey) in monkeys.withIndex()) {
//        println("Monkey $idx:")
        val newMonkeys = mutableListOf<NextMonkey>()
        for (i in monkey.items.indices) {
            val newMonkey = monkey.inspectAndThrow2(i, divider)
            newMonkeys += newMonkey
        }
        newMonkeys.forEachIndexed() { idx, newMonkey ->
            monkeys[newMonkey.number].items += newMonkey.worryLevel
        }
        monkey.items.clear()
    }
    return monkeys
}

fun solution1(monkeys: List<Monkey>): Long {
    repeat(20) { makeRound(monkeys) }

    val sortedBy = monkeys.sortedBy { it.inspectsCount }.reversed()
    return sortedBy[0].inspectsCount * sortedBy[1].inspectsCount
}

fun solution2(monkeys: List<Monkey>, divider: Long): Long {
    repeat(10000) { makeRound2(monkeys, divider) }

    val sortedBy = monkeys.sortedBy { it.inspectsCount }.reversed()
    return sortedBy[0].inspectsCount * sortedBy[1].inspectsCount
}


fun main() {
    val r1 = solution1(monkeys)
    println(r1)

    val r2 = solution2(monkeys2, commonDivider)
    println(r2)

    checkResult(r1, 61503)
    checkResult(r2, 14081365540)
}

const val commonDivider = 19 * 3 * 13 * 17 * 2 * 11 * 5 * 7.toLong()
val monkeys = listOf(
    Monkey(mutableListOf(65, 58, 93, 57, 66), { it * 7L }, { if (it % 19 == 0L) 6 else 4 }, 0),
    Monkey(mutableListOf(76, 97, 58, 72, 57, 92, 82), { it + 4L }, { if (it % 3L == 0L) 7 else 5 }, 0),
    Monkey(mutableListOf(90, 89, 96), { it * 5L }, { if (it % 13L == 0L) 5 else 1 }, 0),
    Monkey(mutableListOf(72, 63, 72, 99), { it * it }, { if (it % 17L == 0L) 0 else 4 }, 0),
    Monkey(mutableListOf(65), { it + 1L }, { if (it % 2L == 0L) 6 else 2 }, 0),
    Monkey(mutableListOf(97, 71), { it + 8L }, { if (it % 11L == 0L) 7 else 3 }, 0),

    Monkey(mutableListOf(83, 68, 88, 55, 87, 67), { it + 2L }, { if (it % 5L == 0L) 2 else 1 }, 0),
    Monkey(mutableListOf(64, 81, 50, 96, 82, 53, 62, 92), { it + 5L }, { if (it % 7L == 0L) 3 else 0 }, 0),
)
// mutable structure, just copy paste it to prevent fails on second run (solution2)
val monkeys2 = listOf(
    Monkey(mutableListOf(65, 58, 93, 57, 66), { it * 7L }, { if (it % 19 == 0L) 6 else 4 }, 0),
    Monkey(mutableListOf(76, 97, 58, 72, 57, 92, 82), { it + 4L }, { if (it % 3L == 0L) 7 else 5 }, 0),
    Monkey(mutableListOf(90, 89, 96), { it * 5L }, { if (it % 13L == 0L) 5 else 1 }, 0),
    Monkey(mutableListOf(72, 63, 72, 99), { it * it }, { if (it % 17L == 0L) 0 else 4 }, 0),
    Monkey(mutableListOf(65), { it + 1L }, { if (it % 2L == 0L) 6 else 2 }, 0),
    Monkey(mutableListOf(97, 71), { it + 8L }, { if (it % 11L == 0L) 7 else 3 }, 0),

    Monkey(mutableListOf(83, 68, 88, 55, 87, 67), { it + 2L }, { if (it % 5L == 0L) 2 else 1 }, 0),
    Monkey(mutableListOf(64, 81, 50, 96, 82, 53, 62, 92), { it + 5L }, { if (it % 7L == 0L) 3 else 0 }, 0),
)
