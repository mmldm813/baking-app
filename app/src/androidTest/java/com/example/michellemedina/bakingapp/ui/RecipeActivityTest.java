package com.example.michellemedina.bakingapp.ui;

import android.content.Intent;


import com.example.michellemedina.bakingapp.DataHelper;
import com.example.michellemedina.bakingapp.MockServerDispatcher;
import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.RecyclerViewMatcher;
import com.example.michellemedina.bakingapp.main.RecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    private String fakeData;

    @Rule
    public ActivityTestRule<RecipeActivity> activityRule =
            new ActivityTestRule<>(RecipeActivity.class, false, false);

    private MockWebServer webServer;

    @Before
    public void setup() throws Exception {
        fakeData = DataHelper.readFakeData();

        webServer = new MockWebServer();
        webServer.start(8080);
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void checkFirstRecipe() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityRule.launchActivity(new Intent());

        onView(withRecyclerView(R.id.recycler).atPosition(0))
                .check(matches(hasDescendant(withText("Nutella Pie"))));
    }

    @Test
    public void checkLastRecipe() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityRule.launchActivity(new Intent());

        onView(withRecyclerView(R.id.recycler).atPosition(3))
                .check(matches(hasDescendant(withText("Cheesecake"))));
    }

    @After
    public void tearDown() throws Exception {
        webServer.shutdown();
    }
}
