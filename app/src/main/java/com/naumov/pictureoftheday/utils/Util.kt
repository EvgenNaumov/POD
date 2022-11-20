package com.naumov.pictureoftheday.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.naumov.pictureoftheday.BuildConfig
import com.naumov.pictureoftheday.recycler.Data
import com.naumov.pictureoftheday.room.NoticeEntity

const val HEIGHT_PRIORITY = 1
const val MEDIUM_PRIORITY = 2
const val LOW_PRIORITY = 3
const val NORMAL_PRIORITY = 4

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

const val SPLASH_MILLI_FUTURE = 2000L
const val SPLASH_COUNTDOWN_INTERVAL = 1000L

val DEBUG: Boolean = BuildConfig.DEBUG && true
const val baseUrl = "https://api.nasa.gov/"
const val TAG = "@@@"
fun View.toast(string: String?, context: Context) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

fun convertNoticeEntityToNotice(noticeEntity:List<NoticeEntity>):List<Pair<Data,Boolean>>{
   return noticeEntity.map {
       Pair(Data(title = it.title, someText = it.someText, someDescription = it.description, priority = convertPriorityToData(it.priority), id_section = it.id_section),false)
   }
}

fun convertPriorityToData(priority: Int): PriorityEnum {
   return when(priority){
        HEIGHT_PRIORITY -> PriorityEnum.Height
        MEDIUM_PRIORITY -> PriorityEnum.Medium
        LOW_PRIORITY -> PriorityEnum.Low
        NORMAL_PRIORITY -> PriorityEnum.Normal
       else -> {PriorityEnum.Normal}
   }

}

fun convertNoticeToNoticeEntity(notice: Data):NoticeEntity{
    return NoticeEntity(id=0, id_section = notice.id_section, title = notice.title, someText = notice.someText, description = notice.someDescription?:"", priority = convertPriorityToDB(notice.priority))
}

fun convertPriorityToDB(priority: PriorityEnum): Int {
   return when(priority){
        PriorityEnum.Height  -> HEIGHT_PRIORITY
        PriorityEnum.Medium -> MEDIUM_PRIORITY
        PriorityEnum.Low -> LOW_PRIORITY
        PriorityEnum.Normal -> NORMAL_PRIORITY
        else -> {NORMAL_PRIORITY}
    }
}
