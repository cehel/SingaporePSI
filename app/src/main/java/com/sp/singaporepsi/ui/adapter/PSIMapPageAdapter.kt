package com.sp.singaporepsi.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class PSIMapPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount() = mFragments.size

    private val mFragments = ArrayList<Fragment>()
    private val mFragmentTitles = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String) {
        mFragments.add(fragment)
        mFragmentTitles.add(title)
    }

    fun removeAllFragments() {
        mFragments.clear()
        mFragmentTitles.clear()
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitles[position]
    }
}