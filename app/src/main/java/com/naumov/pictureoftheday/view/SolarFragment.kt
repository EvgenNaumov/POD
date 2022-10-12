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
import com.naumov.pictureoftheday.databinding.FragmentSolarBinding
import com.naumov.pictureoftheday.utils.KEY_PAGE_EARTH
import com.naumov.pictureoftheday.utils.KEY_PAGE_SOLAR
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayData
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayViewModel

class SolarFragment : Fragment() {

    private var _binding: FragmentSolarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSolarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            binding.imageSolar.load(R.drawable.bg_earth)
        }

        viewModel.getData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendRequestToday(KEY_PAGE_SOLAR)

    }

    private fun renderData(data: PictureOfTheDayData) {

        when (data) {
            is PictureOfTheDayData.Error -> {

                binding.imageSolar.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.imageSolar.load(R.drawable.error_yellow_triangle)
            }
            is PictureOfTheDayData.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.imageSolar.visibility = View.GONE
            }
            is PictureOfTheDayData.SuccessSolar -> {

               val url = data.serverResponseDataSolar.url

                if (url.isEmpty()) {
                    binding.imageSolar.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE
                    binding.imageSolar.load(R.drawable.error_yellow_triangle)
                    return
                }
                binding.imageSolar.load(url){
                    lifecycle(this@SolarFragment)
                    placeholder(R.drawable.bg_earth)
                    error(R.drawable.error_yellow_triangle)
                }
                binding.imageSolar.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SolarFragment()
    }
}