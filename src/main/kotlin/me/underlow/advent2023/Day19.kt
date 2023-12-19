package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInputAsString

object Aplenty {

    data class Workflow(val name: String, val rules: List<Rule>) {
        fun apply(parts: List<Part>): String {
            for (rule in rules) {
                val a = rule.apply(parts)
                if (a.isNotEmpty())
                    return a
            }
            error("Oops")
        }
    }

    interface Rule {
        fun apply(parts: List<Part>): String
    }

    data class IfRule(val symbol: String, val c: Char, val i: Int, val dir: String) : Rule {
        fun compareNum(n: Int): Boolean {
            if (c == '>' && n > i) return true
            if (c == '<' && n < i) return true
            return false
        }


        override fun apply(parts: List<Part>): String {
            for (part in parts) {
                if (part.name == symbol && compareNum(part.rating))
                    return dir
            }
            return ""
        }
    }

    data class Pass(val to: String) : Rule {
        override fun apply(parts: List<Part>): String = to
    }

    object Accept : Rule {
        override fun apply(parts: List<Part>): String = "A"
    }

    object Reject : Rule {
        override fun apply(parts: List<Part>): String = "R"
    }

    data class Part(val name: String, val rating: Int)


    fun part1(list: String): Int {
        val (workflows, parts) = parseInput(list)
        val accepted = mutableListOf<List<Part>>()

        for (part in parts) {


            val start = workflows.find { it.name == "in" }!!
            var current = start
            var cont = true
            while (cont) {
                val res = current.apply(part)
                if (res == "A") {
                    accepted.add(part)
                    cont = false
                    continue
                }
                if (res == "R") {
                    cont = false
                    continue
                }
                current = workflows.find { it.name == res }!!
            }
        }

        return accepted.map { it.map { it.rating }.sum() }.sum()
    }

    fun part2(list: String): Int {
        val directions = parseInput(list)
        return 0
    }

    private fun parseInput(list: String): Pair<List<Workflow>, List<List<Part>>> {
        val rulesS = list.split("\n\n")[0]
        val partsS = list.split("\n\n")[1]

        val workflows = rulesS.split("\n").map { parseWorkflow(it) }

        val parts = partsS.split("\n").map { parseParts(it) }
        return (workflows to parts)
    }

    private fun parseParts(it: String): List<Part> {
        //{x=787,m=2655,a=1222,s=2876}
        val s = it.substring(1, it.length - 1)
        val p = s.split(",")
        return p.map { parsePart(it) }
    }

    private fun parsePart(it: String): Part {
        val n = it.substring(0, it.indexOf('='))
        val i = it.substring(it.indexOf('=') + 1).toInt()
        return Part(n, i)
    }

    private fun parseWorkflow(line: String): Workflow {
        //px{a<2006:qkq,m>2090:A,rfg}
        val name = line.substring(0, line.indexOfFirst { it == '{' })
        val l = line.substring(line.indexOf("{") + 1, line.indexOf("}"))
        val r = l.split(',').map { parseRule(it) }


        return Workflow(name, r)
    }

    private fun parseRule(it: String): Rule {
        val hasSign = it.any { it == '>' || it == '<' }

        if (!hasSign)
            return Pass(it)

        if (it.indexOf('<') >= 0) {
            val s = it.substring(0, it.indexOf('<'))
            val sign = '<'
            val n = it.substring(it.indexOf('<') + 1, it.indexOf(':'))
            val dir = it.substring(it.indexOf(':') + 1)
            return IfRule(s, sign, n.toInt(), dir)
        }
        if (it.indexOf('>') >= 0) {
            val s = it.substring(0, it.indexOf('>'))
            val sign = '>'
            val n = it.substring(it.indexOf('>') + 1, it.indexOf(':'))
            val dir = it.substring(it.indexOf(':') + 1)
            return IfRule(s, sign, n.toInt(), dir)
        }

        error("oops")
    }
}


fun main() {
    val input = readInputAsString("$pathPrefix23/day19.txt")
    val res1 = Aplenty.part1(input)
    val res2 = Aplenty.part2(input)

    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 0)
    checkResult(res2, 0)
}
