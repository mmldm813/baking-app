package com.example.michellemedina.bakingapp.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.data.Ingredient;

public class RecipeDetailFragment extends Fragment {
    private Dessert dessert;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dessert = ((RecipeDetailActivity) getActivity()).getDessert();
        View view = inflater.inflate(R.layout.recipe_detail_layout, container, false);
        addIngredients(view);
        return view;
    }

    private void addIngredients(View view) {
        LinearLayout ingredientLayout = view.findViewById(R.id.ingredients);
        for (Ingredient ingredient : dessert.getIngredients()) {
            View ingredientView = getLayoutInflater().inflate(R.layout.ingredient_layout
                    ,ingredientLayout, false);
            TextView quantity = ingredientView.findViewById(R.id.ingredient_quantity);
            quantity.setText(Double.toString(ingredient.getQuantity()));
            TextView measure = ingredientView.findViewById(R.id.ingredient_measure);
            measure.setText(ingredient.getMeasure());
            TextView ingredientName = ingredientView.findViewById(R.id.ingredient_title);
            ingredientName.setText(ingredient.getIngredient());
            ingredientLayout.addView(ingredientView);
        }
    }
}
