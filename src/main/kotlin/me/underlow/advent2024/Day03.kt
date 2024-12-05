import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24

object MullItOver {

    fun part1(list: List<String>): Long {
        val string = parseInput(list)

        var sum = 0L
        var startIdx = 0
        do {
            if (string.substring(startIdx).startsWith("mul(")) {
                println("Found mul( at index $startIdx")
                val suffix = string.substring(startIdx)
                val substring = suffix.substring(
                    3,
                    suffix.indexOfFirst { it == ')' } + 1)
                println("Checking $substring substring")
                val parseParentheses = parseParentheses(substring)
                println("found $parseParentheses value")
                sum += parseParentheses
            }


            startIdx++

        } while (startIdx < string.length)
        return sum
    }

    private fun parseParentheses(substring: String): Int {
        if (substring.filterNot { it.isDigit() || it == '(' || it == ')' || it == ',' }.isNotEmpty()) {
            println("WWWWWW")
            return 0
        }
        try {
            val p = substring.substring(substring.indexOfFirst { it == '(' } + 1, substring.indexOfFirst { it == ')' })
            val s = p.split(',')
            if (s[0].toInt() > 999 || s[1].toInt() > 999)
                return 0
            return s[0].toInt() * s[1].toInt() // todo: check s[0] < 10000
        } catch (e: Exception) {
            return 0
        }

    }

    fun part2(list: List<String>): Long {
        val string = parseInput(list)

        var sum = 0L
        var startIdx = 0
        var enable = true
        do {
            if (string.substring(startIdx).startsWith("do()")) {
                enable = true
            }
            if (string.substring(startIdx).startsWith("don't()")) {
                enable = false
            }

            if (string.substring(startIdx).startsWith("mul(")) {
                println("Found mul( at index $startIdx")
                val suffix = string.substring(startIdx)
                val substring = suffix.substring(
                    3,
                    suffix.indexOfFirst { it == ')' } + 1)
                println("Checking $substring substring")
                val parseParentheses = parseParentheses(substring)
                println("found $parseParentheses value")
                sum += if (enable) parseParentheses else 0
            }


            startIdx++

        } while (startIdx < string.length)
        return sum
    }

    private fun parseInput(list: List<String>): String {
        return list.joinToString()
    }
}


fun main() {
    val input = readInput("$pathPrefix24/day03.txt")
    val res1 = MullItOver.part1(input)
    val res2 = MullItOver.part2(input)

    checkResult(res1, 155955228) // not 23968600
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
