package com.example.michellemedina.bakingapp;

import android.app.Application;

import com.example.michellemedina.bakingapp.dagger.ApplicationComponent;
import com.example.michellemedina.bakingapp.dagger.ApplicationModule;
import com.example.michellemedina.bakingapp.dagger.DaggerApplicationComponent;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
