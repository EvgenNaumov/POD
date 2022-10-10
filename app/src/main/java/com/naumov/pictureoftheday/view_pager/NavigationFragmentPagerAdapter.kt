package com.naumov.pictureoftheday.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naumov.pictureoftheday.view.MarsFragment
import com.naumov.pictureoftheday.view.MoonFragment
import com.naumov.pictureoftheday.view.SolarFragment

class NavigationFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    final val PAGE_MARS = "MARS"
    final val PAGE_MOON = "MOON"
    final val PAGE_SOLAR = "SOLAR"

    private val fragments = arrayOf(MarsFragment(), MoonFragment(), SolarFragment())
    private val fragments1 = arrayOf(PAGE_MARS, PAGE_MOON, PAGE_SOLAR)
    override fun getCount() =  fragments.size

    override fun getItem(position: Int): Fragment {
        return fragments[position]
//        return when(fragments1[position]){
//            PAGE_MARS -> MarsFragment()
//            PAGE_MOON -> MoonFragment()
//            PAGE_SOLAR -> SolarFragment()
//            else -> MarsFragment()
//        }
    }
}