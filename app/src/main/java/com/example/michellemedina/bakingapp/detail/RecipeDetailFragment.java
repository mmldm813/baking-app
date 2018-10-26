package com.example.michellemedina.bakingapp.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.data.Ingredient;
import com.example.michellemedina.bakingapp.data.Step;

public class RecipeDetailFragment extends Fragment {
    private Dessert dessert;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dessert = ((RecipeDetailActivity) getActivity()).getDessert();
        View view = inflater.inflate(R.layout.recipe_detail_layout, container, false);
        addIngredients(view);
        addSteps(view);
        return view;
    }

    private void addIngredients(View view) {
        LinearLayout ingredientLayout = view.findViewById(R.id.ingredients);
        for (Ingredient ingredient : dessert.getIngredients()) {
            View ingredientView = getLayoutInflater().inflate(R.layout.ingredient_layout
                    ,null, false);
            TextView quantity = ingredientView.findViewById(R.id.ingredient_quantity);
            quantity.setText(Double.toString(ingredient.getQuantity()));
            TextView measure = ingredientView.findViewById(R.id.ingredient_measure);
            measure.setText(ingredient.getMeasure());
            TextView ingredientName = ingredientView.findViewById(R.id.ingredient_title);
            ingredientName.setText(ingredient.getIngredient());
            ingredientLayout.addView(ingredientView);
        }
    }

    private void addSteps(final View view) {
        LinearLayout stepsLayout = view.findViewById(R.id.instructions);
        for (Step step : dessert.getSteps()) {
            View instructionView = getLayoutInflater().inflate(R.layout.instructions_layout
            ,null, false);
            TextView stepId = instructionView.findViewById(R.id.step_id);
            stepId.setText(Integer.toString(step.getStepId()));
            Button shortDescription = instructionView.findViewById(R.id.short_description);
            shortDescription.setText(step.getShortDescription());
            stepsLayout.addView(instructionView);
        }
    }



}
