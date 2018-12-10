package com.example.michellemedina.bakingapp.detail;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michellemedina.bakingapp.R;
import com.example.michellemedina.bakingapp.data.Dessert;
import com.example.michellemedina.bakingapp.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailFragment extends Fragment {
    private static final String EXTRA_DESSERT = "dessert";
    private static final String EXTRA_STEP_ID = "stepId";
    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;

    private Dessert dessert;
    private Step step;
    private ImageView noVideoImage;
    private boolean isTwoPane;
    private View view;

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
        RecipeDetailActivity activity = (RecipeDetailActivity) getActivity();
        isTwoPane = activity.isTwoPaneMode();
        view = inflater.inflate(R.layout.step_detail_layout, container, false);
        view.setClickable(true);
        view.setBackgroundColor(Color.WHITE);
        addStepDescription(view);
        recipeNavigationSetup(view);
        addExoplayerView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupForPlayerView(view);
    }

    private void addStepDescription(View view) {
        TextView stepDescription = view.findViewById(R.id.step_long_description);
        stepDescription.setText(step.getDescription());
    }

    private void recipeNavigationSetup(final View view) {
        Button previousButton = view.findViewById(R.id.previous_button);
        final int currentStepId = step.getStepId();
        if (currentStepId == 0) {
            previousButton.setEnabled(false);
        }
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(isTwoPane ? R.id.step_detail_fragment : R.id.recipe_detail_fragment_container,
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
                        .replace(isTwoPane ? R.id.step_detail_fragment : R.id.recipe_detail_fragment_container,
                                StepDetailFragment.newInstance(dessert, currentStepId + 1))
                        .commit();
            }
        });
    }

    private void addExoplayerView(View view) {
        simpleExoPlayerView = view.findViewById(R.id.player_view);
    }

    private void addNoVideoImage(View view) {
        noVideoImage = view.findViewById(R.id.no_video_image);
    }

    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            Context context = requireActivity();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
//            simpleExoPlayer.seekTo(position);
        }
    }

    private void setupForPlayerView(View view) {
        if (step.getVideoURL().isEmpty() && step.getThumbnailURL().isEmpty()) {
            simpleExoPlayerView.setVisibility(View.GONE);
            addNoVideoImage(view);
            noVideoImage.setVisibility(View.VISIBLE);
        } else if (step.getVideoURL().isEmpty()) {
            initializePlayer(Uri.parse(step.getThumbnailURL()));
        } else {
            initializePlayer(Uri.parse(step.getVideoURL()));
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
