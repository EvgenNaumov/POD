package com.naumov.pictureoftheday.model.mars

import com.google.gson.annotations.SerializedName

data class PictureOfTheMarsResponseData(
    @SerializedName("photos")
    val photos: List<Photo>
)

