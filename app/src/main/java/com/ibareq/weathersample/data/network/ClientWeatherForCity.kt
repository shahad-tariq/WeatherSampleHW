package com.ibareq.weathersample.data.network

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse
import okhttp3.Request

object ClientWeatherForCity : BaseClientResp() {
    fun getWeatherForCity(cityId: Int): Status<WeatherResponse> {
         return checkClient<WeatherResponse>(getResponse("${baseUrl}location/$cityId/"))
    }
}