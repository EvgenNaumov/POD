package com.naumov.pictureoftheday.room

import android.graphics.drawable.Icon
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.naumov.pictureoftheday.utils.PriorityEnum

@Entity(tableName = "notice_table")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val id_section:String,
    val title:String,
    val priority:Int,
    val someText:String,
    val description:String,
)
