package com.sp.singaporepsi.ui.psimap

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.sp.singaporepsi.R
import com.sp.singaporepsi.SingleFragmentActivity
import com.sp.singaporepsi.testdata.ViewModelUtil
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@MediumTest
@RunWith(AndroidJUnit4::class)
internal class PSITabBarFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val tabBarFragment = PSITabBarFragment()

    private val mutabbleViewStateLiveData = MutableLiveData<PSIViewState>()
    private val viewStatLiveData: LiveData<PSIViewState> = mutabbleViewStateLiveData

    private lateinit var viewModel: PSIMapViewModel

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true)

        every { viewModel.viewState} returns viewStatLiveData

        tabBarFragment.psiMapViewModelFactory = ViewModelUtil.createFor(viewModel)

        activityRule.activity.setFragment(tabBarFragment)
    }

    @Test
    fun whenViewStateIsLoading() {
        //Given
        mutabbleViewStateLiveData.postValue(PSIViewState.Loading)

        //then
        onView(allOf(withId(R.id.loadingView))).check(matches(isDisplayed()))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenViewStateIsError() {
        //Given
        mutabbleViewStateLiveData.postValue(PSIViewState.Error("There was an error"))

        //then
        onView(allOf(withId(R.id.errorView))).check(matches(isDisplayed()))
        onView(withId(R.id.loadingView)).check(matches(not(isDisplayed())))
    }
}