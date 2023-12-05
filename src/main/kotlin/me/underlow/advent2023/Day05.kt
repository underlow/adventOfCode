package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput
import kotlin.math.min

object Fertilizer {
    data class Range(val dest: Long, val source: Long, val count: Long)

    // binary search
    class RangeMap {
        private val ranges = mutableListOf<Range>()
        fun addRange(dest: Long, source: Long, count: Long) {
            ranges.add(Range(dest, source, count))
            ranges.sortBy { it.source }
        }

        operator fun get(from: Long): Long {
            val range = ranges.binarySearch {
                if (from < it.source)
                    return@binarySearch 1

                if (from >= it.source + it.count)
                    return@binarySearch -1

                return@binarySearch 0

            }

            if (range < 0) {
//                print(" -> $from")
                return from
            }

            if (from in ranges[range].source until ranges[range].source + ranges[range].count) {
//                print(" -> ${from - ranges[range].source + ranges[range].dest}")
                return from - ranges[range].source + ranges[range].dest
            } else {
                error("Oops")
            }
        }
    }

    fun part1(list: List<String>): Long {
        val almanac = parseInput(list)
        return almanac.seeds.map { seed ->
            with(almanac) {
                humidityToLocation[
                    temperatureToHumidity[
                        lightToTemperature[
                            waterToLight[
                                fertilizerToWater[
                                    soilToFertilizer[
                                        seedToSoil[
                                            seed]]]]]]]
            }
        }.min()
    }

    fun part2(list: List<String>): Long {
        val almanac = parseInput(list)

        var min = Long.MAX_VALUE

        for (i in 0 until almanac.seeds.size step 2) {
            val from = almanac.seeds[i]
            val count = almanac.seeds[i + 1]

            println("Processing $i pair from ${almanac.seeds.size}: $from $count")

            for (j in from until (from + count)) {
                val location = with(almanac) {
                    humidityToLocation[
                        temperatureToHumidity[
                            lightToTemperature[
                                waterToLight[
                                    fertilizerToWater[
                                        soilToFertilizer[
                                            seedToSoil[
                                                j]]]]]]]
                }

                min = min(min, location)
            }
        }


        return min
    }

    data class Almanac(
        val seeds: List<Long>,
        val seedToSoil: RangeMap,

        val soilToFertilizer: RangeMap,
        val fertilizerToWater: RangeMap,
        val waterToLight: RangeMap,
        val lightToTemperature: RangeMap,
        val temperatureToHumidity: RangeMap,
        val humidityToLocation: RangeMap
    )


    private fun parseInput(list: List<String>): Almanac {
        var n = 1
        val seeds = list[0]
            .substring(list[0].indexOfFirst { it == ':' } + 1)
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { it.toLong() }

        val seedToSoil = RangeMap()
        val soilToFertilizer = RangeMap()
        val fertilizerToWater = RangeMap()
        val waterToLight = RangeMap()
        val lightToTemperature = RangeMap()
        val temperatureToHumidity = RangeMap()
        val humidityToLocation = RangeMap()

        var current = seedToSoil

        while (n < list.size) {
            when {
                list[n] == "seed-to-soil map:" -> current = seedToSoil
                list[n] == "soil-to-fertilizer map:" -> current = soilToFertilizer
                list[n] == "fertilizer-to-water map:" -> current = fertilizerToWater
                list[n] == "water-to-light map:" -> current = waterToLight
                list[n] == "light-to-temperature map:" -> current = lightToTemperature
                list[n] == "temperature-to-humidity map:" -> current = temperatureToHumidity
                list[n] == "humidity-to-location map:" -> current = humidityToLocation
                list[n] == "" -> {/*nothing*/
                }

                else -> {
                    val nn = list[n].split(" ")
                    val dest = nn[0].toLong()
                    val source = nn[1].toLong()
                    val count = nn[2].toLong()
                    current.addRange(dest, source, count)
                }
            }
            n++
        }


        return Almanac(
            seeds,
            seedToSoil,
            soilToFertilizer,
            fertilizerToWater,
            waterToLight,
            lightToTemperature,
            temperatureToHumidity,
            humidityToLocation
        )
    }
}


fun main() {
    val input = readInput("$pathPrefix23/day05.txt")
    val res1 = Fertilizer.part1(input)
    val res2 = Fertilizer.part2(input)


    println("part 1: $res1")
    println("part 2: $res2")

    checkResult(res1, 331445006)
    checkResult(res2, 6472060) // 331445006 high

}
