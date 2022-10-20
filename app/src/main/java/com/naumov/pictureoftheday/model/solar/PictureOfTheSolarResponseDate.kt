package com.naumov.pictureoftheday.model.solar

import com.google.gson.annotations.SerializedName

data class PictureOfTheSolarResponseDate(
    @SerializedName("url")
    val url: String,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("copyright")
    val copyright: String?,
    @field:SerializedName("date")
    val date: String?,

    )
