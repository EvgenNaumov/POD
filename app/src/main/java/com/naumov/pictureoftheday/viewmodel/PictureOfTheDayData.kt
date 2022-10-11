package com.naumov.pictureoftheday.viewmodel

import com.naumov.pictureoftheday.Model.PODServerResponseData
import com.naumov.pictureoftheday.Model.mars.PictureOfTheMarsResponseData
import com.naumov.pictureoftheday.Model.moon.PictureOfTheEarthResponseDate
import com.naumov.pictureoftheday.Model.moon.PodOfTheDayEarthResponseDate
import com.naumov.pictureoftheday.Model.solar.PictureOfTheSolarResponseDate

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class SuccessMars(val serverResponseDataMars:PictureOfTheMarsResponseData):PictureOfTheDayData()
    data class SuccessSolar(val serverResponseDataSolar:PictureOfTheSolarResponseDate):PictureOfTheDayData()
    data class SuccessEarth(val serverResponseDataEarth:PodOfTheDayEarthResponseDate):PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}
