package com.naumov.pictureoftheday.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.BottomNavigationLayoutBinding
import com.naumov.pictureoftheday.utils.FormatTextSpannable

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_one -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.container, RecyclerFragment.newInstance())
                        ?.addToBackStack(null)?.commit()
                    activity?.supportFragmentManager?.fragments?.remove(this)
                }
                R.id.navigation_two -> {
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}