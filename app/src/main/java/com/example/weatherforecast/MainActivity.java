package com.example.weatherforecast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    TextView textView, lastUpdatedTextView, coordTextView;
    ImageView imageView;
    double currLat, currLong;

    // List of AreaData containing info abt area name, lat, long, and current forecast
    // update list using sync()
    ArrayList<AreaData> areaDataList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        lastUpdatedTextView = findViewById(R.id.lastUpdatedTextView);
        textView = findViewById(R.id.textView);
        coordTextView = findViewById(R.id.coordTextView);


        // Initialize fusedLocationProviderClient for getLocation()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        sync();
    }

    // get currLat and currLong
    private void getLocation() {
        // when permission denied
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            coordTextView.setText("Permission Denied");
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // initialize location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        // Initialize geoCoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        // Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        // Set latitude on coordTextView
                        currLat = addresses.get(0).getLatitude();
                        currLong = addresses.get(0).getLongitude();
                        String content = new String();
                        content += currLat + ", " + currLong;
                        coordTextView.setText(content);
                    } catch (IOException e) {
                        coordTextView.setText("Error");
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Sync refreshes API as well as current location
    public void sync(View view) {
        sync();
    }

    public void sync() {
        // update currLat and currLong
        getLocation();

        Map<String, String> iconHashMap = new IconHashMap().getIconHashMap();
        RecyclerView areaForecastList = findViewById(R.id.recyclerView);
        AreasForecastRecyclerAdapter adapter = new AreasForecastRecyclerAdapter();
        areaForecastList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        areaForecastList.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.data.gov.sg/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        // convert JSON to Java class ApiData
        Call<ApiData> call = apiInterface.getApiData();

        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if (!response.isSuccessful()) {
                    // if not successful, return response code
                    textView.setText("Code: " + response.code());
                    return;
                }
                ApiData apiData = response.body();

                // loop to update areaDataList
                areaDataList = new ArrayList<AreaData>();
                int numOfAreas = apiData.getAreaMetadata().size(); // number of areas in SG
                for (int i = 0; i < numOfAreas; i++) {
                    ApiData.AreaMetadata currAreaMetadata = apiData.getAreaMetadata().get(i);
                    String currAreaName = currAreaMetadata.getName();
                    ApiData.AreaMetadata.LatLong currLatLong = currAreaMetadata.getLatLong();
                    double currAreaLat = currLatLong.getLatitude();
                    double currAreaLong = currLatLong.getLongitude();
                    String currAreaForecast = apiData.getForecastItems().get(0).getForecasts().get(i).getForecast();
                    String currAreaIcon = iconHashMap.get(currAreaForecast);
                    AreaData currAreaData = new AreaData(currAreaName, currAreaLat,
                            currAreaLong, currAreaForecast, currAreaIcon);
                    areaDataList.add(currAreaData);
                }

                adapter.updateItems(areaDataList);

                String updateTimestamp = apiData.getForecastItems().get(0).getUpdateTimestamp();
                String updateTimeString = updateTimestamp.substring(0, 10) + " ";
                updateTimeString += updateTimestamp.substring(11, 19);
                lastUpdatedTextView.setText(updateTimeString);

                // index of nearest area in List<AreaData>
                int nearestAreaIndex = 0;

                // function to calculate squared distance
                class Helper {
                    public double getSquaredDistanceFromIndex(int index) {
                        // scale to prevent rounding-off
                        double scale = 1000;
                        return Math.pow((areaDataList.get(index).getLatitude() * scale - currLat * scale), 2) +
                                Math.pow((areaDataList.get(index).getLongitude() * scale - currLong * scale), 2);
                    }
                }

                // find the nearest area by looping through the list
                Helper helper = new Helper();
                double minSquaredDistance = helper.getSquaredDistanceFromIndex(0);
                for (int i = 1; i < numOfAreas; i++) {
                    double currSquaredDistance = helper.getSquaredDistanceFromIndex(i);
                    if (currSquaredDistance < minSquaredDistance) {
                        nearestAreaIndex = i;
                    }
                }

                Map<String, String> iconHashMap = new IconHashMap().getIconHashMap();
                Map<String, String> imageHashMap = new IconHashMap().getImageHashMap();

                // set text here
                String content = "";
                AreaData shownArea = areaDataList.get(nearestAreaIndex);
                content += shownArea.getName() + "\n";
                content += shownArea.getForecast() + " " + iconHashMap.get(shownArea.getForecast()) + "\n";
                content += shownArea.getLatitude() + ", " + shownArea.getLongitude();
                textView.setText(content);

                try {
                    imageView.setImageResource(getResources().getIdentifier(imageHashMap.get(shownArea.getForecast()),
                            "mipmap", getPackageName()));
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                // get error message if there is error
                textView.setText(t.getMessage());
            }
        });


    }
}

