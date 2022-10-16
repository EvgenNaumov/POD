package com.naumov.pictureoftheday.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentSolarSystemBinding

class ApiFragment : Fragment() {

    private var _binding: FragmentSolarSystemBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSolarSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottonNavi()
        binding.bottomNavigation.selectedItemId = R.id.action_bottom_navigation_mars
        startFragment(MarsFragment.newInstance())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_navigation_system, menu)

    }

    private fun initBottonNavi() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_bottom_navigation_earth -> startFragment(EarthFragment.newInstance())

                R.id.action_bottom_navigation_mars -> startFragment(MarsFragment.newInstance())

                R.id.action_bottom_navigation_system -> startFragment(SolarFragment.newInstance())

                R.id.action_bottom_navigation_fr -> startFragment(CoordinatorFragment.newInstance())

                else -> true
            }
            true
        }
    }

    private fun startFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack("")
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApiFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
