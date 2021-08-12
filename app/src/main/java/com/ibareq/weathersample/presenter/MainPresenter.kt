package com.ibareq.weathersample.presenter
import android.util.Log
import com.ibareq.weathersample.data.repository.WeatherRepository
import com.ibareq.weathersample.ui.MainActivity
import com.ibareq.weathersample.ui.Interface.iMainView
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class MainPresenter : CoroutineScope   {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    lateinit var mainView:iMainView

    fun getWeatherForCity(cityName: String) =
        launch{
                WeatherRepository
                    .getWeatherForCity(cityName)
                    .catch { e -> Log.i(MainActivity.TAG, "${e.message}") }
                    .collect { Log.i("hhhh" , "hello $it")
                    mainView.onWeatherResult(it)}
        }

}