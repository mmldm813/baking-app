package com.example.michellemedina.bakingapp;

import android.app.Application;

import com.example.michellemedina.bakingapp.dagger.ApplicationComponent;
import com.example.michellemedina.bakingapp.dagger.ApplicationModule;
import com.example.michellemedina.bakingapp.dagger.DaggerApplicationComponent;
import com.example.michellemedina.bakingapp.data.Dessert;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<Dessert> widgetDesserts = new ArrayList<>();
    private int widgetCurrentDessertIndex = 0;

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

    public List<Dessert> getWidgetDesserts() {
        return widgetDesserts;
    }

    public void setWidgetDesserts(List<Dessert> widgetDesserts) {
        this.widgetDesserts = widgetDesserts;
    }

    public int getWidgetCurrentDessertIndex() {
        return widgetCurrentDessertIndex;
    }

    public void setWidgetCurrentDessertIndex(int widgetCurrentDessertIndex) {
        this.widgetCurrentDessertIndex = widgetCurrentDessertIndex;
    }
}
