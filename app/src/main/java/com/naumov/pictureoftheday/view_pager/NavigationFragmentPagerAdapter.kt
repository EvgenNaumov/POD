package com.naumov.pictureoftheday.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naumov.pictureoftheday.view.MarsFragment
import com.naumov.pictureoftheday.view.MoonFragment
import com.naumov.pictureoftheday.view.SolarFragment

private const val PAGE_MARS = 0
private const val PAGE_MOON = 1
private const val PAGE_SOLAR = 2

class NavigationFragmentPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){


    private val fragments = arrayOf(MarsFragment(), MoonFragment(), SolarFragment())
    override fun getCount() =  fragments.size

    override fun getItem(position: Int): Fragment {
//        return fragments[position]
        return when(position){
            PAGE_MARS -> fragments[PAGE_MARS]
            PAGE_MOON -> fragments[PAGE_MOON]
            PAGE_SOLAR -> fragments[PAGE_SOLAR]
            else -> MarsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            PAGE_MARS -> "MARS"
            PAGE_MOON-> "EARTH"
            PAGE_SOLAR-> "SOLAR"
            else -> ""
        }
    }

}