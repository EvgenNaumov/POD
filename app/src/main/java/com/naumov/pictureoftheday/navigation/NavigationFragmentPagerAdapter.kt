package com.naumov.pictureoftheday.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naumov.pictureoftheday.utils.PAGE_MARS
import com.naumov.pictureoftheday.utils.PAGE_EARTH
import com.naumov.pictureoftheday.utils.PAGE_SOLAR
import com.naumov.pictureoftheday.view.MarsFragment
import com.naumov.pictureoftheday.view.EarthFragment
import com.naumov.pictureoftheday.view.SolarFragment


class NavigationFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){


    private val fragments = arrayOf(MarsFragment(), EarthFragment(), SolarFragment())
    override fun getCount() =  fragments.size

    override fun getItem(position: Int): Fragment {
//        return fragments[position]
        return when(position){
            PAGE_MARS -> fragments[PAGE_MARS]
            PAGE_EARTH -> fragments[PAGE_EARTH]
            PAGE_SOLAR -> fragments[PAGE_SOLAR]
            else -> MarsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            PAGE_MARS -> "MARS"
            PAGE_EARTH-> "EARTH"
            PAGE_SOLAR-> "SOLAR"
            else -> ""
        }
    }

}