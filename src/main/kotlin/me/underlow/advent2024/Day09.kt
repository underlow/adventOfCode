package me.underlow.advent2024

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInputAsString

object DiskFragmenter {

    data class DiskBlock(val empty: Boolean, val id: Int)

    fun part1(input: String): Long {
        val disk = mutableListOf<DiskBlock>()
        val compacted = mutableListOf<DiskBlock>()

        var switch = true
        var id = 0
        for (s in input) {
            if (switch) {
                repeat(s.asInt()) {
                    disk.add(DiskBlock(false, id))
                }
                id++
            } else {
                repeat(s.asInt()) {
                    disk.add(DiskBlock(true, 0))
                }
            }
            switch = !switch
        }

        var sP = 0
        var eP = disk.size - 1

        while (sP <= eP) {
            val db = disk[sP]

            if (!db.empty) {
                compacted.add(db)
                sP++
//                print(compacted)
                continue
            }

            while (disk[eP].empty && eP >= sP)
                eP--

            compacted.add(disk[eP])
            eP--
            sP++
//            print(compacted)
        }

        return compacted.mapIndexed { idx, d -> idx * d.id.toLong() }.sum()
    }

    fun print(l: List<DiskBlock>) {
        val ll = l.joinToString("") { if (it.empty) "." else it.id.toString() }
        println(ll)
    }

    fun part2(list: String): Int {
        return 0
    }

    fun Char.asInt() = code - '0'.code

}


fun main() {
    val input = readInputAsString("$pathPrefix24/day09.txt")
    val res1 = DiskFragmenter.part1(input)
    val res2 = DiskFragmenter.part2(input)

    checkResult(res1, 6353658451014) // 1401820230 low
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
