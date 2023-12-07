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

            val grouped = cards.groupBy { it }.mapValues { it.value.size }
            val valuesSet = grouped.values.toList().sorted()
            return when (valuesSet) {
                listOf(5) -> HandType.Five
                listOf(1, 4) -> HandType.Four
                listOf(2, 3) -> HandType.Fullhouse
                listOf(1, 1, 3) -> HandType.Three
                listOf(1, 2, 2) -> HandType.TwoPairs
                listOf(1, 1, 1, 2) -> HandType.Pair
                listOf(1, 1, 1, 1, 1) -> HandType.HighCard
                else -> error("Oops")
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
        val hands = parseInput(list)

        val sortedHands = hands.sortedWith(Comparator { o1, o2 ->
            val t1 = o1.handType()
            val t2 = o2.handType()

            if (t1 != t2) return@Comparator t1.rank.compareTo(t2.rank)

            for (i in o1.cards.indices) {
                val o1c = o1.cards[i]
                val o2c = o2.cards[i]

                val o1r = if (o1c == CardType.J) 0 else o1c.order
                val o2r = if (o2c == CardType.J) 0 else o2c.order

                if (o1c != o2c)
                    return@Comparator o1r.compareTo(o2r)
            }

            error("Oops")
        })

        return sortedHands.mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()
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

    checkResult(res1, 250120186)
    checkResult(res2, 0)
}
