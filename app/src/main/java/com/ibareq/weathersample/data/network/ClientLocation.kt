package com.ibareq.weathersample.data.network

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.LocationResponse
import com.ibareq.weathersample.data.response.WeatherResponse
import okhttp3.Request

object ClientLocation : BaseClientResp() {

    fun getLocationResponse(cityName: String): Status<LocationResponse> {
        val request = Request
            .Builder()
            .url("${baseUrl}location/search/?query=$cityName")
            .build()

        val response = okHttpClient
            .newCall(request)
            .execute()

        return checkClient<LocationResponse>(response)
    }

}