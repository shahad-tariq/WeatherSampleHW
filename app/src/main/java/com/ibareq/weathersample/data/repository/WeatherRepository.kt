package com.ibareq.weathersample.data.repository

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.network.Client
import io.reactivex.rxjava3.core.Observable
import com.ibareq.weathersample.data.response.WeatherResponse

object WeatherRepository {
    fun getWeatherForCity(cityName: String): Observable<Status<WeatherResponse>> {
        return Observable.create { emitter ->
            emitter.onNext(Status.Loading)
            emitter.onNext(Client.getWeatherForCity(cityName))
        }

    }
}