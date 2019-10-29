package com.sp.singaporepsi.ui.psimap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sp.singaporepsi.R
import com.sp.singaporepsi.data.remote.APIServices
import com.sp.singaporepsi.model.PSIReading
import com.sp.singaporepsi.model.RegionMetadata
import com.sp.singaporepsi.model.ui.PollutionData
import kotlinx.android.synthetic.main.fragment_psimap.*

val PollutionTypeKey = "POLLUTION_TYPE"

class PSIMapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(pollutionType: PollutionType): PSIMapFragment {
            val fragment = PSIMapFragment()
            val bundle = Bundle()
            bundle.putString(PollutionTypeKey,pollutionType.name)
            fragment.arguments = bundle
            return fragment
        }
    }

    enum class PollutionType{PSI_24H,PM25_24H}

    private lateinit var viewModel: PSIMapViewModel
    private var mMap: GoogleMap? = null
    private var mapFragment: SupportMapFragment? = null
    private var _pollutionData: PollutionData? = null
    private var _pollutionType: PollutionType? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragment = inflater.inflate(R.layout.fragment_psimap, container, false)
        viewModel = ViewModelProviders.of(activity!!,PSIMapViewModelFactory(APIServices.psiDataSourceRemote)).get(PSIMapViewModel::class.java)
        val pollutionType = arguments?.getString(PollutionTypeKey)
        pollutionType?.let{
            viewModel.pollutionDataFor(pollutionType).observe(viewLifecycleOwner, Observer { pollutionData: PollutionData? ->
                pollutionData?.let {
                    _pollutionData = pollutionData
                    //psiMapTV.text = "Twenty four hourly: ${pollutionData.psiReading}"
                    updateMap()
                }
            })
        }

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance()
            mapFragment?.getMapAsync(this)
        }
        mapFragment?.let{childFragmentManager.beginTransaction().replace(R.id.mapContainer, it).commit()}

        return fragment
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.let {
            it.uiSettings.isZoomControlsEnabled = false
            it.uiSettings.isZoomGesturesEnabled = false
            it.uiSettings.isScrollGesturesEnabled = false
        }
        updateMap()
    }

    private fun updateMap(){
        mMap?.let {mMap ->
            _pollutionData?.regionMetadata?.forEach{ regionMeta ->
                //TODO: mMap.addMarker(MarkerOptions().position(regionMeta.latLng()).title(getReading(regionMeta.name)))
                if (regionMeta.name == "central") {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(regionMeta.latLng()))
                }
            }
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.5f ) );
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
