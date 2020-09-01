package com.wgp.sunnyweather.logic

import androidx.lifecycle.liveData
import com.wgp.sunnyweather.logic.model.Place
import com.wgp.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

/**
 * 仓库层，上层的所有获取数据的请求，都会先走这里，
 * 这里来做是重网络请求还是去本地获取数据
 */
object Reposistory {

    fun searchPlaces(query: String) = liveData<Result<List<Place>>>(Dispatchers.IO) {
        val result = try {
            val searchPlaces = SunnyWeatherNetwork.searchPlaces(query)
            if (searchPlaces.status == "ok") {
                val place = searchPlaces.places
                Result.success(place)
            } else {
                Result.failure(RuntimeException("Response status is ${searchPlaces.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}