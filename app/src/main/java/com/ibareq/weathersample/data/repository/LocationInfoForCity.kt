package com.ibareq.weathersample.data.repository

import android.util.Log
import com.ibareq.weathersample.Const.TAG
import com.ibareq.weathersample.data.Status
import com.ibareq.weathersample.data.network.ClientLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion

object LocationInfoForCity {

    fun getLocationInfo(cityName: String) = flow{
        emit(Status.Loading)
        emit(ClientLocation.getLocationResponse(cityName))
    }
        .onCompletion { Log.i(TAG , "Done getLocationInfo") }
        .flowOn(Dispatchers.IO)
        .conflate()
}