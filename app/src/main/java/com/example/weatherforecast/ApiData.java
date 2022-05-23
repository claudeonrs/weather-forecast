package com.example.weatherforecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiData {
    /* {
        "area_metadata": List<Area>
        "items": [{
               "update_timestamp": ...
               "timestamp": ...
               "valid_period": {start: ...
                                end: ...}
               "forecasts": List<Forecast>
        }]
       }
     */
    @SerializedName("area_metadata")
    private List<AreaMetadata> areaMetadata;

    @SerializedName("items")
    private List<ForecastItem> forecastItems;

    public List<AreaMetadata> getAreaMetadata() {
        return areaMetadata;
    }

    public List<ForecastItem> getForecastItems() {
        return forecastItems;
    }


    // Nested class AreaMetadata
    public class AreaMetadata {
        private String name;
        @SerializedName("label_location")
        private LatLong latLong;

        public AreaMetadata(String name, LatLong latLong) {
            this.name = name;
            this.latLong = latLong;
        }

        public String getName() {
            return name;
        }

        public LatLong getLatLong() {
            return latLong;
        }

        // Nested class LatLong
        public class LatLong {
            private double latitude;
            private double longitude;

            public LatLong(double latitude, double longitude) {
                this.latitude = latitude;
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public double getLongitude() {
                return longitude;
            }
        }
    }



    //Nested class ForecastItem
    public class ForecastItem {

        @SerializedName("update_timestamp")
        private String updateTimestamp;

        @SerializedName("timestamp")
        private String timestamp;

        private List<AreaForecast> forecasts;

        public ForecastItem(String updateTimestamp, String timestamp, List<AreaForecast> forecasts) {
            this.updateTimestamp = updateTimestamp;
            this.timestamp = timestamp;
            this.forecasts = forecasts;
        }

        public String getUpdateTimestamp() {
            return updateTimestamp;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public List<AreaForecast> getForecasts() {
            return forecasts;
        }

        // Nested class AreaForecast

        public class AreaForecast {
            private String name;
            private String forecast;

            public AreaForecast(String name, String forecast) {
                this.name = name;
                this.forecast = forecast;
            }

            public String getName() {
                return name;
            }

            public String getForecast() {
                return forecast;
            }
        }

    }




}
