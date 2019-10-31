package com.sp.singaporepsi.ui.psimap

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sp.singaporepsi.AirQualityAppServices
import com.sp.singaporepsi.R
import com.sp.singaporepsi.model.ui.PMLevel
import com.sp.singaporepsi.model.ui.PSILevel
import com.sp.singaporepsi.model.ui.PollutionData
import com.sp.singaporepsi.model.ui.PollutionLevel
import com.sp.singaporepsi.ui.psimap.CustomMarkerView.Companion.getMarkerIcon
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragment = inflater.inflate(R.layout.fragment_psimap, container, false)
        viewModel = ViewModelProviders.of(activity!!,PSIMapViewModelFactory(AirQualityAppServices.psiDataSourceRemote)).get(PSIMapViewModel::class.java)
        val pollutionType = arguments?.getString(PollutionTypeKey)
        pollutionType?.let{
            viewModel.pollutionDataFor(pollutionType).observe(viewLifecycleOwner, Observer { pollutionData: PollutionData? ->
                pollutionData?.let {
                    updateUIWith(pollutionData)
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

    private fun updateUIWith(pollutionData: PollutionData) {
        _pollutionData = pollutionData
        airQualityGrade.text = pollutionData.pollutionLevel.title.toUpperCase()
        activity?.let {
            val healthAdvColor = pollutionLevelToColor(pollutionData.pollutionLevel, it)
            airQualityGrade.setTextColor(healthAdvColor)
        }
        updateMap()
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

    fun pollutionLevelToColor(level: PollutionLevel?, context: Context): Int {
        val colorID =  when (level) {
            PSILevel.GOOD, PMLevel.NORMAL -> R.color.grade_good
            PSILevel.MODERATE, PMLevel.ELEVATED -> R.color.grade_moderate
            PSILevel.UNHEALTHY, PMLevel.HIGH-> R.color.grade_unhealthy
            PSILevel.VERYUNHEALTHY -> R.color.grade_very_unhealthy
            PSILevel.HAZARDOUS,PMLevel.VERY_HIGH -> R.color.hazardous
            else -> R.color.colorText
        }
        return ContextCompat.getColor(context, colorID)

    }

    private fun updateMap(){
        mMap?.let {mMap ->
            _pollutionData?.regionMetadata?.forEach{ regionMeta ->
                val pollutionValue = _pollutionData?.pollutionValues?.get(regionMeta.name)
                activity?.let { context ->
                    val markerIcon = getMarkerIcon(
                        root = context.findViewById(R.id.contentFrame) as ViewGroup,
                        title = regionMeta.name,
                        value = ""+pollutionValue?.value,
                        color = pollutionLevelToColor(pollutionValue?.pollutionLevel, context))
                    mMap.addMarker(MarkerOptions().position(regionMeta.latLng()).icon(markerIcon))
                    if (regionMeta.name == "central") {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(regionMeta.latLng()))
                    }
                }

            }
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.5f ) )
            mMap.setOnMarkerClickListener(fun(it: Marker): Boolean { return false })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pollutionType = arguments?.getString(PollutionTypeKey)
        airQualityForecast.text = "($pollutionType)"

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
