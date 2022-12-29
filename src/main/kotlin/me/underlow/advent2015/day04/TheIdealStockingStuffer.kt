package me.underlow.advent2015.day04

import me.underlow.advent2022.checkResult
import java.math.BigInteger
import java.security.MessageDigest

// https://adventofcode.com/2015/day/4https://adventofcode.com/2015/day/4

object TheIdealStockingStuffer {

    fun part1(list: String, prefix: String): Int {
        var res = 0
        while (true) {
            val md5 = md5(list + res.toString())
            if (md5.startsWith(prefix)) {
                println(md5)
                return res
            }
            res++
        }
        return 0
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    fun part2(list: String): Int {
        return part1(list, "000000")
    }

}

fun main() {
    val res1 = TheIdealStockingStuffer.part1("ckczppom", "00000")
    val res2 = TheIdealStockingStuffer.part2("ckczppom")

    checkResult(res1, 117946)
    checkResult(res2, 3938038)

    println(res1)
    println(res2)
}
