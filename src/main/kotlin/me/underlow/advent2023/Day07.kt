package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object CamelCards {
    enum class CardType(val order: Int) {
        A(1),
        K(2),
        Q(3),
        J(4),
        T(5),
        NINE(6),
        EIGHT(7),
        SEVEN(8),
        SIX(9),
        FIVE(10),
        FOUR(11),
        THREE(12),
        TWO(13);

        companion object {
            fun fromString(string: String): CardType = when (string) {
                "A" -> A
                "K" -> K
                "Q" -> Q
                "J" -> J
                "T" -> T
                "9" -> NINE
                "8" -> EIGHT
                "7" -> SEVEN
                "6" -> SIX
                "5" -> FIVE
                "4" -> FOUR
                "3" -> THREE
                "2" -> TWO
                else -> error("Oops")
            }
        }
    }

    data class Card(val type: CardType)

    enum class HandType(val rank: Int) {
        Five(6),
        Four(5),
        Fullhouse(4),
        Three(3),
        TwoPairs(2),
        Pair(1),
        HighCard(0);
    }

    data class Hand(val cards: List<CardType>, val bid: Int) {
        fun handType(): HandType {
            return when {
                cards.toSet().size == 1 -> return HandType.Five
                cards.toSet().size == 5 -> return HandType.HighCard
                else -> {
                    val grouped = cards.groupBy { it }.mapValues { it.value.size }
                    val valuesSet = grouped.values.toSet()
                    return when {
                        grouped.keys.size == 2 && valuesSet == setOf(1, 4) -> HandType.Four
                        grouped.keys.size == 2 && valuesSet == setOf(2, 3) -> HandType.Fullhouse
                        grouped.keys.size == 3 && grouped.values.toList().sorted() == listOf(1, 1, 3) -> HandType.Three
                        grouped.keys.size == 3 && grouped.values.toList().sorted() == listOf(
                            1,
                            2,
                            2
                        ) -> HandType.TwoPairs

                        grouped.keys.size == 4 && grouped.values.toList().sorted() == listOf(
                            1,
                            1,
                            1,
                            2
                        ) -> HandType.Pair

                        else -> error("Oops")
                    }
                }
            }


        }
    }

    fun part1(list: List<String>): Int {
        val hands = parseInput(list)

        val sortedHands = hands.sortedWith(Comparator { o1, o2 ->
            val t1 = o1.handType()
            val t2 = o2.handType()

            if (t1 != t2) return@Comparator t1.rank.compareTo(t2.rank)

            for (i in o1.cards.indices) {
                val o1c = o1.cards[i]
                val o2c = o2.cards[i]

                if (o1c != o2c)
                    return@Comparator -o1c.order.compareTo(o2c.order)
            }

            error("Oops")
        })

        return sortedHands.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<Hand> {
        return list.map { l ->
            val split = l.split(" ")
            val cards = split[0].map { CardType.fromString(it.toString()) }
            val bid = split[1].toInt()
            return@map Hand(cards, bid)
        }
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day07.txt")
    val res1 = CamelCards.part1(input)
    val res2 = CamelCards.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 0)
    checkResult(res2, 0)
}
