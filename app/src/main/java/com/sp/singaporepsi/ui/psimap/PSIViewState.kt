package com.sp.singaporepsi.ui.psimap

/**
 * Created by chsc on 04.03.18.
 */
sealed class PSIViewState{

    object Loading: PSIViewState()

    object Success: PSIViewState()

    object NoDataAvailable: PSIViewState()

    data class Error(val errorMessage: String) : PSIViewState()

}