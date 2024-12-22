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

    fun part2(list: List<String>): Int {
        val numbers = list.map { it.toLong() }

        val newSecrets = numbers.map { n ->
            var secret = n
            repeat(2000) {
                secret = newSecret(secret)
            }
            secret
        }

        val prices = newSecrets.map { it % 10 }
        val changes = mutableListOf<Long>()




        return 0
    }

}


fun main() {
    val input = readInput("$pathPrefix24/day22.txt")
    val res1 = MonkeyMarket.part1(input)
    val res2 = MonkeyMarket.part2(input)

    checkResult(res1, 19458130434)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
