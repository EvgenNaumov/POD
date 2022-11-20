package com.naumov.pictureoftheday.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NoticeEntity::class, SectionEntity::class), version = 1)
abstract class NoticeDB: RoomDatabase() {
    abstract fun noticeDao():NoticeDao
}