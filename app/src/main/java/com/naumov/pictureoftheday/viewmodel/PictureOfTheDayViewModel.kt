package com.naumov.pictureoftheday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naumov.pictureoftheday.BuildConfig.NASA_API_KEY
import com.naumov.pictureoftheday.model.PODRetrofitImpl
import com.naumov.pictureoftheday.model.PODServerResponseData
import com.naumov.pictureoftheday.model.mars.PictureOfTheMarsResponseData
import com.naumov.pictureoftheday.model.earth.PodOfTheDayEarthResponseDate
import com.naumov.pictureoftheday.model.solar.PictureOfTheSolarResponseDate
import com.naumov.pictureoftheday.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PictureOfTheDayData> {
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(checkDay: Int, selectPage: String = "pod") {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)

        val apiKey: String = NASA_API_KEY;

        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value =
                PictureOfTheDayData.Error(Throwable("You need API key"))
            return
        }

        val calendar = Calendar.getInstance()
        val dateKey = when (checkDay) {
            TODAY -> {
                val today = calendar.time
                SimpleDateFormat("yyyy-MM-dd").format(today)
            }
            YESTERDAY -> {

                val yesterday = calendar.apply { add(Calendar.DAY_OF_MONTH, -1) }.time
                SimpleDateFormat("yyyy-MM-dd").format(yesterday)

            }
            DBY -> {
                val afterYesterday = calendar.apply { add(Calendar.DAY_OF_MONTH, -2) }.time
                SimpleDateFormat("yyyy-MM-dd").format(afterYesterday)
            }
            else -> {
                val today = calendar.time
                SimpleDateFormat("yyyy-MM-dd").format(today)
            }
        }

        when (selectPage) {
            KEY_PAGE_POD -> {

                retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, dateKey).enqueue(object :
                    Callback<PODServerResponseData> {
                    override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Success(response.body()!!)
                        } else {
                            val message = response.message()

                            if (message.isNullOrEmpty()) {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Error(Throwable("Unidentified error"))
                            } else {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                    }
                })

            }
            KEY_PAGE_MARS -> {
                retrofitImpl.getRetrofitImpl().getPictureOfTheMars(apiKey, 1000, 1, "MAST")
                    .enqueue(object :
                        Callback<PictureOfTheMarsResponseData> {
                        override fun onResponse(
                            call: Call<PictureOfTheMarsResponseData>,
                            response: Response<PictureOfTheMarsResponseData>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.SuccessMars(response.body()!!)
                            } else {
                                val message = response.message()

                                if (message.isNullOrEmpty()) {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable("Unidentified error"))
                                } else {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable(message))
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<PictureOfTheMarsResponseData>,
                            t: Throwable
                        ) {
                            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                        }
                    })
            }
            KEY_PAGE_EARTH -> {
                retrofitImpl.getRetrofitImpl().getListPictureOfTheEarth(apiKey).enqueue(
                    object : Callback<PodOfTheDayEarthResponseDate> {
                        override fun onResponse(
                            call: Call<PodOfTheDayEarthResponseDate>,
                            response: Response<PodOfTheDayEarthResponseDate>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.SuccessEarth(response.body()!!)
                            } else {
                                val message = response.message()

                                if (message.isNullOrEmpty()) {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable("Unidentified error"))
                                } else {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable(message))
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<PodOfTheDayEarthResponseDate>,
                            t: Throwable
                        ) {
                            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                        }
                    }
                )
            }
            KEY_PAGE_SOLAR -> {

                retrofitImpl.getRetrofitImpl().getPictureOfTheSystem(apiKey).enqueue(
                    object : Callback<PictureOfTheSolarResponseDate> {
                        override fun onResponse(
                            call: Call<PictureOfTheSolarResponseDate>,
                            response: Response<PictureOfTheSolarResponseDate>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.SuccessSolar(response.body()!!)
                            } else {
                                val message = response.message()

                                if (message.isNullOrEmpty()) {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable("Unidentified error"))
                                } else {
                                    liveDataForViewToObserve.value =
                                        PictureOfTheDayData.Error(Throwable(message))
                                }
                            }
                        }

                        override fun onFailure(
                            call: Call<PictureOfTheSolarResponseDate>,
                            t: Throwable
                        ) {
                            liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                        }
                    }
                )

            }
            else -> {
                liveDataForViewToObserve.value =
                    PictureOfTheDayData.Error(Throwable("Unidentified error"))
            }
        }

    }

    fun sendRequestToday(selectPage: String = KEY_PAGE_POD) {
        sendServerRequest(TODAY, selectPage)
    }

    fun sendRequestYT(selectPage: String = KEY_PAGE_POD) {
        sendServerRequest(YESTERDAY, selectPage)
    }

    fun sendRequestTDBY(selectPage: String = KEY_PAGE_POD) {
        sendServerRequest(DBY, selectPage)
    }
}