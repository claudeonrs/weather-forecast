package com.example.weatherforecast;

import java.util.HashMap;
import java.util.Map;

// HashMap to link forecast to emoji/icons
public class IconHashMap {
    private Map<String, String> iconHashMap = new HashMap<String, String>();

    private Map<String, String> imageHashMap = new HashMap<String, String>();

    public IconHashMap() {

        iconHashMap.put("Partly Cloudy (Night)", "☁");
        iconHashMap.put("Partly Cloudy (Day)", "\uD83C\uDF24");
        iconHashMap.put("Fair (Night)", "\uD83C\uDF19");
        iconHashMap.put("Fair (Day)", "☀");
        iconHashMap.put("Fair and Warm", "☀");
        iconHashMap.put("Cloudy", "☁");
        iconHashMap.put("Light Rain", "\uD83C\uDF26");
        iconHashMap.put("Showers", "\uD83C\uDF27");
        iconHashMap.put("Thundery Showers", "\u26C8");

        // imageHashMap
        imageHashMap.put("Partly Cloudy (Night)", "partly_cloudy_night");
        imageHashMap.put("Partly Cloudy (Day)", "partly_cloudy_day");
        imageHashMap.put("Fair (Night)", "fair_night");
        imageHashMap.put("Fair (Day)", "fair_day");
        imageHashMap.put("Fair and Warm", "fair_and_warm");
        imageHashMap.put("Cloudy", "cloudy");
        imageHashMap.put("Light Rain", "light_rain");
        imageHashMap.put("Showers", "showers");
        imageHashMap.put("Thundery Showers", "thundery_showers");
    }


    public Map<String, String> getIconHashMap() {
        return iconHashMap;
    }

    public Map<String, String> getImageHashMap() {
        return imageHashMap;
    }
}
