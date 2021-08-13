package com.ibareq.weathersample.presenter
import android.util.Log
import com.ibareq.weathersample.Const.TAG
import com.ibareq.weathersample.data.repository.WeatherRepository
import com.ibareq.weathersample.ui.Interface.IMain
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class MainPresenter : CoroutineScope   {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    lateinit var mainView:IMain

    fun getWeatherForCity(cityName: String) =
        launch{
                WeatherRepository
                            .getWeatherForCity(cityName)
                            .catch { e -> Log.i(TAG, "${e.message}") }
                            .collect { mainView.onWeatherResult(it)}
        }

}