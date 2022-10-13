package com.naumov.pictureoftheday.viewmodel

import com.naumov.pictureoftheday.model.PODServerResponseData
import com.naumov.pictureoftheday.model.mars.PictureOfTheMarsResponseData
import com.naumov.pictureoftheday.model.earth.PodOfTheDayEarthResponseDate
import com.naumov.pictureoftheday.model.solar.PictureOfTheSolarResponseDate

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class SuccessMars(val serverResponseDataMars:PictureOfTheMarsResponseData):PictureOfTheDayData()
    data class SuccessSolar(val serverResponseDataSolar:PictureOfTheSolarResponseDate):PictureOfTheDayData()
    data class SuccessEarth(val serverResponseDataEarth:PodOfTheDayEarthResponseDate):PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}
