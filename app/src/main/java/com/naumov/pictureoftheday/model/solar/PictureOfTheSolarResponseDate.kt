package com.naumov.pictureoftheday.model.solar

import com.google.gson.annotations.SerializedName

data class PictureOfTheSolarResponseDate(
    @SerializedName("url")
    val url: String
)
