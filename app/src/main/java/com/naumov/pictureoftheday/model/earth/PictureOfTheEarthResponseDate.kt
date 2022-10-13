package com.naumov.pictureoftheday.model.earth

import com.google.gson.annotations.SerializedName

data class PictureOfTheEarthResponseDate(
    @SerializedName("url")
    val url: String
)
