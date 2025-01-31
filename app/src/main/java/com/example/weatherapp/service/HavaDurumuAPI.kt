package com.example.weatherapp.service

import com.example.weatherapp.model.HavaDurumu
import retrofit2.http.GET
import retrofit2.http.Query

interface HavaDurumuAPI {

    @GET("weather")
    suspend fun getGuncelHavaDurumu(
        @Query("q") sehir : String,
        @Query("appid") apiKey : String,
        @Query("units") units : String
    ) : HavaDurumu


}