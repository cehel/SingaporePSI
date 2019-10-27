package com.sp.singaporepsi.ui.psimap

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sp.singaporepsi.R
import com.sp.singaporepsi.data.remote.APIServices

class PSITabBarFragment : Fragment() {

    companion object {
        fun newInstance() = PSITabBarFragment()
    }

    private lateinit var viewModel: PSIMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.psitabbar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,PSIMapViewModelFactory(APIServices.psiDataSourceRemote)).get(PSIMapViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
