package com.naumov.pictureoftheday.navigation

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.naumov.pictureoftheday.utils.PAGE_MARS
import com.naumov.pictureoftheday.utils.PAGE_EARTH
import com.naumov.pictureoftheday.utils.PAGE_SOLAR
import com.naumov.pictureoftheday.view.MarsFragment
import com.naumov.pictureoftheday.view.EarthFragment
import com.naumov.pictureoftheday.view.SolarFragment


class NavigationFragmentPagerAdapter2(fm: Fragment) : FragmentStateAdapter(fm) {

    private val fragments = arrayOf(MarsFragment(), EarthFragment(), SolarFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_MARS -> fragments[PAGE_MARS]
            PAGE_EARTH -> fragments[PAGE_EARTH]
            PAGE_SOLAR -> fragments[PAGE_SOLAR]
            else -> MarsFragment()
        }
    }

}