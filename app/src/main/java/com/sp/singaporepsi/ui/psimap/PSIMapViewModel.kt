package com.sp.singaporepsi.ui.psimap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sp.singaporepsi.data.PSIDataSource
import com.sp.singaporepsi.model.PSIInfo
import com.sp.singaporepsi.model.ui.PollutionData

class PSIMapViewModel(val psiDataSource: PSIDataSource) : ViewModel() {

    private val mutablePsiInfo: MutableLiveData<PSIInfo> = MutableLiveData<PSIInfo>()
    val psiInfo: LiveData<PSIInfo> = mutablePsiInfo

    private val mutablePollutionDataPsi = MutableLiveData<PollutionData>()
    val pollutionDataPsi24: LiveData<PollutionData> = mutablePollutionDataPsi

    private val mutablePollutionDataPM25 = MutableLiveData<PollutionData>()
    val pollutionDataPM25: LiveData<PollutionData> = mutablePollutionDataPM25

    private val mutableViewState: MutableLiveData<PSIViewState> = MutableLiveData<PSIViewState>()
    val viewState : LiveData<PSIViewState> = mutableViewState


    fun pollutionDataFor(pollutionType: String): LiveData<PollutionData> {
        return when (pollutionType) {
            PSIMapFragment.PollutionType.PM25_24H.name -> {
                pollutionDataPM25
            }
            PSIMapFragment.PollutionType.PSI_24H.name -> {
                pollutionDataPsi24
            }
            else -> throw NoSuchFieldException("no livedata for pollutiontype: ${pollutionType}")
        }
    }


    fun loadPsiData() {
        mutableViewState.postValue(PSIViewState.Loading)

        psiDataSource.fetchPSIData(object: PSIDataSource.PSIInfoCallback {
            override fun onPSIInfoLoaded(psiInfo: PSIInfo) {
                mutablePsiInfo.postValue(psiInfo)
                mutablePollutionDataPsi.postValue(
                    PollutionData(
                        psiInfo.region_metadata,
                        psiInfo.items[0].readings.psi_twenty_four_hourly,
                        psiInfo.items[0].update_timestamp))
                mutablePollutionDataPM25.postValue(
                    PollutionData(
                        psiInfo.region_metadata,
                        psiInfo.items[0].readings.pm25_twenty_four_hourly,
                        psiInfo.items[0].update_timestamp))
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
