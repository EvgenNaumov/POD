package com.naumov.pictureoftheday.recycler

import com.naumov.pictureoftheday.utils.PriorityEnum

data class NoticeData(
   val type:Int,
   val title:String,
   val someText:String,
   val someDescription:String,
   val priority:PriorityEnum,
   val pair:Boolean,
   val id_section:String
)
