package com.ibareq.weathersample.data.network

import com.google.gson.Gson
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.response.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    private val okHttpClient = OkHttpClient()
    private val baseUrl = "https://www.metaweather.com/api/"
    private val gson = Gson()

    fun getWeatherForCity(cityName: String): Status<WeatherResponse>{
        val request = Request.Builder().url("${baseUrl}location/44418/").build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful){
            val weatherResponse = gson.fromJson(response.body!!.string(), WeatherResponse::class.java)
            Status.Success(weatherResponse)
        } else {
            Status.Error(response.message)
        }
    }
}