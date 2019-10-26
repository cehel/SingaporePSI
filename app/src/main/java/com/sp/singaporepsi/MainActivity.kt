package com.sp.singaporepsi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sp.singaporepsi.ui.psimap.PSIMapFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.psimap_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PSIMapFragment.newInstance())
                .commitNow()
        }
    }

}
