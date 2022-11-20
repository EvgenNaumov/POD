package com.naumov.pictureoftheday.room

import com.naumov.pictureoftheday.ui.App
import com.naumov.pictureoftheday.utils.convertNoticeEntityToNotice
import com.naumov.pictureoftheday.view.RecyclerFragment
import com.naumov.pictureoftheday.viewmodel.NoticeViewModel

class NoticeRoomImpl:NoticeRepositoryAll {
    override fun getNoticeAllItems(callback:NoticeViewModel.CallbackAllNotice) {
        Thread{
            callback.onResponse(convertNoticeEntityToNotice(App.getNoticeDao().getAllNotice()))
        }.start()
    }
}