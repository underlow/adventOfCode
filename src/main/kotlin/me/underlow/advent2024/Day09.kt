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

    data class DB2(val empty: Boolean, val id: Int, val len: Int)

    fun part2(input: String): Long {
        val disk = mutableListOf<DB2>()
        val compacted = mutableListOf<DiskBlock>()

        var switch = true
        var id = 0
        for (s in input) {
            if (switch) {
                disk.add(DB2(false, id, s.asInt()))
                ++id
            } else {
                disk.add(DB2(true, -1, s.asInt()))
            }
            switch = !switch
        }

        var eP = disk.size - 1
        while (eP >= 0) {

            if (disk[eP].empty) {
                eP--
                continue
            }
            var moved = false
            for (i in 0 until eP) {
                if (!disk[i].empty)
                    continue

                if (disk[i].len >= disk[eP].len) {
                    if (disk[i].len > disk[eP].len) {
                        disk.add(i + 1, DB2(true, disk[i].id, disk[i].len - disk[eP].len))
                        disk[i] = disk[eP + 1]
                        disk[eP + 1] = disk[eP + 1].copy(empty = true, id = -1)

                    } else {
                        disk[i] = disk[eP]
                        disk[eP] = disk[eP].copy(empty = true, id = -1)

                    }
                    eP--
                    moved = true
                    break
                }

            }

            if (!moved)
                eP--
        }

        var id1 = 0
        for (s in disk) {
            if (!s.empty) {
                repeat(s.len) {
                    compacted.add(DiskBlock(false, s.id))
                }
                id1++
            } else {
                repeat(s.len) {
                    compacted.add(DiskBlock(true, 0))
                }
            }
        }

//        print(compacted)

        return compacted.mapIndexed { idx, d -> idx * d.id.toLong() }.sum()

    }

    fun Char.asInt() = code - '0'.code

}


fun main() {
    val input = readInputAsString("$pathPrefix24/day09.txt")
    val res1 = DiskFragmenter.part1(input)
    val res2 = DiskFragmenter.part2(input)

    checkResult(res1, 6353658451014) // 1401820230 low
    checkResult(res2, 6382582136592)

    println(res1)
    println(res2)
}
