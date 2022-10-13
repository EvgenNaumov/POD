package com.naumov.pictureoftheday.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentNavigationBinding

class NavigationFragment : Fragment()  {

    private var _binding:FragmentNavigationBinding? = null
    private val binding get() = _binding!!

    private lateinit var NavigationCollectionPagerAdapter: NavigationFragmentPagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        NavigationCollectionPagerAdapter = NavigationFragmentPagerAdapter(childFragmentManager)

        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = NavigationCollectionPagerAdapter

        binding.tabLayoutApi.setupWithViewPager(binding.viewPager)
        binding.tabLayoutApi.getTabAt(0)?.apply {
            this.setIcon(R.drawable.ic_mars)
            this.tag = "mars"
        }
        binding.tabLayoutApi.getTabAt(1)?.apply {
            setIcon(R.drawable.ic_earth)
            tag = "earth"
        }
        binding.tabLayoutApi.getTabAt(2)?.apply {
            setIcon(R.drawable.ic_system)
            tag = "system"
        }

        binding.tabLayoutApi.selectTab(binding.tabLayoutApi.getTabAt(0))
        binding.tabLayoutApi.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Toast.makeText(context, "${tab?.tag}", Toast.LENGTH_SHORT).show()
                when(tab?.tag){
                    "mars"-> {}
                    "earth"-> {}
                    "solar"->{}
                    else -> {}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Toast.makeText(context, "${tab?.tag}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(context, "${tab?.tag}", Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NavigationFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
