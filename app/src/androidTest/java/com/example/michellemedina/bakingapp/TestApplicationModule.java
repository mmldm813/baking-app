package com.example.michellemedina.bakingapp;

import android.app.Application;

import com.example.michellemedina.bakingapp.dagger.ApplicationModule;

import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestApplicationModule extends ApplicationModule {

    public TestApplicationModule(Application application) {
        super(application);
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient client) {
        return new retrofit2.Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
