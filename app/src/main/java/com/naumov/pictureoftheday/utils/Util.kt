package com.naumov.pictureoftheday.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.naumov.pictureoftheday.BuildConfig

const val TODAY = 1
const val YESTERDAY = 2
const val DBY = 3

const val PAGE_MARS = 0
const val PAGE_EARTH = 1
const val PAGE_SOLAR = 2
const val PAGE_FRAG = 3


const val KEY_PAGE_POD = "pod"
const val KEY_PAGE_MARS = "mars"
const val KEY_PAGE_EARTH = "earth"
const val KEY_PAGE_SOLAR = "solar"

const val THEME1 = 1
const val THEME2 = 2
const val THEME3 = 3

const val KEY_SP = "key_sp"
const val KEY_CURRENT_THEME = "current_theme"

val DEBUG: Boolean = BuildConfig.DEBUG && true
const val baseUrl = "https://api.nasa.gov/"
const val TAG = "@@@"
fun View.toast(string: String?, context: Context) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}
