package com.sp.singaporepsi.data.remote

import com.sp.singaporepsi.data.PSIDataSource
import com.sp.singaporepsi.model.PSIInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PSIDataSourceRemote: PSIDataSource {

    override fun fetchPSIData(callback: PSIDataSource.PSIInfoCallback) {
        APIServices.psiAPI.getPsiInfo().enqueue(object: Callback<PSIInfo> {

            override fun onFailure(call: Call<PSIInfo>, t: Throwable) {
                callback.onDataNotAvailable()
            }

            override fun onResponse(call: Call<PSIInfo>, response: Response<PSIInfo>) {
                if (response.isSuccessful && response.body() != null ) {
                    val psiInfo = response.body()
                    psiInfo?.let { callback.onPSIInfoLoaded(psiInfo) }
                } else if (response.body() == null) {
                    callback.onError("There was an error parsing the PSIData")
                } else {
                    callback.onError("There was an error fetching the data")
                }
            }

        })
    }

}