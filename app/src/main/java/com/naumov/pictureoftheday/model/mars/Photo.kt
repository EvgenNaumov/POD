package com.naumov.pictureoftheday.model.mars

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("earth_date")
    val earthDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("sol")
    val sol: Int
)
