package com.ibareq.weathersample.data.network

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse

object ClientWeatherForCity : BaseClient() {

    fun getWeatherForCity(cityId: Int): Status<WeatherResponse> {
         return checkClient<WeatherResponse>(getResponse("${baseUrl}location/$cityId/"))
    }
}