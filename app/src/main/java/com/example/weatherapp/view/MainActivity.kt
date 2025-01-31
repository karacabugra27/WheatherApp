package com.example.weatherapp.view

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.HavaDurumuViewModel
import kotlinx.coroutines.cancel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel:HavaDurumuViewModel by viewModels()

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.nemOraniResmi.setImageResource(R.drawable.humidity)
        binding.ruzgarHiziResmi.setImageResource(R.drawable.windss)
        binding.yagmurOraniResmi.setImageResource(R.drawable.watersplash)
        binding.havaDurumuImage.setImageResource(R.drawable.sun)

        binding.aramaCubugu.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query:String?):Boolean {
                val city = query?.trim()

                if(!city.isNullOrEmpty()) {
                    val apiKey = "3423ee3e77781176e013300f6892582b"
                    val units = "metric"
                    viewModel.verileriAl(city, apiKey, units)
                } else {
                    Toast.makeText(this@MainActivity, "Şehir adı girmeniz gereklidir!", Toast.LENGTH_LONG)
                        .show()
                }
                return true
            }

            override fun onQueryTextChange(newText:String?):Boolean {
                return false
            }

        })
        observeLiveData()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.havaDurumu.removeObservers(this)
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.cancel()
    }

    private fun observeLiveData() {
        viewModel.havaDurumu.observe(this, Observer { havaDurumu ->
            havaDurumu?.let {

                val aciklama = when(havaDurumu.weather[0].description) {
                    "clear sky"            -> "Hava Açık"
                    "few clouds"           -> "Parçalı Bulutlu"
                    "scattered clouds"     -> "Dağınık Bulutlu"
                    "broken  clouds"       -> "Kırık Bulutlu"
                    "overcast   clouds"    -> "Kapalı Bulutlar"
                    "shower rain"          -> "Sağanak Yağmur"
                    "light rain"           -> "Hafif Yağmur"
                    "moderate rain"        -> "Orta Düzeyde Yağmur"
                    "heavy intensity rain" -> "Sağanak Yağmur"
                    "very heavy rain"      -> "Şiddetli Sağanak Yağmur"
                    "extreme rain"         -> "Aşırı Şiddetli Sağanak Yağmur"
                    "freezing rain"        -> "Dondurucu Yağmur"
                    "very heavy rain"      -> "Şiddetli Sağanak Yağmur"
                    "thunderstorm"         -> "Şimşek"
                    "snow"                 -> "Karlı"
                    "sleet"                -> "Sulu Kar"
                    "mist"                 -> "Sisli"
                    "fog"                  -> "Sisli"
                    else                   -> havaDurumu.weather[0].description

                }

                binding.sehirIsmi.text = havaDurumu.name
                binding.derece.text = "${havaDurumu.main.temp.toInt()}°C"
                binding.aciklama.text = aciklama
                binding.nemOrani.text = "%${havaDurumu.main.humidity}"
                binding.ruzgarHizi.text = havaDurumu.wind.speed.toString()
                val yagmurIhtimali = havaDurumu.rain?.`1h` ?: 0
                binding.yagmurOrani.text = "${yagmurIhtimali} mm"
                binding.tarih.text = tarihAl()


                when(havaDurumu.weather[0].main) {
                    "Clear"        -> binding.havaDurumuImage.setImageResource(R.drawable.sun)
                    "Rain"         -> binding.havaDurumuImage.setImageResource(R.drawable.lightrain)
                    "Clouds"       -> binding.havaDurumuImage.setImageResource(R.drawable.cloudyday)
                    "Snow"         -> binding.havaDurumuImage.setImageResource(R.drawable.snowflake)
                    "Thunderstorm" -> binding.havaDurumuImage.setImageResource(R.drawable.storm)
                    "Fog"          -> binding.havaDurumuImage.setImageResource(R.drawable.fog)
                    "Mist"         -> binding.havaDurumuImage.setImageResource(R.drawable.fog)
                }
            }
        })
    }

    private fun tarihAl():String {
        val formatter = DateTimeFormatter.ofPattern("d MMMM EEEE", Locale("tr"))
        val formattedDate = LocalDate.now()
            .format(formatter)
        return formattedDate
    }

}