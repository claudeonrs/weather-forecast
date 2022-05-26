package com.example.weatherforecast;

import java.util.Comparator;

// class for storing information of each area
public class AreaData {
    private double latitude, longitude;
    private String name, forecast, icon;

    public AreaData(String name, double latitude, double longitude, String forecast, String icon) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.forecast = forecast;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getForecast() {
        return forecast;
    }

    public String getIcon() {
        return icon;
    }

}

