package com.sp.singaporepsi.ui.psimap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.whenever
import com.sp.singaporepsi.MainActivity
import com.sp.singaporepsi.R
import com.sp.singaporepsi.testdata.ViewModelUtil
import com.sp.singaporepsi.utils.mock
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@MediumTest
@RunWith(AndroidJUnit4::class)
internal class PSITabBarFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val tabBarFragment = PSITabBarFragment()

    private val mutabbleViewStateLiveData = MutableLiveData<PSIViewState>()
    private val viewStatLiveData: LiveData<PSIViewState> = mutabbleViewStateLiveData

    @Mock
    private lateinit var viewModel: PSIMapViewModel

    @Before
    fun setUp() {
        viewModel = mock()

        whenever(viewModel.viewState).thenReturn(viewStatLiveData)

        tabBarFragment.psiMapViewModelFactory = ViewModelUtil.createFor(viewModel)
    }

    @Test
    fun whenViewStateIsLoading() {
        //Given
        mutabbleViewStateLiveData.postValue(PSIViewState.Loading)

        //When
        val beginTransaction = activityRule.activity.supportFragmentManager.beginTransaction()
        beginTransaction.apply {
            add(R.id.contentFrame, tabBarFragment)
                .commit()
        }

        //then
        onView(withId(R.id.toplayout)).check(matches(isDisplayed()))
        //onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))


    }
}