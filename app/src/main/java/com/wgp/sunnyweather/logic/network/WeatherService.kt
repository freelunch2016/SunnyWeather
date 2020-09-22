package com.wgp.sunnyweather.logic.network

import com.wgp.sunnyweather.SunnyWeatherApplication
import com.wgp.sunnyweather.logic.model.DailyResponse
import com.wgp.sunnyweather.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 获取天气信息
 */
interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.token}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng:String,@Path("lat")lat:String):Call<RealTimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.token}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String,@Path("lat")lat: String):Call<DailyResponse>
}






