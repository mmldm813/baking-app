package com.example.michellemedina.bakingapp.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.data.Step;

public class StepDetailFragment extends Fragment {
    private static final String EXTRA_DESSERT = "dessert";
    private static final String EXTRA_STEP_ID = "stepId";

    private Dessert dessert;
    private Step step;

    public static StepDetailFragment newInstance(Dessert dessert, int stepId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DESSERT, dessert);
        bundle.putInt(EXTRA_STEP_ID, stepId);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(bundle);

        return stepDetailFragment;
    }

    private void readFromBundle(Bundle bundle) {
        if (bundle != null) {
            dessert = (Dessert) bundle.getSerializable(EXTRA_DESSERT);
            int stepId = bundle.getInt(EXTRA_STEP_ID);
            step = dessert.getSteps().get(stepId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        readFromBundle(getArguments());
        View view = inflater.inflate(R.layout.step_detail_layout, container, false);
        addStepDescription(view);
        recipeNavigationSetup(view);
        return view;
    }

    private void addStepDescription(View view) {
        TextView stepDescription = view.findViewById(R.id.step_long_description);
        stepDescription.setText(step.getDescription());
    }

    private void recipeNavigationSetup(View view) {
        Button previousButton = view.findViewById(R.id.previous_button);
        final int currentStepId = step.getStepId();
        if (currentStepId == 0) {
            previousButton.setEnabled(false);
        }
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_fragment_container,
                                StepDetailFragment.newInstance(dessert, currentStepId - 1))
                        .commit();
            }
        });

        Button nextButton = view.findViewById(R.id.next_button);
        if (currentStepId == dessert.getSteps().size() - 1) {
            nextButton.setEnabled(false);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_fragment_container,
                                StepDetailFragment.newInstance(dessert, currentStepId + 1))
                        .commit();
            }
        });
    }

}
