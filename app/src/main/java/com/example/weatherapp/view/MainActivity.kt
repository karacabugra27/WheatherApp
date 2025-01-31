package com.example.weatherapp.view

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.viewmodel.HavaDurumuViewModel
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
                val city = newText?.trim()

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

        })
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.havaDurumu.observe(this, Observer { havaDurumu ->
            havaDurumu?.let {
                binding.sehirIsmi.text = havaDurumu.name
                binding.derece.text = "${havaDurumu.main.temp}°C"
                binding.aciklama.text = havaDurumu.weather[0].description
                binding.nemOrani.text = "%${havaDurumu.main.humidity}"
                binding.ruzgarHizi.text = havaDurumu.wind.speed.toString()
                binding.yagmurOrani.text = "%${havaDurumu.rain?.`1h`}"
                binding.tarih.text = tarihAl()

                when(havaDurumu.weather[0].main) {
                    "Clear"        -> binding.havaDurumuImage.setImageResource(R.drawable.sun)
                    "Rain"         -> binding.havaDurumuImage.setImageResource(R.drawable.lightrain)
                    "Clouds"       -> binding.havaDurumuImage.setImageResource(R.drawable.cloudyday)
                    "Snow"         -> binding.havaDurumuImage.setImageResource(R.drawable.snowflake)
                    "Thunderstorm" -> binding.havaDurumuImage.setImageResource(R.drawable.storm)
                    "Fog"          -> binding.havaDurumuImage.setImageResource(R.drawable.fog)
                    "Snow"         -> binding.havaDurumuImage.setImageResource(R.drawable.snowflake)
                    else           -> binding.havaDurumuImage.setImageResource(R.drawable.sun)

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