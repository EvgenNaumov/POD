package com.naumov.pictureoftheday.model.earth

import com.google.gson.annotations.SerializedName

data class PodOfTheDayEarthResponseDateItem(
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("image")
    val image: String
)