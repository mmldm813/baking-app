package com.example.michellemedina.bakingapp.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String EXTRA_DESSERT = "dessert";

    private Dessert dessert;

    public static void startWith(Context context, Dessert dessert) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_DESSERT, dessert);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDessertFromExtra();
        setContentView(R.layout.activity_recipe_detail);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.recipe_detail_fragment_container,  RecipeDetailFragment.newInstance(dessert))
                .commit();
    }

    private void getDessertFromExtra() {
        Intent intent = getIntent();
        dessert = (Dessert) intent.getSerializableExtra(EXTRA_DESSERT);
    }

    @Override
    public void onBackPressed() {
        // since we don't add to the backstack, pressing the back button ends up
        // leaving a fragment overlapping the prev fragment.
        // this works around the problem by just intercepting the back button and finishing the
        // activity
        finish();
    }
}
