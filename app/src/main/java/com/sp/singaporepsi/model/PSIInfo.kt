package com.sp.singaporepsi.model

data class PSIInfo(
    val api_info: APIInfo,
    val region_metadata: List<RegionMetadata>,
    val items: List<PSIItem>)