import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInputAsString
import me.underlow.advent2024.pathPrefix24

object PrintQueue {

    fun part1(list: String): Int {
        val (rules, lists) = parseInput(list)

        val mapLower = mutableMapOf<Int, MutableSet<Int>>()

        rules.forEach { (n1, n2) ->
            mapLower.getOrPut(n1) { mutableSetOf() }.add(n2)
        }

        var sum = 0

        for (ints in lists) {
            val r = ints.mapIndexed { idx, l ->
                var valid = true
                for (i in 0 until idx) {
                    if (mapLower[l]?.contains(ints[i]) ?: false)
                        valid = false
                }
                return@mapIndexed valid
            }

            if (r.all { it })
                sum += ints[ints.size / 2]
        }


        return sum
    }

    fun part2(list: String): Int {
        val directions = parseInput(list)
        return 0
    }


    private fun parseInput(list: String): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
        val p1 = list.split("\n\n")[0].split('\n').map {
            it.split('|')[0].toInt() to it.split('|')[1].toInt()
        }

        val p2: List<List<Int>> = list.split("\n\n")[1].split("\n").map {
            it.split(',').map { it.toInt() }
        }

        return p1 to p2
    }
}


fun main() {
    val input = readInputAsString("$pathPrefix24/day05.txt")
    val res1 = PrintQueue.part1(input)
    val res2 = PrintQueue.part2(input)

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
