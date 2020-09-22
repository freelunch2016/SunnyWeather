package com.wgp.sunnyweather.logic

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wgp.sunnyweather.logic.dao.PlaceDao
import com.wgp.sunnyweather.logic.model.Place
import com.wgp.sunnyweather.logic.model.Weather
import com.wgp.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * 仓库层，上层的所有获取数据的请求，都会先走这里，
 * 这里来做是重网络请求还是去本地获取数据
 */
object Reposistory {

//    fun searchPlaces(query: String) = liveData<Result<List<Place>>>(Dispatchers.IO) {
//        val result = try {
//            val searchPlaces = SunnyWeatherNetwork.searchPlaces(query)
//            if (searchPlaces.status == "ok") {
//                val place = searchPlaces.places
//                Result.success(place)
//            } else {
//                Result.failure(RuntimeException("Response status is ${searchPlaces.status}"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//        emit(result)
//    }

    fun searchPlaces(query: String) = fireEx {
        val searchPlaces = SunnyWeatherNetwork.searchPlaces(query)
        if (searchPlaces.status == "ok") {
            val place = searchPlaces.places
            Result.success(place)
        } else {
            Result.failure(RuntimeException("Response status is ${searchPlaces.status}"))
        }
    }


    fun refreshWeather(lng: String, lat: String) = liveData<Result<Weather>>(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }

                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()

                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather =
                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                } else {
                    Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status},daily response status is ${dailyResponse.status}"))
                }
            }

        } catch (e: Exception) {
            Result.failure<Weather>(e)
        }

        emit(result)

    }


    //定义捕获异常的方法
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    private fun <T> fireEx(block: suspend () -> Result<T>) = liveData<Result<T>>(Dispatchers.IO) {
        val result: Result<T> = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }


     fun savePlace(place: Place){
         PlaceDao.savePlace(place)
    }


    fun getSavedPlace():Place{
       return PlaceDao.getSavedPlace()
    }


    fun isPlaceSaved()= PlaceDao.isPlaceSaved()

}