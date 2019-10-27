package com.sp.singaporepsi.data

import com.sp.singaporepsi.model.PSIInfo
import retrofit2.Callback

interface PSIDataSource {
    fun fetchPSIData(callback: PSIInfoCallback)

    interface PSIInfoCallback {

        fun onPSIInfoLoaded(psiInfo: PSIInfo)

        fun onDataNotAvailable()

        fun onError(message: String)
    }
}