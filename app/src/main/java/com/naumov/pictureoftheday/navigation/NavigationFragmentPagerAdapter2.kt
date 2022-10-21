package com.naumov.pictureoftheday.navigation

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.naumov.pictureoftheday.utils.PAGE_MARS
import com.naumov.pictureoftheday.utils.PAGE_EARTH
import com.naumov.pictureoftheday.utils.PAGE_FRAG
import com.naumov.pictureoftheday.utils.PAGE_SOLAR
import com.naumov.pictureoftheday.view.*


class NavigationFragmentPagerAdapter2(fm: Fragment) : FragmentStateAdapter(fm) {

    private val fragments = arrayOf(MarsFragment(), EarthFragment(), SolarFragment(), RecyclerFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_MARS -> fragments[PAGE_MARS]
            PAGE_EARTH -> fragments[PAGE_EARTH]
            PAGE_SOLAR -> fragments[PAGE_SOLAR]
            PAGE_FRAG -> fragments[PAGE_FRAG]
            else -> MarsFragment()
        }
    }

}