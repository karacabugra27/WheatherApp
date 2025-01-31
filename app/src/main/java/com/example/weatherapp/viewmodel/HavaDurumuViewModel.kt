package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.HavaDurumu
import com.example.weatherapp.service.HavaDurumuAPIServis
import kotlinx.coroutines.launch


class HavaDurumuViewModel(application:Application) : AndroidViewModel(application) {

    val havaDurumu = MutableLiveData<HavaDurumu>()


    private fun verileriAl(city:String, apiKey:String, units:String) {
        viewModelScope.launch {
                val yanit = HavaDurumuAPIServis.RetrofitClient.apiServis.getGuncelHavaDurumu(city,apiKey,units)
                havaDurumu.value = yanit
        }
    }

}