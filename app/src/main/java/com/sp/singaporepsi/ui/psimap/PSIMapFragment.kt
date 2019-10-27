package com.sp.singaporepsi.ui.psimap

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.sp.singaporepsi.R
import com.sp.singaporepsi.data.remote.APIServices


class PSIMapFragment : Fragment() {
    companion object {
        fun newInstance() = PSITabBarFragment()
    }

    private lateinit var viewModel: PSIMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_psimap, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,PSIMapViewModelFactory(APIServices.psiDataSourceRemote)).get(PSIMapViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
