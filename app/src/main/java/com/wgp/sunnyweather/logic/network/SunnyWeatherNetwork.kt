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






