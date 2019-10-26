package com.sp.singaporepsi.ui.psimap

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sp.singaporepsi.R

class PSIMapFragment : Fragment() {

    companion object {
        fun newInstance() = PSIMapFragment()
    }

    private lateinit var viewModel: PSIMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.psimap_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PSIMapViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
