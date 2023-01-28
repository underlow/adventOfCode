package me.underlow.advent2022

import kotlin.math.abs
import kotlin.math.max

object BeaconExclusionZoneInput {
    /**
     * Sonsor coordinates
     */
    private data class Sensor(val coord: Point)

    /**
     * Beacon coordinates
     */
    private data class Beacon(val coord: Point)

    /**
     * Parse that line "Sensor at x=2, y=18: closest beacon is at x=-2, y=15" to a pair of Sensor and Beacon
     */
    private fun parseInput(input: List<String>): List<Pair<Sensor, Beacon>> {
        return input.map {
            val split = it.split(":")
            val sString = split[0]
            val bString = split[1]
            val sensor = Sensor(
                Point(
                    sString.substringAfter("x=").substringBefore(",").trim().toInt(),
                    sString.substringAfter("y=").substringBefore(":").trim().toInt()
                )
            )
            val beacon = Beacon(
                Point(
                    bString.substringAfter("x=").substringBefore(",").trim().toInt(),
                    bString.substringAfter("y=").substringBefore(":").trim().toInt()
                )
            )
            sensor to beacon
        }
    }

    /**
     * Find manhattan distance between two points
     */
    private fun manhattanDistance(p1: Point, p2: Point): Int = abs(p1.x - p2.x) + abs(p1.y - p2.y)

    private data class BeaconSensor(val sensor: Sensor, val beacon: Beacon, val distance: Int)

    /**
     * Form BeaconSensor for each pair
     */
    private fun formBeaconSensors(pairs: List<Pair<Sensor, Beacon>>): List<BeaconSensor> =
        pairs.map { BeaconSensor(it.first, it.second, manhattanDistance(it.first.coord, it.second.coord)) }

    private data class Field(val upperLeft: Point, val lowerRight: Point)

    private fun findFieldSize(beaconSensors: List<BeaconSensor>): Field {
        val coordsX = beaconSensors.map { it.sensor.coord.x } + beaconSensors.map { it.beacon.coord.x }
        val minX = coordsX.minOf { it }
        val maxX = coordsX.maxOf { it }

        val coordsY = beaconSensors.map { it.sensor.coord.y } + beaconSensors.map { it.beacon.coord.y }
        val minY = coordsY.minOf { it }
        val maxY = coordsY.maxOf { it }
        println("Field is minX=$minX, maxX=$maxX, minY=$minY, maxY=$maxY")
        return Field(Point(minX, minY), Point(maxX, maxY))
    }

    private fun BeaconSensor.pointIsTooClose(p: Point): Boolean = manhattanDistance(sensor.coord, p) <= distance

    private fun findCountOfImpossibleBeacons(beaconSensors: List<BeaconSensor>, field: Field, y: Int): Int {
        var count = 0
        for (i in field.upperLeft.x - 5000000..field.lowerRight.x + 5000000) {
            if (beaconSensors.any { it.beacon.coord == Point(i, y) }) continue

            if (beaconSensors.any { it.pointIsTooClose(Point(i, y)) }) {
//            println("Point [$i, $y] is impossible")
                count++
            }
        }
        return count
    }

    private fun findCountOfImpossibleBeacons3(
        beaconSensors: List<BeaconSensor>,
        field: Field,
        xRange: IntRange,
        yRange: IntRange
    ): Point {
        fun checkPoint(i: Int, j: Int): Point? {
            return if (beaconSensors.any { it.pointIsTooClose(Point(i, j)) }) {
                null
            } else {
                Point(i, j)
            }
        }

        for (i in yRange) {
            val range = calculateRangeForLine(beaconSensors, i)
            val start = System.currentTimeMillis()

            if (range.size == 1 && range[0].first <= yRange.first && range[0].last >= yRange.last) {
                continue
            }
            println("Searching in $i line")
            for (j in xRange) {
                if (!range.any { j in it }) {
                    val p = checkPoint(j, i)
                    if (p != null) {
                        return p
                    }
                }
            }

            if (i % 100 == 0) {
                val end = System.currentTimeMillis()
                val time = end - start
                val itemsLeft = xRange.last - i
                println("Processing $i row, one item in $time, ETA: ${time * itemsLeft / 1000} seconds")
            }

        }
        error("No point found")
    }


    private fun calculateRangeForLine(beaconSensors: List<BeaconSensor>, y: Int): List<IntRange> {
        val ranges = beaconSensors.map { sensor ->
            if (y < sensor.sensor.coord.y - sensor.distance || y > sensor.sensor.coord.y + sensor.distance) {
                return@map IntRange.EMPTY
            } else {
                val dx = sensor.distance - abs(sensor.sensor.coord.y - y)
                val nx = sensor.sensor.coord.x - abs(dx)
                val ny = sensor.sensor.coord.x + abs(dx)
                return@map nx..ny
            }
        }
        val sortedRanges = ranges.filter { it != IntRange.EMPTY }.sortedBy { it.first }
        var i = 0
        var j = i + 1
        val combinedRangesSet = mutableListOf<IntRange>()
        while (i < sortedRanges.size) {
            var last = sortedRanges[j - 1].last
            while (j < sortedRanges.size && sortedRanges[j].first <= last) {
                last = max(last, sortedRanges[j].last)
                j++
            }
            combinedRangesSet.add(sortedRanges[i].first..last)
            i = j + 1
            j = i + 1
        }


        return combinedRangesSet.toList()
    }

    fun solution1(lines: List<String>, yLine: Int): Int {
        val beaconSensors = formBeaconSensors(parseInput(lines))
        val field = findFieldSize(beaconSensors)

        return findCountOfImpossibleBeacons(beaconSensors, field, yLine) //- beaconCountAtyLine
    }

    fun solution2(lines: List<String>, xRange: IntRange, yRange: IntRange): Long {
        val beaconSensors = formBeaconSensors(parseInput(lines))
        val field = findFieldSize(beaconSensors)

        val beacon = findCountOfImpossibleBeacons3(beaconSensors, field, xRange, yRange)
        return beacon.x.toLong() * 4000000 + beacon.y
    }
}

fun main() {
    val input = readInput("${pathPrefix}/day15.txt")

    val s1 = BeaconExclusionZoneInput.solution1(input, 2000000)
    val s2 = BeaconExclusionZoneInput.solution2(input, 0..4000000, 0..4000000)

    println(s1)
    println(s2)

    checkResult(s1, 5367037)
    checkResult(s2, 11914583249288L)
}
