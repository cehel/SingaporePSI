package com.sp.singaporepsi.model

data class PSIReading(
    val national: Double,
    val north: Double,
    val south: Double,
    val east: Double,
    val west: Double,
    val central: Double
)