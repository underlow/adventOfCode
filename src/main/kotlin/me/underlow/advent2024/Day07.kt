import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import me.underlow.advent2024.pathPrefix24

object BridgeRepair {

    fun part1(list: List<String>): Long {
        val eqs = parseInput(list)

        val good = eqs.filter { it.isValid() }

        return good.sumOf { it.result }
    }

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: List<String>): List<Eq> {
        return list.map {
            val result = it.split(":")[0].toLong()
            val numbers = it.split(':')[1].split(' ').filter { it.isNotBlank() }.map { it.toLong() }.toList()

            return@map Eq(result, numbers)
        }
    }

    enum class Op(val op: String) {
        Plus("+"), Multiply("*")
    }

    data class Eq(val result: Long, val numbers: List<Long>)
    data class Task(val eq: Eq, val result: Long, val restNumbers: List<Long>, val op: List<Op>)


    private fun Eq.isValid(): Boolean {
        val tasks = mutableListOf<Task>()
        tasks += Task(this, this.numbers[0], this.numbers.subList(1, this.numbers.size), listOf())
//        tasks += Task(this, 0L, this.numbers.subList(1, this.numbers.size), listOf())

        while (tasks.isNotEmpty()) {
            val t = tasks.removeLast()

            // check if it is expected result

            if (t.result == this.result) {
                println("GOOD: ${this.result}, $numbers, ${t.op}, ___ ${pr(numbers, t.op)} ")

                if (validate(numbers, t.op) != this.result) {
                    println("OOOPS")
                }

                return true
            }

            if (t.result > this.result)
                continue

            if (t.restNumbers.isEmpty())
                continue


            val restList = if (t.restNumbers.size == 1) emptyList() else t.restNumbers.subList(1, t.restNumbers.size)
            val t1 =
                Task(
                    this,
                    t.result + t.restNumbers[0],
                    restList,
                    t.op + Op.Plus
                )
            val t2 = Task(
                this,
                t.result * t.restNumbers[0],
                restList,
                t.op + Op.Multiply
            )

            tasks += t1
            tasks += t2
        }
        return false
    }

    fun pr(numbers: List<Long>, op: List<Op>): String {
        return buildString {
            for (i in numbers.indices) {
                append(numbers[i])
                if (i in op.indices) {
                    append(op[i].op)
                }
            }
        }
    }

    fun validate(numbers: List<Long>, op: List<Op>): Long {
        var s = numbers[0]
        for (i in numbers.indices) {
            if (i == 0) continue
            if ((i - 1) in op.indices) {
                s = if (op[i - 1] == Op.Plus) s + numbers[i] else s * numbers[i]
            }
        }

        return s
    }


}


fun main() {
    val input = readInput("$pathPrefix24/day07.txt")
    val res1 = BridgeRepair.part1(input)
    val res2 = BridgeRepair.part2(input)

    checkResult(res1, 0) // 6083020304082 high
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
