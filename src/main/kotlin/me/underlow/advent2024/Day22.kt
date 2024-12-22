package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import kotlin.math.floor

object MonkeyMarket {

    fun part1(list: List<String>): Long {
        val numbers = list.map { it.toLong() }

        val newSecrets = numbers.map { n ->
            var secret = n
            repeat(2000) {
                secret = newSecret(secret)
            }
            secret
        }

        return newSecrets.sum()
    }

    fun mix(n1: Long, n2: Long): Long {
        return n2 xor n1
    }

    fun prune(n1: Long): Long {
        return n1 % 16777216
    }


    fun newSecret(n: Long): Long {
        val res = n
        // step 1
        val s1 = prune(mix(res, res * 64))
        // step 2
        val s2 = prune(mix(s1, floor(s1.toDouble() / 32).toLong()))

        // step 3
        val s3 = prune(mix(s2, s2 * 2048))

        return s3
    }

    data class Shopper(val startSecret: Long, val secrets: List<Long>, val prices: List<Long>, val changes: List<Long>)

    fun toShopper(n: Long): Shopper {
        val secrets = mutableListOf<Long>()
        secrets += n
        repeat((2000)) {
            secrets += newSecret(secrets.last())
        }

        val prices = secrets.map { it % 10 }
        val changes = mutableListOf<Long>()
        var lastPrice = 0L

        for (i in prices.indices) {
            changes += (prices[i] - lastPrice)
            lastPrice = prices[i]
        }



        return Shopper(n, secrets, prices, changes)
    }

    data class Sequence(val n1: Long, val n2: Long, val n3: Long, val n4: Long) {
        companion object {
            fun fromList(list: List<Long>, i: Int) =
                Sequence(list[i], list[i + 1], list[i + 2], list[i + 3])
        }
    }

    fun part2(list: List<String>): Long {
        val numbers = list.map { it.toLong() }

        val shoppers = numbers.map {
            toShopper(it)
        }


        val sequences = mutableSetOf<Sequence>()

        shoppers.forEach { shopper ->
            for (i in 0 until shopper.changes.size - 3) {
                //it can be easily improved if we store all places where this pattern appears,
                // but brute force works for this input in minutes, that's ok
                sequences += Sequence.fromList(shopper.changes, i)
            }
        }
        println("Total sequences: ${sequences.size}")
        val dfd = sequences.mapIndexed() { idx, sequence ->
            // find a gain for this sequence
            if (sequence == Sequence(-2, 1, -1, 3)) {
                println()
            }

            val gains = shoppers.map {
                val changes = it.changes
                for (i in 0 until changes.size - 3) {
                    if (changes[i] != sequence.n1)
                        continue
                    if (changes[i + 1] != sequence.n2)
                        continue
                    if (changes[i + 2] != sequence.n3)
                        continue
                    if (changes[i + 3] != sequence.n4)
                        continue
                    if (Sequence.fromList(changes, i) == sequence)
                        return@map it.prices[i + 3]
                }
                return@map 0
            }
            println("Sequence $idx gain is ${gains.sum()}")

            return@mapIndexed gains.sum()
        }


        return dfd.max()
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day22.txt")
    val res1 = MonkeyMarket.part1(input)
    val res2 = MonkeyMarket.part2(input)

    checkResult(res1, 19458130434)
    checkResult(res2, 2130) // 2121 low

    println(res1)
    println(res2)
}
