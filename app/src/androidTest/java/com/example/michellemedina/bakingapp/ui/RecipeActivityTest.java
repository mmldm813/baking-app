package com.example.michellemedina.bakingapp.ui;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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

import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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

    public static void tapRecyclerViewItem(int recyclerViewId, int position) {
        onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(position));
        onView(withRecyclerView(recyclerViewId).atPosition(position)).perform(click());
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

        onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withRecyclerView(R.id.recycler).atPosition(3))
                .check(matches(hasDescendant(withText("Cheesecake"))));

    }


    @After
    public void tearDown() throws Exception {
        webServer.shutdown();
    }
}
