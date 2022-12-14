package com.naumov.pictureoftheday.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transition.Transition
import coil.transition.TransitionTarget
import com.naumov.pictureoftheday.model.mars.Photo
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentMarsBinding
import com.naumov.pictureoftheday.utils.KEY_PAGE_MARS
import com.naumov.pictureoftheday.utils.toast
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayData
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayViewModel
import kotlin.random.Random

class MarsFragment : Fragment() {
    private var _binding:FragmentMarsBinding? = null
    private val binding get() = _binding!!
    var isFlag = false

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
        // Inflate the layout for this fragment
        _binding = FragmentMarsBinding.inflate(inflater, container, false)

        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState==null){
            binding.imageMars.load(R.drawable.bg_mars)
            binding.imageMars.visibility = View.VISIBLE
        }


        viewModel.getData()
            .observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendRequestToday(KEY_PAGE_MARS)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.SuccessMars -> {
                val serverResponseData = data.serverResponseDataMars
                var url = ""
                val photo:List<Photo> = serverResponseData.photos
                if (photo.isNotEmpty()) {
                    val rndPhoto = Random.nextInt(photo.size - 1)
                    url = photo[rndPhoto].imgSrc.replace("http","https",true)
                }else{
                    requireView().toast("Load error", requireContext())
                    return
                }

                if (url.isEmpty()) {
                    requireView().toast("Link is empty", requireContext())
                } else {
                    binding.imageMars.load(url) {
                        lifecycle(this@MarsFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                        crossfade(true)
                        size(400,400)
                        listener { _, _ ->
                            Toast.makeText(context, "Succes", Toast.LENGTH_SHORT).show()

                        }
                    }
                    isFlag = !isFlag
                    var  transitionSet = TransitionSet()
                    transitionSet.ordering = TransitionSet.ORDERING_TOGETHER

                    val slide = Slide()
                    slide.slideEdge = Gravity.RIGHT
                    slide.duration = 2000L
                    val changeImage = ChangeImageTransform()
                    transitionSet.addTransition(slide)
                    transitionSet.addTransition(changeImage)
                    TransitionManager.beginDelayedTransition(binding.viewTransition,transitionSet)
                    binding.imageMars.visibility = if (isFlag) View.VISIBLE else View.GONE
                    binding.loadingLayout.visibility = View.GONE

                }
            }
            is PictureOfTheDayData.Loading -> {
               binding.imageMars.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PictureOfTheDayData.Error -> {
                requireView().toast(data.error.message, requireContext())
                binding.imageMars.visibility = View.VISIBLE
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
        fun newInstance() = MarsFragment()
    }
}
