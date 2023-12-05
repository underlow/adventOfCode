package me.underlow.advent2023

import me.underlow.advent2022.checkResult
import me.underlow.advent2022.readInput

object Fertilizer {
    data class Range(val dest: Long, val source: Long, val count: Long)

    // fuck performance
    class RangeMap {
        private val ranges = mutableListOf<Range>()
        fun addRange(dest: Long, source: Long, count: Long) {
            ranges.add(Range(dest, source, count))
        }

        operator fun get(from: Long): Long {
            for (range in ranges) {
                if (from in range.source until range.source + range.count) {
                    return from - range.source + range.dest
                }
            }

            return from
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

    fun part2(list: List<String>): Int {
        val directions = parseInput(list)
        return 0
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

    checkResult(res1, 0)
    checkResult(res2, 0)

    println(res1)
    println(res2)
}
