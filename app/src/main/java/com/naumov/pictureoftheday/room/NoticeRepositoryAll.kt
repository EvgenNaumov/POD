package com.naumov.pictureoftheday.room

import com.naumov.pictureoftheday.view.RecyclerFragment
import com.naumov.pictureoftheday.viewmodel.NoticeViewModel

interface NoticeRepositoryAll {
    fun getNoticeAllItems(callback:NoticeViewModel.CallbackAllNotice)
}