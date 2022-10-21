package com.naumov.pictureoftheday.recycler

import com.naumov.pictureoftheday.utils.PAGE_EARTH
import java.util.*

data class Data(    val type: Int = PAGE_EARTH,
                    val someText: String = "Text",
                    val someDescription: String? = "Description"
){
    companion object {
        const val TYPE_EARTH = 0
        const val TYPE_MARS = 1
        const val TYPE_HEADER = 2
    }
}


