package com.example.michellemedina.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.michellemedina.bakingapp.MyApplication;
import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.data.Ingredient;

import java.util.List;

public class BakingWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new com.example.michellemedina.bakingapp.widget.RemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int appWidgetId;
    private MyApplication myApplication;

    public RemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        myApplication = ((MyApplication) context.getApplicationContext());
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        //not being used
    }

    @Override
    public void onDataSetChanged() {
       //not being used
    }

    @Override
    public void onDestroy() {
        //not being used
    }

    @Override
    public int getCount() {
        List<Dessert> desserts = myApplication.getWidgetDesserts();
        if (desserts.isEmpty()){
            return 0;
        }
        Dessert dessert = desserts.get(myApplication.getWidgetCurrentDessertIndex());
        return dessert.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        List<Dessert> desserts = myApplication.getWidgetDesserts();
        Dessert dessert = desserts.get(myApplication.getWidgetCurrentDessertIndex());
        Ingredient ingredient = dessert.getIngredients().get(position);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_layout);
        remoteViews.setTextViewText(R.id.ingredient_quantity, Double.toString(ingredient.getQuantity()));
        remoteViews.setTextViewText(R.id.ingredient_measure, ingredient.getMeasure());
        remoteViews.setTextViewText(R.id.ingredient_title, ingredient.getIngredient());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        //not being used
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
