package com.example.weatherapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HavaDurumuAPIServis {

    object RetrofitClient {
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiServis : HavaDurumuAPI = retrofit.create(HavaDurumuAPI::class.java)

    }

}