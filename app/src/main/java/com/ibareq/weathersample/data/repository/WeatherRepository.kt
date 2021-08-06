package com.ibareq.weathersample.data.repository

import android.util.Log
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.network.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


object WeatherRepository {
    fun getWeatherForCity(cityName: String)  = getLocationInfo(cityName).flatMapConcat {
            flow {
                when(it){
                    is Status.Error -> emit(it)
                    is Status.Loading -> emit(it)
                    is Status.Success ->
                        if (it.data.isEmpty())
                            emit(Status.Error("city not found"))
                        else
                            emit(Client.getWeatherForCity(it.data[0].cityId)) //to make it easier we pick the first city and skip others
                }
            }.onCompletion { Log.i("COMPLETE" , "Done1") }.flowOn(Dispatchers.IO).conflate()
        }

    private fun getLocationInfo(cityName: String) = flow{
                emit(Status.Loading)
                emit(Client.getLocationResponse(cityName))
    }.onCompletion { Log.i("COMPLETE" , "Done2") }.flowOn(Dispatchers.IO).conflate()

}