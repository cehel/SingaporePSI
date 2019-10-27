package com.sp.singaporepsi.ui.psimap

import androidx.lifecycle.Observer
import org.junit.Rule
import org.junit.rules.TestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sp.singaporepsi.model.PSIInfo
import com.sp.singaporepsi.testdata.DataNotAvailableSource
import com.sp.singaporepsi.testdata.ErrorDataSource
import com.sp.singaporepsi.testdata.SuccessDataSource
import com.sp.singaporepsi.testdata.SuccessDataSource.successPSITest
import com.sp.singaporepsi.utils.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*


internal class PSIMapViewModelTest {

    val psiViewModelSuccess = PSIMapViewModel(SuccessDataSource)
    val psiViewModelNoDataAvailable = PSIMapViewModel(DataNotAvailableSource)
    val psiViewModelError = PSIMapViewModel(ErrorDataSource)

    @Mock
    lateinit var observerViewState: Observer<PSIViewState>
    lateinit var observerPsiInfo: Observer<PSIInfo>

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        this.observerViewState = mock()
        this.observerPsiInfo = mock()

        psiViewModelSuccess.viewState.observeForever(observerViewState)
        psiViewModelNoDataAvailable.viewState.observeForever(observerViewState)
        psiViewModelError.viewState.observeForever(observerViewState)

        psiViewModelSuccess.psiInfo.observeForever(observerPsiInfo)
        psiViewModelError.psiInfo.observeForever(observerPsiInfo)
        psiViewModelNoDataAvailable.psiInfo.observeForever(observerPsiInfo)
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
    fun testSuccessfulPSILoadingPSIInfo() {
        //when
        psiViewModelSuccess.loadPsiData()

        //then
        verify(observerPsiInfo,times(1)).onChanged(any())
        Assert.assertEquals(successPSITest, psiViewModelSuccess.psiInfo.value)
    }

    @Test
    fun testNoDataAvailablePSILoadingPSIInfo() {
        //when
        psiViewModelNoDataAvailable.loadPsiData()

        //then
        verify(observerPsiInfo,times(0)).onChanged(any())
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
        verify(observerPsiInfo,times(0)).onChanged(any())
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