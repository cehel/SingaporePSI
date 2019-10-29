package com.sp.singaporepsi.model

import com.google.android.gms.maps.model.LatLng
import com.sp.singaporepsi.model.mapper.PollutionDataMapper
import com.sp.singaporepsi.model.ui.PollutionLevel
import com.sp.singaporepsi.ui.psimap.PSIMapFragment

data class RegionMetadata(
    val name: String,
    val label_location: PSICoords
    )
{
    fun latLng() = LatLng(label_location.latitude, label_location.longitude)
}


