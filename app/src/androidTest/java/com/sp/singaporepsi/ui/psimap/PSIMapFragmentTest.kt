package com.sp.singaporepsi.ui.psimap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.sp.singaporepsi.R
import com.sp.singaporepsi.SingleFragmentActivity
import com.sp.singaporepsi.model.ui.PMLevel
import com.sp.singaporepsi.model.ui.PollutionData
import com.sp.singaporepsi.model.ui.PollutionValue
import com.sp.singaporepsi.testdata.ViewModelUtil
import com.sp.singaporepsi.testdata.testPMPollutionData
import com.sp.singaporepsi.testdata.testPSI
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class PSIMapFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val psiMapFragment = PSIMapFragment.newInstance(PSIMapFragment.PollutionType.PM25_24H)

    private val mutablePollutionDataPM = MutableLiveData<PollutionData>()
    private val pollutionDataPM: LiveData<PollutionData> = mutablePollutionDataPM

    private lateinit var viewModel: PSIMapViewModel

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true)

        every { viewModel.pollutionDataFor(any())} returns pollutionDataPM

        psiMapFragment.psiMapViewModelFactory = ViewModelUtil.createFor(viewModel)

        activityRule.activity.setFragment(psiMapFragment)
    }

    @Test
    fun whenNormalpmPollutionIsDisplayedCorrectly() {
        //when
        mutablePollutionDataPM.postValue(testPMPollutionData())

        //then
        onView(withId(R.id.airQualityGrade)).check(matches(withText("NORMAL")))
        onView(withId(R.id.airQualityGrade)).check(matches(hasTextColor(R.color.grade_good)))
        onView(withId(R.id.healthAdvisoryGrade)).check(matches(withText("Normal Activity")))
    }

    @Test
    fun whenElevatedpmPollutionIsDisplayedCorrectly() {
        //when
        mutablePollutionDataPM.postValue(testPMPollutionDataWithNational( PMLevel.ELEVATED))

        //then
        onView(withId(R.id.airQualityGrade)).check(matches(withText("ELEVATED")))
        onView(withId(R.id.airQualityGrade)).check(matches(hasTextColor(R.color.grade_moderate)))
        onView(withId(R.id.healthAdvisoryGrade)).check(matches(withText("Less Outdoor Activity")))
    }

    @Test
    fun whenHighpmPollutionIsDisplayedCorrectly() {
        //when
        mutablePollutionDataPM.postValue(testPMPollutionDataWithNational( PMLevel.HIGH))

        //then
        onView(withId(R.id.airQualityGrade)).check(matches(withText("HIGH")))
        onView(withId(R.id.airQualityGrade)).check(matches(hasTextColor(R.color.grade_unhealthy)))
        onView(withId(R.id.healthAdvisoryGrade)).check(matches(withText("Minimize Outdoor")))
    }

    @Test
    fun whenVeryHighpmPollutionIsDisplayedCorrectly() {
        //when
        mutablePollutionDataPM.postValue(testPMPollutionDataWithNational( PMLevel.VERY_HIGH))

        //then
        onView(withId(R.id.airQualityGrade)).check(matches(withText("VERY HIGH")))
        onView(withId(R.id.airQualityGrade)).check(matches(hasTextColor(R.color.hazardous)))
        onView(withId(R.id.healthAdvisoryGrade)).check(matches(withText("Avoid Outdoor")))
    }


    fun testPMPollutionDataWithNational(pmLevel: PMLevel): PollutionData {
        val psiInfo = testPSI()
        val expectedMap = mapOf<String, PollutionValue>(
            "national" to PollutionValue(50.0, pmLevel),
            "north" to PollutionValue(10.0, PMLevel.NORMAL),
            "west" to PollutionValue(50.0, PMLevel.NORMAL),
            "south" to PollutionValue(100.0, PMLevel.ELEVATED),
            "east" to PollutionValue(200.0, PMLevel.HIGH),
            "central" to PollutionValue(255.0, PMLevel.VERY_HIGH)
        )
        return PollutionData(psiInfo.region_metadata, expectedMap, psiInfo.items[0].update_timestamp,
            pmLevel)
    }
}