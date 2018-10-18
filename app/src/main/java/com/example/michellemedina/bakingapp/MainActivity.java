package com.example.michellemedina.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.michellemedina.bakingapp.api.DessertClient;
import com.example.michellemedina.bakingapp.api.RetrofitHelper;
import com.example.michellemedina.bakingapp.data.Dessert;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Dessert> desserts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getDesserts() {
        DessertClient client = RetrofitHelper.getRetrofitInstance().create(DessertClient.class);
        Call<List<Dessert>> call = client.getDesserts();

        call.enqueue(new Callback<List<Dessert>>() {
            @Override
            public void onResponse(Call<List<Dessert>> call, Response<List<Dessert>> response) {
                desserts = response.body();
            }

            @Override
            public void onFailure(Call<List<Dessert>> call, Throwable t) {
                Log.e(TAG, "Network request failed.", t);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDesserts();
    }
}
