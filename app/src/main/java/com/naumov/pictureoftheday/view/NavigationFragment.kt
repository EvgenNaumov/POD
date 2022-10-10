package com.naumov.pictureoftheday.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
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
