package com.sp.singaporepsi.ui.psimap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sp.singaporepsi.data.PSIDataSource
import com.sp.singaporepsi.model.PSIInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PSIMapViewModel(val psiDataSource: PSIDataSource) : ViewModel() {

    private val mutablePsiInfo: MutableLiveData<PSIInfo> = MutableLiveData<PSIInfo>()
    val psiInfo: LiveData<PSIInfo> = mutablePsiInfo


    private val mutableViewState: MutableLiveData<PSIViewState> = MutableLiveData<PSIViewState>()
    val viewState : LiveData<PSIViewState> = mutableViewState


    fun loadPsiData() {
        mutableViewState.postValue(PSIViewState.Loading)

        psiDataSource.fetchPSIData(object: PSIDataSource.PSIInfoCallback {
            override fun onPSIInfoLoaded(psiInfo: PSIInfo) {
                mutablePsiInfo.postValue(psiInfo)
                mutableViewState.postValue(PSIViewState.Success)
            }

            override fun onDataNotAvailable() {
                mutableViewState.postValue(PSIViewState.NoDataAvailable)
            }

            override fun onError(message: String) {
                mutableViewState.postValue(PSIViewState.Error(message))
            }

        })
    }

}
