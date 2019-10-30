package com.sp.singaporepsi.ui.psimap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sp.singaporepsi.model.ui.PollutionData
import com.sp.singaporepsi.testdata.*
import com.sp.singaporepsi.utils.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify


internal class PSIMapViewModelTest {

    val psiViewModelSuccess = PSIMapViewModel(SuccessDataSource)
    val psiViewModelNoDataAvailable = PSIMapViewModel(DataNotAvailableSource)
    val psiViewModelError = PSIMapViewModel(ErrorDataSource)

    @Mock
    lateinit var observerViewState: Observer<PSIViewState>
    lateinit var observerPSIPollution: Observer<PollutionData>
    lateinit var observerPMPollution: Observer<PollutionData>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        this.observerViewState = mock()
        this.observerPSIPollution = mock()
        this.observerPMPollution = mock()

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
        verify(observerViewState,times(2)).onChanged(any())
        Assert.assertEquals(PSIViewState.Success, psiViewModelSuccess.viewState.value)
    }

    @Test
    fun testSuccessfulPSILoading() {
        //when
        psiViewModelSuccess.loadPsiData()

        //then
        verify(observerPSIPollution,times(1)).onChanged(any())
        Assert.assertEquals(testPSIPollutionData(), psiViewModelSuccess.pollutionDataPsi24.value)

        verify(observerPMPollution,times(1)).onChanged(any())
        Assert.assertEquals(testPMPollutionData(), psiViewModelSuccess.pollutionDataPM25.value)
    }

    @Test
    fun testNoDataAvailablePSILoadingPSIInfo() {
        //when
        psiViewModelNoDataAvailable.loadPsiData()

        //then
        verify(observerPSIPollution,times(0)).onChanged(any())
        verify(observerPMPollution,times(0)).onChanged(any())
    }

    @Test
    fun testNoDataAvailablePSILoadingViewState() {
        //when
        psiViewModelNoDataAvailable.loadPsiData()

        //then
        verify(observerViewState,times(2)).onChanged(any())
        Assert.assertEquals(PSIViewState.NoDataAvailable, psiViewModelNoDataAvailable.viewState.value)
    }


    @Test
    fun testErrorPSILoadingPSIInfo() {
        //when
        psiViewModelError.loadPsiData()

        //then
        verify(observerPSIPollution,times(0)).onChanged(any())
        verify(observerPMPollution,times(0)).onChanged(any())
    }

    @Test
    fun testErrorPSILoadingViewState() {
        //when
        psiViewModelError.loadPsiData()

        //then
        verify(observerViewState,times(2)).onChanged(any())
        Assert.assertEquals(PSIViewState.Error(ErrorDataSource.errorMessage), psiViewModelError.viewState.value)
    }
}