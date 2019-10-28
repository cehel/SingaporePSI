package com.sp.singaporepsi.model.ui

import com.sp.singaporepsi.model.PSIReading
import com.sp.singaporepsi.model.RegionMetadata
import java.util.*

data class PollutionData (
    val regionMetadata: List<RegionMetadata>,
    val psiReading: PSIReading,
    val updateTime: Date
)