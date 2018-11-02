package com.example.michellemedina.bakingapp.ui;

import android.content.Intent;

import com.example.michellemedina.bakingapp.DataHelper;
import com.example.michellemedina.bakingapp.MockServerDispatcher;
import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.detail.RecipeDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import androidx.test.rule.ActivityTestRule;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class StepDetailFragmentTest {

    private List<Dessert> desserts;
    private String fakeData;

    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class, false, false);

    private MockWebServer webServer;

    @Before
    public void setup() throws Exception {
        fakeData = DataHelper.readFakeData();

        webServer = new MockWebServer();
        webServer.start(8080);

        fakeData = DataHelper.readFakeData();
        TypeToken<List<Dessert>> token = new TypeToken<List<Dessert>>() {
        };
        Gson gson = new Gson();
        desserts = gson.fromJson(fakeData, token.getType());
    }

    @Test
    public void checkPreviousButtonDisabled() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityTestRule.launchActivity(new Intent().putExtra("dessert", desserts.get(0)));

        onView(allOf(withId(R.id.short_description), withText("Recipe Introduction"))).perform(scrollTo()).perform(click());
        onView(withId(R.id.previous_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void checkNextButtonDisabled() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityTestRule.launchActivity(new Intent().putExtra("dessert", desserts.get(0)));

        onView(allOf(withId(R.id.short_description), withText("Finishing Steps"))).perform(scrollTo()).perform(click());
        onView(withId(R.id.next_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void checkPreviousButtonNavigatesToPreviousStep() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityTestRule.launchActivity(new Intent().putExtra("dessert", desserts.get(0)));

        onView(allOf(withId(R.id.short_description), withText("Starting prep"))).perform(scrollTo()).perform(click());
        onView(withId(R.id.previous_button)).perform(click());
        onView(withId(R.id.step_long_description)).check(matches(withText("Recipe Introduction")));
    }

    @Test
    public void checkNextButtonNavigatesToNextStep() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityTestRule.launchActivity(new Intent().putExtra("dessert", desserts.get(0)));

        onView(allOf(withId(R.id.short_description), withText("Starting prep"))).perform(scrollTo()).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.step_long_description)).check(matches(withText("2. Whisk the graham " +
                "cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together " +
                "in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry " +
                "ingredients and stir together until evenly mixed.")));
    }

    @After
    public void tearDown() throws Exception {
        webServer.shutdown();
    }

}
