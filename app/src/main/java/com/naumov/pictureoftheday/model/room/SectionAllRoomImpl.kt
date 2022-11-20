package com.naumov.pictureoftheday.model.room

import com.naumov.pictureoftheday.model.room.SectionAllRoom
import com.naumov.pictureoftheday.ui.App
import com.naumov.pictureoftheday.viewmodel.NoticeViewModel

class SectionAllRoomImpl: SectionAllRoom {
    override fun getSectionAllRoom() {
        Thread{
           App.getNoticeDao().getAllSection()
        }.start()
    }
}