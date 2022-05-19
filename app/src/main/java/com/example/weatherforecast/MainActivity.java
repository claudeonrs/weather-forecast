package com.example.weatherforecast;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }

    public void sync(View view) {
        TextView textView = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.data.gov.sg/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        // getForecast is just a placeholder name
        Call<ApiData> call = apiInterface.getApiData();

        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if (!response.isSuccessful()) {
                    // if not successful, return response code
                    textView.setText("Code: "+response.code());
                    return;
                }
                ApiData apiData = response.body();
                String content = "";
                content += apiData.getAreaMetadata().get(1).getName();

                textView.append(content);
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                // get error message
                textView.setText(t.getMessage());
            }
        });
    }
}

