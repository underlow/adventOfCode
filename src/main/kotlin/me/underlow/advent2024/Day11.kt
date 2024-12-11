package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import java.math.BigInteger

object PlutonianPebbles {

    fun part1(list: String): Int {
        val stones = parseInput(list)

        val mList = mutableListOf<BigInteger>()
        mList += stones


        repeat(25) {
            var i = 0
            println("Stones: $mList")

            while (i < mList.size) {
                val current = mList[i]
                if (current == BigInteger.ZERO) {
                    mList[i] = current + BigInteger.ONE
                    i++
                    continue
                }

                if (current.toString().length % 2 == 0) {
                    val s = current.toString()
                    val s1 = s.substring(0, s.length / 2)
                    val s2 = s.substring(s.length / 2)
                    mList[i] = s1.toBigInteger()
                    mList.add(i + 1, s2.toBigInteger())
                    i += 2
                    continue
                }

                mList[i] = current * BigInteger.valueOf(2024)
                i++
            }
        }

        return mList.size
    }

    fun part2(list: String): Int {
        val stones = parseInput(list)

        val mList = mutableListOf<BigInteger>()
        mList += stones


        repeat(75) {
            var i = 0
//            println("Stones: $mList")

            while (i < mList.size) {
                val current = mList[i]
                if (current == BigInteger.ZERO) {
                    mList[i] = current + BigInteger.ONE
                    i++
                    continue
                }

                if (current.toString().length % 2 == 0) {
                    val s = current.toString()
                    val s1 = s.substring(0, s.length / 2)
                    val s2 = s.substring(s.length / 2)
                    mList[i] = s1.toBigInteger()
                    mList.add(i + 1, s2.toBigInteger())
                    i += 2
                    continue
                }

                mList[i] = current * BigInteger.valueOf(2024)
                i++
            }
        }

        return mList.size
    }

    private fun parseInput(list: String): List<BigInteger> {
        return list.split(" ").filter { it.isNotBlank() }.map { it.toBigInteger() }
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day11.txt")
    val res1 = PlutonianPebbles.part1("4 4841539 66 5279 49207 134 609568 0")
    val res2 = PlutonianPebbles.part2("4 4841539 66 5279 49207 134 609568 0")

    checkResult(res1, 212655)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
