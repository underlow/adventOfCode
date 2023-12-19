package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInputAsString
import java.math.BigInteger

object Aplenty {

    data class Workflow(val name: String, val rules: List<Rule>) {
        fun apply(parts: List<Part>): List<Pair<String, List<Part>>> {
            val pairs = mutableListOf<Pair<String, List<Part>>>()
            var rest = listOf<Part>(*parts.toTypedArray())
            for (rule in rules) {
                val a = rule.apply(rest)

                rest = a.filter { it.first == "" }.firstOrNull()?.second ?: emptyList()
                pairs.addAll(a.filter { it.first != "" })

                if (a.filter { it.first != "" }.size > 1) {
                    println("dfger")
                }

                if (rest.isEmpty())
                    return pairs
            }
            return pairs
        }
    }

    interface Rule {
        fun apply(parts: List<Part>): List<Pair<String, List<Part>>>
    }

    data class IfRule(val symbol: String, val c: Char, val i: Int, val dir: String) : Rule {
        override fun apply(parts: List<Part>): List<Pair<String, List<Part>>> {
            for (part in parts) {
                if (part.name == symbol) {
                    val restOfPartList = parts - part
                    if (i in part.rating) {
                        if (c == '<') {
                            val newPartLow = part.copy(rating = part.rating.first..(i - 1))
                            val newPartUp = part.copy(rating = i..part.rating.last)
                            if (i == part.rating.first)
                                return listOf(

                                    "" to (restOfPartList + newPartUp)
                                )
                            return listOf(
                                dir to (restOfPartList + newPartLow),
                                "" to (restOfPartList + newPartUp)
                            )
                        } else { // c == '>'
                            val newPartLow = part.copy(rating = part.rating.first..i)
                            val newPartUp = part.copy(rating = (i + 1)..part.rating.last)
                            if (i == part.rating.last) {
                                return listOf(
                                    "" to (restOfPartList + newPartLow),
                                )
                            }
                            return listOf(
                                "" to (restOfPartList + newPartLow),
                                dir to (restOfPartList + newPartUp)
                            )
                        }
                    } else {
                        if (c == '<' && i < part.rating.first) {
                            return listOf("" to (parts))
                        }
                        if (c == '<' && i > part.rating.last) {
                            return listOf(dir to (parts))
                        }
                        if (c == '>' && i < part.rating.first) {
                            return listOf(dir to (parts))
                        }
                        if (c == '>' && i > part.rating.last) {
                            return listOf("" to (parts))
                        }
                    }
                }


            }

            return error("oops")
        }
    }

    data class Pass(val to: String) : Rule {
        override fun apply(parts: List<Part>): List<Pair<String, List<Part>>> = listOf(to to parts)
    }

    data class Part(val name: String, val rating: IntRange)


    fun part1(list: String): BigInteger {
        val (workflows, parts) = parseInput(list)
        val accepted = calc(workflows, parts)

        return accepted.map { it.map { it.rating.first }.sum() }.sum().toBigInteger()
    }

    private fun calc(workflows: List<Workflow>, parts: List<List<Part>>): MutableList<List<Part>> {

        val accepted = mutableListOf<List<Part>>()
        val start = workflows.find { it.name == "in" }!!
        val queue = mutableListOf<Pair<Workflow, List<Part>>>()
        for (part in parts) {
            queue.add(start to part)
        }

        while (queue.isNotEmpty()) {
            val part = queue.last()
            queue.removeAt(queue.lastIndex)

            val result = part.first.apply(part.second)
            for (res in result) {
                if (res.first == "A") {
                    accepted.add(part.second)
                    continue
                }
                if (res.first == "R") {
                    continue
                }
                val current = workflows.find { it.name == res.first }!!
                queue.add(current to res.second)
            }
        }
        println(accepted.sortedBy { it.first().rating.first }
            .map { it.joinToString { "Part(name=${it.name}, rating=${it.rating.first.toString()})" } }
            .joinToString("\n"))
        return accepted
    }

    fun part2(list: String): BigInteger {
        val (workflows, _) = parseInput(list)
        val parts = listOf(
            listOf(
                Part("x", 1..4000),
                Part("m", 1..4000),
                Part("a", 1..4000),
                Part("s", 1..4000),
            )
        )
        val accepted = calc(workflows, parts)

        val map = accepted
            .map {
                val reduce =
                    it.map { BigInteger.valueOf(it.rating.count().toLong()) }.reduce { acc, unit -> acc.multiply(unit) }
                reduce
            }
        return map.reduce { acc, unit -> acc.add(unit) }
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
        return Part(n, i..i)
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

    checkResult(res1, BigInteger.valueOf(406934))
    checkResult(res2, 0) // 399931924904867 high
}
