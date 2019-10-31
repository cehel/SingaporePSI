package com.sp.singaporepsi.model.ui

import com.sp.singaporepsi.model.RegionMetadata
import java.util.*


data class PollutionData (
    val regionMetadata: List<RegionMetadata>,
    val pollutionValues: Map<String, PollutionValue>,
    val updateTime: Date,
    val pollutionLevel: PollutionLevel
)

interface PollutionLevel{
    val title: String
    val range: IntRange}

enum class PMLevel(override val title: String, override val range: IntRange) : PollutionLevel{
    NORMAL("Normal", 0..55),
    ELEVATED("Elevated", 56..150),
    HIGH("High",151..250),
    VERY_HIGH("Very High",251..Int.MAX_VALUE)
}

enum class PSILevel(override val title: String, override val range: IntRange) : PollutionLevel{
    GOOD("Good", 0..50),
    MODERATE("Moderate", 51..100),
    UNHEALTHY("Unhealthy",101..200),
    VERYUNHEALTHY("Very unhealthy",201..300),
    HAZARDOUS("Hazardous",301..Int.MAX_VALUE)
}