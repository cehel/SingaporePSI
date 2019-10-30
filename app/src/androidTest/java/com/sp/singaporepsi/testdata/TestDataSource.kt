package com.sp.singaporepsi.testdata

import com.sp.singaporepsi.data.PSIDataSource
import com.sp.singaporepsi.model.APIInfo
import com.sp.singaporepsi.model.PSIInfo

object SuccessDataSource: PSIDataSource {
    val successPSITest = testPSI()

    override fun fetchPSIData(callback: PSIDataSource.PSIInfoCallback) {
        callback.onPSIInfoLoaded(successPSITest)
    }
}

object ErrorDataSource: PSIDataSource {
    val errorMessage = "There was an error in the server"

    override fun fetchPSIData(callback: PSIDataSource.PSIInfoCallback) {
        callback.onError(errorMessage)
    }
}

object DataNotAvailableSource: PSIDataSource {
    override fun fetchPSIData(callback: PSIDataSource.PSIInfoCallback) {
        callback.onDataNotAvailable()
    }
}