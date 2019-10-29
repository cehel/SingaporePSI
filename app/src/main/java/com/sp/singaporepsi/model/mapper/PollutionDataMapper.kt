package com.sp.singaporepsi.model.mapper

import com.sp.singaporepsi.model.PSIInfo
import com.sp.singaporepsi.model.PSIReading
import com.sp.singaporepsi.model.ui.*

object PollutionDataMapper {

    fun createPSIPollutionData(psiInfo: PSIInfo): PollutionData {

        val reading = psiInfo.items[0].readings.psi_twenty_four_hourly

        val nationalIndex = reading.national.toInt()

        val healthAdvisory: PollutionLevel = mapPSIToPollutionLevel(nationalIndex)

        val pollutionValueMap = psiInfo.region_metadata.map { it.name to findPollutionValue(it.name, reading, ::mapPSIToPollutionLevel) }.toMap()

        return PollutionData(
            psiInfo.region_metadata,
            pollutionValueMap,
            psiInfo.items[0].update_timestamp,
            healthAdvisory)
    }

    fun createPMPollutionData(psiInfo: PSIInfo): PollutionData {

        val reading = psiInfo.items[0].readings.pm25_twenty_four_hourly

        val healthAdvisory: PollutionLevel = mapPM25ToPollutionLevel(reading.national.toInt())

        val pollutionValueMap = psiInfo.region_metadata.map { it.name to findPollutionValue(it.name, reading, ::mapPM25ToPollutionLevel) }.toMap()

        return PollutionData(
            psiInfo.region_metadata,
            pollutionValueMap,
            psiInfo.items[0].update_timestamp,
            healthAdvisory)
    }

    private fun findPollutionValue(
        name: String,
        reading: PSIReading,
        mapPollutionLevel: (Int) -> PollutionLevel
    ): PollutionValue {
        return when(name) {
            "north" -> PollutionValue(reading.north, mapPollutionLevel(reading.north.toInt()))
            "east" -> PollutionValue(reading.east, mapPollutionLevel(reading.east.toInt()))
            "central" -> PollutionValue(reading.central, mapPollutionLevel(reading.central.toInt()))
            "south" -> PollutionValue(reading.south, mapPollutionLevel(reading.south.toInt()))
            "west" -> PollutionValue(reading.west, mapPollutionLevel(reading.west.toInt()))
            "national" -> PollutionValue(reading.national, mapPollutionLevel(reading.national.toInt()))
            else -> throw NoSuchFieldException("There is no PollutionValue mapping for $name")
        }
    }

    fun mapPSIToPollutionLevel(psiIndex: Int): PSILevel {
        val healthAdvisory = when (psiIndex) {
            in PSILevel.GOOD.range -> PSILevel.GOOD
            in PSILevel.MODERATE.range -> PSILevel.MODERATE
            in PSILevel.UNHEALTHY.range -> PSILevel.UNHEALTHY
            in PSILevel.VERYUNHEALTHY.range -> PSILevel.VERYUNHEALTHY
            in PSILevel.HAZARDOUS.range -> PSILevel.HAZARDOUS
            else -> PSILevel.HAZARDOUS
        }
        return healthAdvisory
    }

    fun mapPM25ToPollutionLevel(pm25: Int): PMLevel {
        return when (pm25) {
            in PMLevel.NORMAL.range -> PMLevel.NORMAL
            in PMLevel.ELEVATED.range -> PMLevel.ELEVATED
            in PMLevel.HIGH.range -> PMLevel.HIGH
            in PMLevel.VERY_HIGH.range -> PMLevel.VERY_HIGH
            else -> PMLevel.VERY_HIGH
        }
    }

}