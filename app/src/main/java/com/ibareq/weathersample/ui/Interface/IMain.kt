package com.ibareq.weathersample.ui.Interface

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse

interface IMain {
    fun onWeatherResult(response: Status<WeatherResponse>)
}