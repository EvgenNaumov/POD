package com.naumov.pictureoftheday.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.naumov.pictureoftheday.databinding.FragmentNavigation2Binding
import com.naumov.pictureoftheday.utils.PAGE_MARS
import com.naumov.pictureoftheday.utils.PAGE_EARTH
import com.naumov.pictureoftheday.utils.PAGE_FRAG
import com.naumov.pictureoftheday.utils.PAGE_SOLAR

class Navigation2Fragment : Fragment()  {

    private var _binding:FragmentNavigation2Binding? = null
    private val binding get() = _binding!!

    private lateinit var NavigationCollectionPagerAdapter: NavigationFragmentPagerAdapter2
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        NavigationCollectionPagerAdapter = NavigationFragmentPagerAdapter2(this)

        _binding = FragmentNavigation2Binding.inflate(inflater,container,false)
        binding.viewPager2.adapter = NavigationCollectionPagerAdapter

        TabLayoutMediator(binding.tabLayoutApi2,binding.viewPager2,object : TabLayoutMediator.TabConfigurationStrategy{

            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = when(position){
                    PAGE_MARS->"mars"
                    PAGE_EARTH->"earth"
                    PAGE_SOLAR->"solar"
                    PAGE_FRAG->"frag"
                    else -> ""
                }
            }
        }).attach()

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = Navigation2Fragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
