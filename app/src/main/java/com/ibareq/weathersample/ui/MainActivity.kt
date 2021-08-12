package com.ibareq.weathersample.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse
import com.ibareq.weathersample.databinding.ActivityMainBinding
import com.ibareq.weathersample.presenter.MainPresenter
import com.ibareq.weathersample.ui.Interface.iMainView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), iMainView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenter()
        presenter.mainView = this

        binding.buttonSearch.setOnClickListener {
             presenter.getWeatherForCity(binding.inputCityName.text.toString())
        }
    }

    override fun onWeatherResult(response: Status<WeatherResponse>){
        Log.i("hhhhhhhh" , response.toString())
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