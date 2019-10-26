package com.sp.singaporepsi.data

import com.sp.singaporepsi.model.PSIInfo
import retrofit2.Callback

interface PSIDataSource {
    fun fetchPSIData(callback: Callback<PSIInfo>)
}