package com.ibareq.weathersample.ui.activity

import android.view.LayoutInflater
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse
import com.ibareq.weathersample.databinding.ActivityMainBinding
import com.ibareq.weathersample.presenter.MainPresenter
import com.ibareq.weathersample.ui.Interface.IMain
import com.ibareq.weathersample.ui.hide
import com.ibareq.weathersample.ui.show
import kotlin.math.roundToInt

class MainActivity : BaseActivity<ActivityMainBinding>(), IMain {

    private lateinit var presenter: MainPresenter

    override val LOG_TAG: String
        get() = "MAIN_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        presenter = MainPresenter()
        presenter.mainView = this
    }

    override fun addCallbacks() {
        binding!!.apply {
            buttonSearch.setOnClickListener {
                presenter.getWeatherForCity(inputCityName.text.toString())
            }
        }
    }

    override fun onWeatherResult(response: Status<WeatherResponse>){
        hideAllViews()

        when(response){
            is Status.Error -> binding!!.imageError.show()
            is Status.Loading -> binding!!.progressLoading.show()
            is Status.Success -> bindData(response.data)
        }
    }

    private fun bindData(data: WeatherResponse) =
        binding!!.textMaxTemp.run {
            show()
            val maxTemp = data.consolidatedWeather[0].maxTemp.roundToInt().toString()
            val cityName = data.title
            text = "$cityName: $maxTemp"
        }

    private fun hideAllViews() =
        binding!!.run {
            progressLoading.hide()
            textMaxTemp.hide()
            imageError.hide()
        }
}
