package com.sp.singaporepsi.model

import com.google.android.gms.maps.model.LatLng

data class RegionMetadata(
    val name: String,
    val label_location: PSICoords
    )
fun RegionMetadata.latLng() = LatLng(label_location.latitude, label_location.longitude)
