package com.example.michellemedina.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.michellemedina.bakingapp.api.DessertClient;
import com.example.michellemedina.bakingapp.data.Dessert;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecipeActivity extends AppCompatActivity {

    private static final String TAG = RecipeActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private DessertCardAdapter adapter;
    private List<Dessert> desserts = new ArrayList<>();

    @Inject Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDagger();
        setupCardView();
    }

    private void setupDagger() {
        ((MyApplication)getApplication()).getComponent().inject(this);
    }

    public void setupCardView(){
        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager cardList = new LinearLayoutManager(this);
        cardList.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(cardList);
        adapter = new DessertCardAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void getDesserts() {
        DessertClient client = retrofit.create(DessertClient.class);
        Call<List<Dessert>> call = client.getDesserts();

        call.enqueue(new Callback<List<Dessert>>() {
            @Override
            public void onResponse(Call<List<Dessert>> call, Response<List<Dessert>> response) {
                desserts = response.body();
                adapter.setDesserts(desserts);
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
