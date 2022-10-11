package com.naumov.pictureoftheday.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentNavigationBinding
import com.naumov.pictureoftheday.view_pager.NavigationFragmentPagerAdapter

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
        binding.tabLayoutApi.getTabAt(0)?.setIcon(R.drawable.bg_mars)
        binding.tabLayoutApi.getTabAt(0)?.tag = "mars"
        binding.tabLayoutApi.getTabAt(1)?.setIcon(R.drawable.bg_earth)
        binding.tabLayoutApi.getTabAt(0)?.tag = "earth"
        binding.tabLayoutApi.getTabAt(2)?.setIcon(R.drawable.bg_system)
        binding.tabLayoutApi.getTabAt(0)?.tag = "system"

        binding.viewPager.setOnClickListener {
               when (it.tag){

               }
        }
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
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
