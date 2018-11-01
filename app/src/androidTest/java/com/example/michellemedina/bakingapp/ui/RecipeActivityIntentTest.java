package com.example.michellemedina.bakingapp.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;

import com.example.michellemedina.bakingapp.DataHelper;
import com.example.michellemedina.bakingapp.MockServerDispatcher;
import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.RecyclerViewMatcher;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.main.RecipeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityIntentTest {

    private String fakeData;

    @Rule
    public IntentsTestRule<RecipeActivity> intentsTestRule =
            new IntentsTestRule<>(RecipeActivity.class, false, false);

    private MockWebServer webServer;

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void setup() throws Exception {
        fakeData = DataHelper.readFakeData();

        webServer = new MockWebServer();
        webServer.start(8080);
    }

    @Test
    public void checkOnClickLoadsDetailFragment() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        intentsTestRule.launchActivity(new Intent());

        intending((isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

        onView(withRecyclerView(R.id.recycler).atPosition(0)).perform(click());

        Intent receivedIntent = Iterables.getOnlyElement(Intents.getIntents());
        assertEquals("com.example.michellemedina.bakingapp.detail.RecipeDetailActivity",
                receivedIntent.getComponent().getClassName());

        Bundle extras = receivedIntent.getExtras();
        Dessert dessert = (Dessert) extras.getSerializable("dessert");

        assertEquals("Nutella Pie", dessert.getDessertName());
    }
}
