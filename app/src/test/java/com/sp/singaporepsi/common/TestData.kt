package com.sp.singaporepsi.common

import com.sp.singaporepsi.model.*
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
