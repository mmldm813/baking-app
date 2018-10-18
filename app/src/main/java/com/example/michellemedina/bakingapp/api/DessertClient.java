package com.example.michellemedina.bakingapp.api;

import com.example.michellemedina.bakingapp.data.Dessert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DessertClient {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Dessert>> getDesserts();
}
