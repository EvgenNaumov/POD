package com.naumov.pictureoftheday.viewmodel

import com.naumov.pictureoftheday.recycler.Data

sealed class NoticeAppState{
    object Loading:NoticeAppState()
    data class Success(val noticeData:List<Pair<Data,Boolean>>):NoticeAppState()
    data class  Error(val error:Throwable):NoticeAppState()
}
