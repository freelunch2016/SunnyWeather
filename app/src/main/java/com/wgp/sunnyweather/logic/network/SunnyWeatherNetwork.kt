package com.wgp.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *统一的 网络数据源访问入口，
 */
object SunnyWeatherNetwork {

    //创建service接口对应的Retrofit对象
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    //创建请求
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()


    //创建service接口对应的Retrofit的代理对象
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    //调用接口中的方法发出去网络请求 获取实时天气
    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    //获取 未来几天的天气
    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()


    //给call 定义一个扩展函数await
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null!"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })

        }
    }

}






