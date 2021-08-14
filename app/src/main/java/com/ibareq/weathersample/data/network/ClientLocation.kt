package com.ibareq.weathersample.data.network

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.LocationResponse
import com.ibareq.weathersample.data.response.WeatherResponse
import okhttp3.Request

object ClientLocation : BaseClient() {

    fun getLocationResponse(cityName: String): Status<LocationResponse> {
        return checkClient<LocationResponse>(getResponse("${baseUrl}location/search/?query=$cityName"))
    }
}