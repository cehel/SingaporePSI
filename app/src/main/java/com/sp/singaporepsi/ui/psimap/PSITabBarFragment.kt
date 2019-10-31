package com.sp.singaporepsi.ui.psimap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.sp.singaporepsi.R
import com.sp.singaporepsi.AirQualityAppServices
import com.sp.singaporepsi.ui.adapter.PSIMapPageAdapter
import kotlinx.android.synthetic.main.psitabbar_fragment.*


class PSITabBarFragment : Fragment() {

    var psiMapViewModelFactory: ViewModelProvider.Factory = PSIMapViewModelFactory(AirQualityAppServices.psiDataSourceRemote)

    companion object {
        fun newInstance() = PSITabBarFragment()
    }

    private lateinit var viewModel: PSIMapViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(com.sp.singaporepsi.R.layout.psitabbar_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, psiMapViewModelFactory).get(PSIMapViewModel::class.java)
        viewModel.loadPsiData()
        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState -> handleViewState(viewState) })
        setupViewPager(viewpager)
        tabLayout.setupWithViewPager(viewpager)
    }

    private fun handleViewState(viewState: PSIViewState?) {
        when (viewState) {
            PSIViewState.NoDataAvailable -> {
                airQualityLoadingView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                errorView.setText(resources.getString(R.string.no_data_available))
            }
            is PSIViewState.Error -> {
                airQualityLoadingView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                errorView.setText(viewState.errorMessage)
            }
            PSIViewState.Success -> {
                airQualityLoadingView.visibility = View.GONE
                errorView.visibility = View.GONE
            }
            PSIViewState.Loading -> {
                airQualityLoadingView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
            }
        }
    }

    fun setupViewPager(viewPager: ViewPager){
        val adapter = PSIMapPageAdapter(childFragmentManager)
        adapter.addFragment(PSIMapFragment.newInstance(PSIMapFragment.PollutionType.PSI_24H),resources.getString(R.string.tab_title_24hpsi))
        adapter.addFragment(PSIMapFragment.newInstance(PSIMapFragment.PollutionType.PM25_24H),resources.getString(R.string.tab_title_24h25PM))
        viewPager.adapter = adapter
    }

}
