package com.naumov.pictureoftheday.ui

import android.app.Application
import androidx.room.Room
import com.naumov.pictureoftheday.room.NoticeDB
import com.naumov.pictureoftheday.room.NoticeDao
import java.lang.IllegalStateException

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val appInstance = this
    }

    companion object {
        private val appInstance: App? = null
        private var db: NoticeDB? = null
        private const val DB_NAME = "Notice.db"

        fun getNoticeDao(): NoticeDao {
            if (db == null) {
                synchronized(NoticeDB::class.java) {
                    if (db == null) {
                        if (appInstance!= null) {
                            db = Room.databaseBuilder(appInstance!!, NoticeDB::class.java, DB_NAME)
                                .build()
                        } else {
                            throw IllegalStateException("ошибка инициализации базы данных примечаний")
                        }

                    }
                }
            }
            return db!!.noticeDao()
        }
    }
}