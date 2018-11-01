package com.example.michellemedina.bakingapp.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.data.Step;

public class StepDetailFragment extends android.support.v4.app.Fragment{
    private static final String EXTRA_STEP = "step";
    private Step step;

    public static StepDetailFragment newInstance(Step step) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_STEP, step);

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(bundle);

        return stepDetailFragment;
    }

    private void readFromBundle(Bundle bundle) {
        if (bundle != null) {
            step = (Step) bundle.getSerializable(EXTRA_STEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        readFromBundle(getArguments());
        View view = inflater.inflate(R.layout.step_detail_layout, container, false);
        addStepDescription(view);
        return view;
    }

    private void addStepDescription(View view) {
            TextView stepDescription = view.findViewById(R.id.step_long_description);
            stepDescription.setText(step.getDescription());
    }
}
