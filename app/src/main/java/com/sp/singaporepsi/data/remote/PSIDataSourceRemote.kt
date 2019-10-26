package com.sp.singaporepsi.data.remote

import com.sp.singaporepsi.data.PSIDataSource
import com.sp.singaporepsi.model.PSIInfo
import retrofit2.Callback

class PSIDataSourceRemote: PSIDataSource {

    override fun fetchPSIData(callback: Callback<PSIInfo>) {
        APIServices.psiAPI.getPsiInfo().enqueue(callback)
    }

}