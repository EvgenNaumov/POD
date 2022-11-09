package com.naumov.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.*
import android.transition.*
import android.view.*
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naumov.pictureoftheday.model.PODServerResponseData
import com.naumov.pictureoftheday.R
import com.naumov.pictureoftheday.databinding.FragmentPictureOfTheDayBinding
import com.naumov.pictureoftheday.navigation.Navigation2Fragment
import com.naumov.pictureoftheday.ui.MainActivity
import com.naumov.pictureoftheday.utils.FormatTextSpannable
import com.naumov.pictureoftheday.utils.KEY_PAGE_POD
import com.naumov.pictureoftheday.utils.toast
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayData
import com.naumov.pictureoftheday.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(PictureOfTheDayViewModel::class.java)
    }

    private var isFlag = false;
    private var mapList: MutableList<List<Char>>? = null

    private var formatText:FormatTextSpannable?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getData()
            .observe(viewLifecycleOwner) { renderData(it) }
        viewModel.sendRequestToday(KEY_PAGE_POD)

        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomSheetBehavior()
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        formatText = FormatTextSpannable()

        binding.imageView.setOnClickListener {
            isFlag = !isFlag
            TransitionManager.beginDelayedTransition(
                binding.main, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
            params.height =
                if (isFlag) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageView.layoutParams = params
            binding.imageView.scaleType =
                if (isFlag) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }


        initChip()
        if (savedInstanceState == null) {
            binding.chipGroup.check(R.id.chip_td)
        }

        setBottomAppBar(view)
    }

    private fun initChip() {

        binding.chipTd.setOnClickListener {
            binding.imageView.setImageDrawable(requireContext().getDrawable(R.drawable.ic_no_photo_vector))
            binding.imageView.visibility = View.VISIBLE
            binding.videoOfTheDay.visibility = View.GONE
            viewModel.sendRequestToday(KEY_PAGE_POD)
        }
        binding.chipEst.setOnClickListener {
            binding.imageView.setImageDrawable(requireContext().getDrawable(R.drawable.ic_no_photo_vector))
            binding.imageView.visibility = View.VISIBLE
            binding.videoOfTheDay.visibility = View.GONE

            viewModel.sendRequestYT(KEY_PAGE_POD)
        }
        binding.chipDby.setOnClickListener {
            binding.imageView.setImageDrawable(requireContext().getDrawable(R.drawable.ic_no_photo_vector))
            binding.imageView.visibility = View.VISIBLE
            binding.videoOfTheDay.visibility = View.GONE

            viewModel.sendRequestTDBY(KEY_PAGE_POD)
        }
        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_telescope -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, Navigation2Fragment.newInstance())
                    ?.addToBackStack(null)
                    ?.commit()
            }
            R.id.app_bar_photo_library -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, Navigation2Fragment.newInstance())
                    ?.addToBackStack(null)
                    ?.commit()
            }
            R.id.app_bar_fav -> {
                requireView().toast("Favourite", requireContext())
            }
            R.id.app_bar_settings -> {
                requireView().toast("Settings", requireContext())
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, SettingsFragment.newInstance())
                    ?.addToBackStack(null)
                    ?.commit()
            }
            R.id.app_bar_zoom -> {
                isFlag = !isFlag
                val transitionSet = TransitionSet()
                val param = binding.main.layoutParams
                val changeImageTransform = ChangeImageTransform()
                val changeBounds = ChangeBounds()

                transitionSet.addTransition(changeBounds)
                transitionSet.addTransition(changeImageTransform)

                TransitionManager.beginDelayedTransition(binding.main, transitionSet)
                if (isFlag) {
                    param.height = CoordinatorLayout.LayoutParams.MATCH_PARENT
                    binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                } else {
                    param.height = CoordinatorLayout.LayoutParams.WRAP_CONTENT
                    binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                }

            }

            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }


        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData

                val url = serverResponseData.url

                if (url.isNullOrEmpty()) {
                    requireView().toast("Link is empty", requireContext())
                } else {
                    setData(serverResponseData)

                    binding.includeBottomSheetDescription.bottomSheetDescriptionHeader.text =
                        data.serverResponseData.title
                    binding.includeBottomSheetDescription.bottomSheetDescription.text =
                        data.serverResponseData.explanation


//                    val spannebleString =
//                        SpannableString(data.serverResponseData.explanation.toString())
//                    binding.includeBottomSheetDescription.bottomSheetDescription.setText(
//                        spannebleString,
//                        TextView.BufferType.SPANNABLE
//                    )
//                    val spannebleText =
//                        binding.includeBottomSheetDescription.bottomSheetDescription.text as Spannable


                    val spannebleText = formatText?.setSpanneble(data.serverResponseData.explanation.toString(),binding.includeBottomSheetDescription.bottomSheetDescription)
                    if (spannebleText != null) {
                        formatText?.setСolorTextExplanation(spannebleText)
                    }

                }
            }
            is PictureOfTheDayData.Loading -> {
                //TODO
            }
            is PictureOfTheDayData.Error -> {
                requireView().toast(data.error.message, requireContext())
            }
        }
    }


    private fun setData(data: PODServerResponseData) {
        val url = data.hdurl
        if (url.isNullOrEmpty()) {
            val videoUrl = data.url
            videoUrl?.let { showAVideoUrl(it) }
        } else {
            binding.imageView.load(url) {
                lifecycle(this@PictureOfTheDayFragment)
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
                crossfade(true)
            }

        }
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        binding.imageView.visibility = View.GONE
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = getString(R.string.message_hdurl, videoUrl, ">", "<")
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
        }

        val spannebleText = formatText?.setSpanneble(videoOfTheDay.text.toString(),videoOfTheDay)
        if (spannebleText != null) {
//            formatText?.setTextAsHyperReference(spannebleText,videoUrl)
            formatText?.setBulletSpan(requireContext(),spannebleText)
            formatText?.setTextAsHyperReference(spannebleText)
        }

    }

    private fun setBottomSheetBehavior() {
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.includeBottomSheetDescription.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)

        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }

}