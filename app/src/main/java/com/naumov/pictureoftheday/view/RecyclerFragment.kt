package com.naumov.pictureoftheday.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentCoordinatorBinding
import com.naumov.pictureoftheday.databinding.FragmentRecyclerBinding
import com.naumov.pictureoftheday.databinding.FragmentSolarBinding
import com.naumov.pictureoftheday.recycler.Data
import com.naumov.pictureoftheday.recycler.RecyclerAdapter
import com.naumov.pictureoftheday.utils.KEY_PAGE_EARTH
import com.naumov.pictureoftheday.utils.KEY_PAGE_SOLAR
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayData
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayViewModel

class RecyclerFragment : Fragment() {

    private var _binding: FragmentRecyclerBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {

        }

        val data = arrayListOf<Data>(
            Data(someText = "Mars", type = Data.TYPE_MARS),
            Data(someText = "Earth", type = Data.TYPE_EARTH),
            Data(someText = "Mars", type = Data.TYPE_MARS),
            Data(someText = "Mars", type = Data.TYPE_MARS),
            Data(someText = "Mars", type = Data.TYPE_MARS),
            Data(someText = "Earth", type = Data.TYPE_EARTH),
            Data(someText = "Earth", type = Data.TYPE_EARTH),
            Data(someText = "Earth", type = Data.TYPE_EARTH),
            Data(someText = "Earth", type = Data.TYPE_EARTH),

        )

        binding.recyclerView.adapter  = RecyclerAdapter(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = RecyclerFragment()
    }
}