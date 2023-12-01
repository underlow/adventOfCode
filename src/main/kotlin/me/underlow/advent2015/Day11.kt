package me.underlow.advent2015

import me.underlow.advent2022.checkResult

object CorporatePolicy {

    fun part1(password: String): String {
        while (true) {
            password.increment()
            if (req1(password) && req2(password) && req3(password))
                return password

        }
    }

    private fun req3(password: String): Boolean {
        TODO("Not yet implemented")
    }

    private fun req2(password: String): Boolean {
        TODO("Not yet implemented")
    }

    private fun req1(password: String): Boolean {
        TODO("Not yet implemented")
    }

    fun part2(list: String): Int {
        return 0
    }

}

private fun String.increment() {
    TODO("Not yet implemented")
}


fun main() {
    val res1 = CorporatePolicy.part1("vzbxkghb")
    val res2 = CorporatePolicy.part2("input")

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}

