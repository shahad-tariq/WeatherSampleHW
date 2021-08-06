package com.ibareq.weathersample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse
import com.ibareq.weathersample.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import com.ibareq.weathersample.data.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSearch.setOnClickListener {
            getWeatherForCity(binding.inputCityName.text.toString())
        }
    }

    fun getWeatherForCity(cityName: String){
        lifecycleScope.launch{
            val result = withContext(Dispatchers.IO) {
                WeatherRepository.getWeatherForCity(cityName)
                    .catch { e -> Log.i(TAG, "${e.message}") }
            }
            result.collect(::onWeatherResult)
        }
    }

    private fun onWeatherResult(response: Status<WeatherResponse>){
        hideAllViews()
        when(response){
            is Status.Error -> binding.imageError.show()
            is Status.Loading ->  binding.progressLoading.show()
            is Status.Success -> bindData(response.data)
        }
    }

    private fun bindData(data: WeatherResponse){
        binding.textMaxTemp.run {
            show()
            val maxTemp = data.consolidatedWeather[0].maxTemp.roundToInt().toString()
            val cityName = data.title
            text = "$cityName: $maxTemp"
        }
    }

    private fun hideAllViews(){
        binding.run {
            progressLoading.hide()
            textMaxTemp.hide()
            imageError.hide()

        }
    }
    companion object{
        const val TAG = "BK_PROGRAMMER"
    }
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}