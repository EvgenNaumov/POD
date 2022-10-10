package com.naumov.pictureoftheday.Model.mars

import com.google.gson.annotations.SerializedName
import com.naumov.pictureoftheday.Model.mars.Photo

data class PictureOfTheMarsResponseData(
    @SerializedName("photos")
    val photos: List<Photo>
)

