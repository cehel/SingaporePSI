package com.sp.singaporepsi.testdata

import com.sp.singaporepsi.model.*
import com.sp.singaporepsi.model.ui.PMLevel
import com.sp.singaporepsi.model.ui.PSILevel
import com.sp.singaporepsi.model.ui.PollutionData
import com.sp.singaporepsi.model.ui.PollutionValue
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


fun testPSI(): PSIInfo {
    val date = Date(LocalDate.of(2019,4,5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    val pm25Reading = PSIReading(national = 50.0, north = 10.0, west = 50.0, south = 100.0, east = 200.0, central = 255.0)
    val psiReading = PSIReading(national = 50.0, north = 10.0, west = 50.0, south = 100.0, east = 200.0, central = 300.0)
    val metaData = listOf(
        RegionMetadata(name = "national", label_location = PSICoords( 1.35735,  103.7)),
        RegionMetadata(name = "north", label_location = PSICoords( 1.35735,  103.7)),
        RegionMetadata(name = "west", label_location = PSICoords( 1.35735,  103.7)),
        RegionMetadata(name = "south", label_location = PSICoords( 1.35735,  103.7)),
        RegionMetadata(name = "east", label_location = PSICoords( 1.35735,  103.7)),
        RegionMetadata(name = "central", label_location = PSICoords( 1.35735,  103.7))
    )
    val apiInfo = APIInfo(status = "healthy")

    val items = listOf<PSIItem>(PSIItem(date, date, PSIReadings(psiReading, pm25Reading)))

    return PSIInfo(apiInfo,metaData,items)
}

fun testPSIPollutionData(): PollutionData {
    val psiInfo = testPSI()
    val expectedMap = mapOf<String, PollutionValue>(
        "national" to PollutionValue(50.0, PSILevel.GOOD),
        "north" to PollutionValue(10.0, PSILevel.GOOD),
        "west" to PollutionValue(50.0, PSILevel.GOOD),
        "south" to PollutionValue(100.0, PSILevel.MODERATE),
        "east" to PollutionValue(200.0, PSILevel.UNHEALTHY),
        "central" to PollutionValue(300.0, PSILevel.VERYUNHEALTHY)
    )
    return PollutionData(psiInfo.region_metadata, expectedMap, psiInfo.items[0].update_timestamp,
        PSILevel.GOOD)
}

fun testPMPollutionData(): PollutionData {
    val psiInfo = testPSI()
    val expectedMap = mapOf<String, PollutionValue>(
        "national" to PollutionValue(50.0, PMLevel.NORMAL),
        "north" to PollutionValue(10.0, PMLevel.NORMAL),
        "west" to PollutionValue(50.0, PMLevel.NORMAL),
        "south" to PollutionValue(100.0, PMLevel.ELEVATED),
        "east" to PollutionValue(200.0, PMLevel.HIGH),
        "central" to PollutionValue(255.0, PMLevel.VERY_HIGH)
    )
    return PollutionData(psiInfo.region_metadata, expectedMap, psiInfo.items[0].update_timestamp,
        PMLevel.NORMAL)
}
