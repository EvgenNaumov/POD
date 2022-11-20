package com.naumov.pictureoftheday.recycler

import com.naumov.pictureoftheday.utils.PAGE_EARTH
import com.naumov.pictureoftheday.utils.PriorityEnum
import java.util.*

data class Data(
    val id: Int = 0,
    val title:String,
    val type: Int = PAGE_EARTH,
    val someText: String = "Text",
    val someDescription: String? = "Description",
    var priority:PriorityEnum = PriorityEnum.Normal,
    val id_section:String
) {
    companion object {
        const val TYPE_EARTH = 0
        const val TYPE_MARS = 1
        const val TYPE_HEADER = 2
    }
}


