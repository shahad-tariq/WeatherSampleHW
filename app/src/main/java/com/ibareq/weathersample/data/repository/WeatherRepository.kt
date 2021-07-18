package com.ibareq.weathersample.data.repository

import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.network.Client
import io.reactivex.rxjava3.core.Observable
import com.ibareq.weathersample.data.response.WeatherResponse
import com.ibareq.weathersample.data.response.LocationResponse


object WeatherRepository {
    fun getWeatherForCity(cityName: String): Observable<Status<WeatherResponse>> {
        return getLocationInfo(cityName).flatMap {
            when(it){
                is Status.Error -> {
                    Observable.create{ emitter ->
                        emitter.onNext(it)
                        emitter.onComplete()
                    }
                }
                is Status.Loading -> {
                    Observable.create{ emitter ->
                        emitter.onNext(it)
                        emitter.onComplete()
                    }
                }
                is Status.Success -> {
                    Observable.create { emitter ->
                        if (it.data.isEmpty()){
                            emitter.onNext(Status.Error("city not found"))
                        } else {
                            emitter.onNext(Client.getWeatherForCity(it.data[0].cityId)) //to make it easier we pick the first city and skip others
                        }
                        emitter.onComplete()
                    }
                }
            }
        }

    }

    fun getLocationInfo(cityName: String): Observable<Status<LocationResponse>> {
        return Observable.create { emitter ->
            emitter.onNext(Status.Loading)
            emitter.onNext(Client.getLocationResponse(cityName))
            emitter.onComplete()
        }
    }

}