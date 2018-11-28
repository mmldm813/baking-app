package com.example.michellemedina.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.michellemedina.bakingapp.MyApplication;
import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.api.DessertClient;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.data.Ingredient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    private static final String TAG = BakingWidgetProvider.class.getSimpleName();
    public static String NEXT_DESSERT_ACTION = "nextDessert";
    public static String PREVIOUS_DESSERT_ACTION = "previousDessert";

    private Retrofit retrofit;

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        String dessertName = "---";

        MyApplication myApplication = ((MyApplication) context.getApplicationContext());
        List<Dessert> desserts = myApplication.getWidgetDesserts();
        int currentDessertIndex = myApplication.getWidgetCurrentDessertIndex();
        if (!desserts.isEmpty()) {
            dessertName = desserts.get(currentDessertIndex).getDessertName();
        }

        // Construct the RemoteViews object
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        remoteViews.setTextViewText(R.id.widget_dessert_name, dessertName);

        final Intent intent = new Intent(context, BakingWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_ingredients_list, intent);

        final Intent nextButtonClick = new Intent(context, BakingWidgetProvider.class);
        nextButtonClick.setAction(NEXT_DESSERT_ACTION);
        final PendingIntent refreshPendingIntentNext = PendingIntent.getBroadcast(context, 0,
                nextButtonClick, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_next_button, refreshPendingIntentNext);

        final Intent prevButtonClick = new Intent(context, BakingWidgetProvider.class);
        prevButtonClick.setAction(PREVIOUS_DESSERT_ACTION);
        final PendingIntent refreshPendingIntentPrev = PendingIntent.getBroadcast(context, 0,
                prevButtonClick, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_previous_button, refreshPendingIntentPrev);
        widgetNavigationSetup(context, remoteViews);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    private void widgetNavigationSetup(Context context, RemoteViews rmv) {
        MyApplication myApplication = ((MyApplication) context.getApplicationContext());
        int newIndex = myApplication.getWidgetCurrentDessertIndex();
        rmv.setBoolean(R.id.widget_previous_button, "setEnabled",
                newIndex == 0 ? false : true);

        rmv.setBoolean(R.id.widget_next_button, "setEnabled",
                newIndex == myApplication.getWidgetDesserts().size() - 1 ? false : true);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        setupRetrofitCall(context);
    }

    private void setupRetrofitCall(final Context context) {
        retrofit = ((MyApplication) context.getApplicationContext()).getComponent().getRetrofit();
        DessertClient client = retrofit.create(DessertClient.class);
        Call<List<Dessert>> call = client.getDesserts();

        call.enqueue(new Callback<List<Dessert>>() {
            @Override
            public void onResponse(Call<List<Dessert>> call, Response<List<Dessert>> response) {
                MyApplication myApplication = ((MyApplication) context.getApplicationContext());
                myApplication.setWidgetDesserts(response.body());

                updateWidget(context);
            }

            @Override
            public void onFailure(Call<List<Dessert>> call, Throwable t) {
                Log.e(TAG, "Network request failed.", t);
            }
        });
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(NEXT_DESSERT_ACTION) || action.equals(PREVIOUS_DESSERT_ACTION)) {
            MyApplication myApplication = ((MyApplication) context.getApplicationContext());
            int newIndex = myApplication.getWidgetCurrentDessertIndex();

            if (action.equals(NEXT_DESSERT_ACTION)) {
                newIndex++;
            } else if (action.equals(PREVIOUS_DESSERT_ACTION)) {
                newIndex--;
            }

            myApplication.setWidgetCurrentDessertIndex(newIndex);
            updateWidget(context);
        }

        super.onReceive(context, intent);
    }

    private void updateWidget(Context context) {
        MyApplication myApplication = ((MyApplication) context.getApplicationContext());

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        ComponentName cn = new ComponentName(context, BakingWidgetProvider.class);
        List<Dessert> desserts = myApplication.getWidgetDesserts();
        int newIndex = myApplication.getWidgetCurrentDessertIndex();

        mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_ingredients_list);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        remoteViews.setTextViewText(R.id.widget_dessert_name, desserts.get(newIndex).getDessertName());

        widgetNavigationSetup(context, remoteViews);
        mgr.updateAppWidget(mgr.getAppWidgetIds(cn), remoteViews);
    }
}

