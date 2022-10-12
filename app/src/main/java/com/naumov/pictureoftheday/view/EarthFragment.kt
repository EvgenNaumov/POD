package com.naumov.pictureoftheday.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.naumov.pictureoftheday.Model.moon.PodOfTheDayEarthResponseDateItem
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentMoonBinding
import com.naumov.pictureoftheday.utils.KEY_PAGE_EARTH
import com.naumov.pictureoftheday.utils.toast
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayData
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import kotlin.random.Random

class EarthFragment : Fragment() {

    private var _binding: FragmentMoonBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            binding.imageEarth.load(R.drawable.bg_earth)
        }

        viewmodel.getData().observe(viewLifecycleOwner) { renderData(it) }
        viewmodel.sendRequestToday(KEY_PAGE_EARTH)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.SuccessEarth -> {
                val serverResponseData: ArrayList<PodOfTheDayEarthResponseDateItem> =
                    data.serverResponseDataEarth
                var imageName: String?
                var strDate: String?
                if (serverResponseData.isNotEmpty()) {
                    val itemList = serverResponseData[Random.nextInt(serverResponseData.size - 1)]
                    imageName = itemList.image
                    strDate = itemList.identifier
                } else {
                    requireView().toast("Load error", requireContext())
                    return
                }

                if (imageName.isEmpty() || strDate.isEmpty()) {
                    requireView().toast("Load error", requireContext())
                } else {

                    val url = "https://epic.gsfc.nasa.gov/archive/natural/" +
                            strDate.subSequence(0, 4) + "/" + strDate.subSequence(4,6) + "/" + strDate.subSequence(6, 8) +
                            "/png/" +
                            "$imageName" + ".png"
                    binding.imageEarth.load(url) {
                        lifecycle(this@EarthFragment)
                        error(R.drawable.error_yellow_triangle)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                    }

                    binding.imageEarth.visibility = View.VISIBLE
                    binding.loadingLayout.visibility = View.GONE

                }
            }
            is PictureOfTheDayData.Loading -> {
                binding.imageEarth.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayData.Error -> {
                requireView().toast(data.error.message, requireContext())
                binding.imageEarth.visibility = View.VISIBLE
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
        fun newInstance() = EarthFragment()
    }
}