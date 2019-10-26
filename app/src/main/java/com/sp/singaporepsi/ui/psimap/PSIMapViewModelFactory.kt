package com.sp.singaporepsi.ui.psimap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sp.singaporepsi.data.PSIDataSource

class PSIMapViewModelFactory(val psiDataSource: PSIDataSource): ViewModelProvider.Factory{

    @Throws(IllegalArgumentException::class)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PSIMapViewModel::class.java)){
            return PSIMapViewModel(psiDataSource) as T
        }
        throw IllegalArgumentException("Unkown Viewmodel class $modelClass cannot initiate it.")
    }

}