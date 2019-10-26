package com.sp.singaporepsi.model

import java.time.LocalDateTime
import java.util.*

data class PSIItem(
    val update_timestamp: Date,
    val timestamp: Date,
    val readings: PSIReadings
    )