package com.example.michellemedina.bakingapp;

import com.example.michellemedina.bakingapp.dagger.ApplicationComponent;
import com.example.michellemedina.bakingapp.dagger.DaggerApplicationComponent;

public class UiTestApp extends MyApplication {

    @Override
    public ApplicationComponent getComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new TestApplicationModule(this))
                .build();
    }
}
