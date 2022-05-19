package com.example.weatherforecast;

import android.location.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("v1/environment/2-hour-weather-forecast")
    Call<ApiData> getApiData();
}
