package com.example.michellemedina.bakingapp.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.michellemedina.bakingapp.DataHelper;
import com.example.michellemedina.bakingapp.MockServerDispatcher;
import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.detail.RecipeDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    private List<Dessert> desserts;
    private String fakeData;


    @Rule
    public ActivityTestRule<RecipeDetailActivity> activityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class, false, false);

    private MockWebServer webServer;

    public static Matcher<View> withLinearLayoutChildrenCount(final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(final View view) {
                if (view instanceof LinearLayout) {
                    return ((LinearLayout) view).getChildCount() == size;
                }
                return false;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("LinearLayout should have " + size + " items");
            }
        };
    }

    @Before
    public void setup() throws Exception {
        webServer = new MockWebServer();
        webServer.start(8080);

        fakeData = DataHelper.readFakeData();
        TypeToken<List<Dessert>> token = new TypeToken<List<Dessert>>() {
        };
        Gson gson = new Gson();
        desserts = gson.fromJson(fakeData, token.getType());

    }

    @Test
    public void checkTotalIngredientsFirstRecipe() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityTestRule.launchActivity(new Intent().putExtra("dessert", desserts.get(0)));

        onView(withId(R.id.ingredients)).check(matches(withLinearLayoutChildrenCount(9)));
    }

    @Test
    public void checkTotalStepsFirstRecipe() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityTestRule.launchActivity(new Intent().putExtra("dessert", desserts.get(0)));

        onView(withId(R.id.steps)).check(matches(withLinearLayoutChildrenCount(7)));
    }

    @Test
    public void checkStepDescription() {
        webServer.setDispatcher(new MockServerDispatcher(null, 200, fakeData).new RequestDispatcher());
        activityTestRule.launchActivity(new Intent().putExtra("dessert", desserts.get(0)));

        onView(allOf(withId(R.id.short_description), withText("Starting prep"))).perform(scrollTo()).perform(click());
        onView(withId(R.id.step_long_description)).check(matches(withText("1. Preheat the oven to 350°F. " +
                "Butter a 9\" deep dish pie pan.")));

    }
}
