package com.naumov.pictureoftheday.room

import com.naumov.pictureoftheday.room.SectionAllRoom
import com.naumov.pictureoftheday.ui.App

class SectionAllRoomImpl: SectionAllRoom {
    override fun getSectionAllRoom() {
        Thread{
           App.getNoticeDao().getAllSection()
        }.start()
    }
}