package com.naumov.pictureoftheday.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "section_table")
data class SectionEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String
    )