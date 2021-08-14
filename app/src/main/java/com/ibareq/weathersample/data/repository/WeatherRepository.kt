package com.ibareq.weathersample.data.repository

import android.util.Log
import com.ibareq.weathersample.Const.TAG
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.network.ClientWeatherForCity
import com.ibareq.weathersample.data.response.LocationResponse
import com.ibareq.weathersample.data.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


object WeatherRepository {

    fun getWeatherForCity(cityName: String) =
        LocationInfoForCity
            .getLocationInfo(cityName)
            .flatMapConcat {
                    flow {
                        emit(checkStatusRepository(it))
                    }
                        .onCompletion { Log.i(TAG , "Done getWeatherForCity") }
                        .flowOn(Dispatchers.IO)
                        .conflate()
            }

    private fun checkStatusRepository(statusResp: Status<LocationResponse>) =
         when(statusResp){
             is Status.Error -> statusResp
             is Status.Loading -> statusResp
             is Status.Success -> checkSuccessfulRepository(statusResp)
         }

    private fun checkSuccessfulRepository(status: Status.Success<LocationResponse>)
    : Status<WeatherResponse> =
                if (status.data.isEmpty()) Status.Error("city not found")
                else ClientWeatherForCity.getWeatherForCity(status.data[0].cityId)
                //to make it easier we pick the first city and skip others

}


