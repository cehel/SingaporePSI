package com.sp.singaporepsi.model

data class PSIReadings(
    val psi_twenty_four_hourly: PSIReading,
    val pm25_twenty_four_hourly: PSIReading
    )