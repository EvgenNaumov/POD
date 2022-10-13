package com.naumov.pictureoftheday.model

import com.naumov.pictureoftheday.model.mars.PictureOfTheMarsResponseData
import com.naumov.pictureoftheday.model.earth.PodOfTheDayEarthResponseDate
import com.naumov.pictureoftheday.model.solar.PictureOfTheSolarResponseDate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String, @Query("date") dateKey:String): Call<PODServerResponseData>

    //mars
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getPictureOfTheMars(@Query("api_key") apiKey:String, @Query("sol") sol:Int, @Query("page") page:Int, @Query("camera") camera:String): Call<PictureOfTheMarsResponseData>

    //system
    @GET("planetary/apod")
    fun getPictureOfTheSystem(@Query("api_key") apiKey:String): Call<PictureOfTheSolarResponseDate>

    //earth
    @GET("EPIC/api/natural/images")
    fun getListPictureOfTheEarth(@Query("api_key") apiKey:String): Call<PodOfTheDayEarthResponseDate>


}