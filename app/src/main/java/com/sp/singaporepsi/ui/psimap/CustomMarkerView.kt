package com.sp.singaporepsi.ui.psimap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.sp.singaporepsi.R
import kotlinx.android.synthetic.main.marker_layout.view.*

class CustomMarkerView(root: ViewGroup,
                       title: String?,
                       value: String?,
                       color: Int) : FrameLayout(root.context) {

    init {
        View.inflate(context, R.layout.marker_layout, this)

        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        markerTitle.text = title?.toUpperCase()
        markerValue.text = value
        markerValue.setTextColor(color)
    }
    
    companion object {
        fun getMarkerIcon(root: ViewGroup, title: String?, value: String, color: Int): BitmapDescriptor? {
            val markerView = CustomMarkerView(root, title, value, color)
            markerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)

            val b = Bitmap.createBitmap(markerView.measuredWidth, markerView.measuredHeight,
                Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            c.translate((-markerView.scrollX).toFloat(), (-markerView.scrollY).toFloat())
            markerView.draw(c)

            return BitmapDescriptorFactory.fromBitmap(b)
        }
    }
}

