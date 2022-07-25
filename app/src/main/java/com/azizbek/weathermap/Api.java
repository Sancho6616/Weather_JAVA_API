package com.azizbek.weathermap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    @Headers({
            "X-RapidAPI-Key: 9a175b37a3mshd609112702de745p1d00eejsn6fb33262c025",
            "X-RapidAPI-Host: community-open-weather-map.p.rapidapi.com"
    })


    @GET("weather")
    Call<MyWeather> getMyCityWeather(@Query("q")String cityname);
}
