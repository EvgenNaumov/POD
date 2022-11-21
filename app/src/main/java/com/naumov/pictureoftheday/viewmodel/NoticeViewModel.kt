package com.naumov.pictureoftheday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naumov.pictureoftheday.recycler.Data
import com.naumov.pictureoftheday.room.NoticeRepositoryAll
import com.naumov.pictureoftheday.room.NoticeRoomImpl

class NoticeViewModel(
    private val liveData: MutableLiveData<NoticeAppState> = MutableLiveData(),
    private val repository: NoticeRoomImpl = NoticeRoomImpl()
) : ViewModel() {

    fun getData(): LiveData<NoticeAppState> {
        return liveData
    }

    fun getAll() {
        repository.getNoticeAllItems(object : CallbackAllNotice {
            override fun onFail(t: Throwable) {
                liveData.postValue(NoticeAppState.Error(t))
            }

            override fun onResponse(listNotice: List<Pair<Data, Boolean>>) {
                liveData.postValue(NoticeAppState.Success(listNotice))
            }
        })
    }

    interface CallbackAllNotice {
        fun onResponse(listNotice: List<Pair<Data, Boolean>>)
        fun onFail(t: Throwable)
    }
}