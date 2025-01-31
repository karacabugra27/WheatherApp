package com.example.weatherapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.HavaDurumu
import com.example.weatherapp.service.HavaDurumuAPIServis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HavaDurumuViewModel(application:Application) : AndroidViewModel(application) {

    val havaDurumu = MutableLiveData<HavaDurumu>()

    fun verileriAl(city:String, apiKey:String, units:String) {
        viewModelScope.launch {
            try {
                val yanit = withContext(Dispatchers.IO) {
                    HavaDurumuAPIServis.RetrofitClient.apiServis.getGuncelHavaDurumu(city, apiKey, units)
                }
                havaDurumu.value = yanit
            } catch(e:Exception) {
                Log.e("HavaDurumu", "Veri çekme hatası: ${e.message}")
                e.printStackTrace()
            }
        }
    }

}