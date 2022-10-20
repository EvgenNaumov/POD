package com.naumov.pictureoftheday.view

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
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

    private var show = false

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

        binding.imageSolar.setOnClickListener {
            if (show) hideComponents() else showComponemts()
        }
    }

    private fun showComponemts() {
        show = true
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_solar_end)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        val changeImage = ChangeImageTransform()
        val transitionSet = TransitionSet()
        transitionSet.addTransition(transition)
        transitionSet.addTransition(changeImage)
        TransitionManager.beginDelayedTransition(binding.constraintImage,
            transitionSet)
        constraintSet.applyTo(binding.constraintImage)

        val param: ViewGroup.LayoutParams = binding.imageSolar.layoutParams
        param.height = ViewGroup.LayoutParams.MATCH_PARENT
        binding.imageSolar.layoutParams = param
        binding.imageSolar.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    private fun hideComponents() {
        show = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.fragment_solar)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200
        val changeImage = ChangeImageTransform()
        val transitionSet = TransitionSet()
        transitionSet.addTransition(transition)
        transitionSet.addTransition(changeImage)
        TransitionManager.beginDelayedTransition(binding.constraintImage,
            transitionSet)
        constraintSet.applyTo(binding.constraintImage)

        val param: ViewGroup.LayoutParams = binding.imageSolar.layoutParams
        param.height = ViewGroup.LayoutParams.WRAP_CONTENT
        binding.imageSolar.layoutParams = param
        binding.imageSolar.scaleType = ImageView.ScaleType.CENTER

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
                binding.title.text = data.serverResponseDataSolar.title
                binding.date.text = data.serverResponseDataSolar.date
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