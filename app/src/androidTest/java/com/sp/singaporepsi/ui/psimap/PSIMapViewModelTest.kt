package com.sp.singaporepsi.ui.psimap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sp.singaporepsi.model.ui.PollutionData
import com.sp.singaporepsi.testdata.*
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


internal class PSIMapViewModelTest {

    val psiViewModelSuccess = PSIMapViewModel(SuccessDataSource)
    val psiViewModelNoDataAvailable = PSIMapViewModel(DataNotAvailableSource)
    val psiViewModelError = PSIMapViewModel(ErrorDataSource)

    lateinit var observerViewState: Observer<PSIViewState>
    lateinit var observerPSIPollution: Observer<PollutionData>
    lateinit var observerPMPollution: Observer<PollutionData>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        this.observerViewState = mockk(relaxed = true)
        this.observerPSIPollution = mockk(relaxed = true)
        this.observerPMPollution = mockk(relaxed = true)

        psiViewModelSuccess.viewState.observeForever(observerViewState)
        psiViewModelSuccess.pollutionDataPsi24.observeForever(observerPSIPollution)
        psiViewModelSuccess.pollutionDataPM25.observeForever(observerPMPollution)

        psiViewModelNoDataAvailable.viewState.observeForever(observerViewState)
        psiViewModelError.viewState.observeForever(observerViewState)

    }

    @Test
    fun testSuccessfulPSILoadingViewState() {
        //when
        psiViewModelSuccess.loadPsiData()

        //then
        verify(exactly = 1){observerViewState.onChanged(PSIViewState.Loading)}
        verify(exactly = 1){observerViewState.onChanged(PSIViewState.Success)}
        Assert.assertEquals(PSIViewState.Success, psiViewModelSuccess.viewState.value)
    }

    @Test
    fun testSuccessfulPSILoading() {
        //when
        psiViewModelSuccess.loadPsiData()

        //then
        verify(exactly = 1){observerPSIPollution.onChanged(any())}
        Assert.assertEquals(testPSIPollutionData(), psiViewModelSuccess.pollutionDataPsi24.value)

        verify(exactly = 1){observerPMPollution.onChanged(any())}
        Assert.assertEquals(testPMPollutionData(), psiViewModelSuccess.pollutionDataPM25.value)
    }

    @Test
    fun testNoDataAvailablePSILoadingPSIInfo() {
        //when
        psiViewModelNoDataAvailable.loadPsiData()

        //then
        verify(exactly = 0){observerPSIPollution.onChanged(any())}
        verify(exactly = 0){observerPMPollution.onChanged(any())}
    }

    @Test
    fun testNoDataAvailablePSILoadingViewState() {
        //when
        psiViewModelNoDataAvailable.loadPsiData()

        //then
        verify(exactly = 2){observerViewState.onChanged(any())}
        Assert.assertEquals(PSIViewState.NoDataAvailable, psiViewModelNoDataAvailable.viewState.value)
    }


    @Test
    fun testErrorPSILoadingPSIInfo() {
        //when
        psiViewModelError.loadPsiData()

        //then
        verify(exactly = 0){observerPSIPollution.onChanged(any())}
        verify(exactly = 0){observerPMPollution.onChanged(any())}
    }

    @Test
    fun testErrorPSILoadingViewState() {
        //when
        psiViewModelError.loadPsiData()

        //then
        verify(exactly = 2){observerViewState.onChanged(any())}
        Assert.assertEquals(PSIViewState.Error(ErrorDataSource.errorMessage), psiViewModelError.viewState.value)
    }
}