package com.ibareq.weathersample.data.network

import com.google.gson.Gson
import com.ibareq.weathersample.data.Status
import okhttp3.OkHttpClient
import okhttp3.Response
import java.lang.Exception

abstract class BaseClientResp {
    protected val okHttpClient = OkHttpClient()
    protected val baseUrl = "https://www.metaweather.com/api/"
    protected val gson = Gson()

    protected inline fun <reified T> checkClient(response: Response)
            : Status<T> =
        try{
            val getResponse = gson.fromJson(response.body!!.string(), T::class.java)
            Status.Success(getResponse)
        } catch (e: Exception) {
            Status.Error(response.message)
        }

}