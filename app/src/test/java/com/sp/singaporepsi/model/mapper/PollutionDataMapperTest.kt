package com.sp.singaporepsi.model.mapper

import com.sp.singaporepsi.common.testPSI
import com.sp.singaporepsi.model.ui.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

internal class PollutionDataMapperTest {
    val testSubject = PollutionDataMapper
    val psiInfo = testPSI()

    @Test
    fun createPSIPollutionData() {
        //when
        val pmPollutionData = testSubject.createPSIPollutionData(psiInfo)

        //then
        val expectedMap = mapOf<String, PollutionValue>(
            "national" to PollutionValue(50.0, PSILevel.GOOD),
            "north" to PollutionValue(10.0, PSILevel.GOOD),
            "west" to PollutionValue(50.0, PSILevel.GOOD),
            "south" to PollutionValue(100.0, PSILevel.MODERATE),
            "east" to PollutionValue(200.0, PSILevel.UNHEALTHY),
            "central" to PollutionValue(300.0, PSILevel.VERYUNHEALTHY)
            )
        val expected = PollutionData(psiInfo.region_metadata, expectedMap, psiInfo.items[0].update_timestamp,PSILevel.GOOD)

        assertEquals(expected.healthAdvisory, pmPollutionData.healthAdvisory)
        assertEquals(expected.pollutionLevel, pmPollutionData.pollutionLevel)
        assertEquals(expected.pollutionValues, pmPollutionData.pollutionValues)
    }

    @Test
    fun createPMPollutionData() {
        //when
        val pmPollutionData = testSubject.createPMPollutionData(psiInfo)

        //then
        val expectedMap = mapOf<String, PollutionValue>(
            "national" to PollutionValue(50.0, PMLevel.NORMAL),
            "north" to PollutionValue(10.0, PMLevel.NORMAL),
            "west" to PollutionValue(50.0, PMLevel.NORMAL),
            "south" to PollutionValue(100.0, PMLevel.ELEVATED),
            "east" to PollutionValue(200.0, PMLevel.HIGH),
            "central" to PollutionValue(255.0, PMLevel.VERY_HIGH)
        )
        val expected = PollutionData(psiInfo.region_metadata, expectedMap, psiInfo.items[0].update_timestamp,PMLevel.NORMAL)

        assertEquals(expected.healthAdvisory, pmPollutionData.healthAdvisory)
        assertEquals(expected.pollutionLevel, pmPollutionData.pollutionLevel)
        assertEquals(expected.pollutionValues, pmPollutionData.pollutionValues)
    }

    @ParameterizedTest
    @CsvSource(
        "1, GOOD",
        "50, GOOD",
        "51, MODERATE",
        "100, MODERATE",
        "101, UNHEALTHY",
        "250, VERYUNHEALTHY",
        "301, HAZARDOUS"

    )
    fun mapPSIToPollutionLevel(pm25Value: Int, expected: String) {
        //when
        val pollutionLevel = testSubject.mapPSIToPollutionLevel(pm25Value)

        //then
        assertEquals(PSILevel.valueOf(expected), pollutionLevel)
    }

    @ParameterizedTest
    @CsvSource(
        "1, NORMAL",
        "56, ELEVATED",
        "150, ELEVATED",
        "151, HIGH",
        "250, HIGH",
        "251, VERY_HIGH",
        "400, VERY_HIGH"
    )
    fun mapPM25ToPollutionLevel(psiValue: Int, expected: String) {
        //when
        val pollutionLevel = testSubject.mapPM25ToPollutionLevel(psiValue)

        //then
        assertEquals(PMLevel.valueOf(expected), pollutionLevel)
    }

}