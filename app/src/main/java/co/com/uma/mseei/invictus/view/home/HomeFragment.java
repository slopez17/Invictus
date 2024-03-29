package co.com.uma.mseei.invictus.view.home;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.TRUE;
import static co.com.uma.mseei.invictus.R.drawable.ic_arrow_black_24dp;
import static co.com.uma.mseei.invictus.R.drawable.ic_stop_black_24dp;
import static co.com.uma.mseei.invictus.R.id.trackingButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.com.uma.mseei.invictus.databinding.FragmentHomeBinding;
import co.com.uma.mseei.invictus.viewmodel.home.HomeViewModel;

public class HomeFragment
        extends Fragment implements OnClickListener {

    private Activity activity;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ActivityResultLauncher<Intent> getSportType;
    private ActivityResultLauncher<Intent> getStopTrackingConfirmation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = requireActivity();
        initializeSportTypeView();
        initializeFallsViews();
        initializeStepsViews();
        initializeCaloriesViews();
        initializeDistanceViews();
        initializeSpeedViews();
        initializeElapsedTimeViews();
        initializeTrackingButton();

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSportType = registerForActivityResult(new StartActivityForResult(),
                result -> homeViewModel.startTrackingFor(result));

        getStopTrackingConfirmation = registerForActivityResult(new StartActivityForResult(),
                result -> {
                    boolean wasTrackingStopped = homeViewModel.stopTrackingFor(result);
                    if(wasTrackingStopped){
                        Intent intent = new Intent(activity, FeedbackActivity.class);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.setRefreshDataTimer(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        homeViewModel.setRefreshDataTimer(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == trackingButton) {
            runTrackingTriggers();
        }
    }

    private void initializeSportTypeView() {
        TextView sportTypeTextView = binding.sportTypeTextView;
        ImageView sportTypeImageView = binding.sportTypeImageView;
        homeViewModel.getSportType().observe(getViewLifecycleOwner(), x -> {
            sportTypeTextView.setText(x.getName());
            sportTypeImageView.setImageResource(x.getIcon());
        });
        setViewsVisibility(sportTypeTextView);
        setViewsVisibility(sportTypeImageView);
    }

    private void initializeFallsViews() {
        TextView fallsTextView = binding.fallsTextView;
        homeViewModel.getFalls().observe(getViewLifecycleOwner(), fallsTextView::setText);
        setViewsVisibility(fallsTextView);
    }

    private void initializeStepsViews() {
        TextView stepsJumpsTextView = binding.stepsJumpsTextView;
        homeViewModel.getSteps().observe(getViewLifecycleOwner(), stepsJumpsTextView::setText);
        setViewsVisibility(stepsJumpsTextView);
    }

    private void initializeCaloriesViews() {
        TextView caloriesTextView = binding.caloriesTextView;
        TextView caloriesUndText = binding.caloriesUndText;
        homeViewModel.getCalories().observe(getViewLifecycleOwner(), caloriesTextView::setText);
        homeViewModel.getCaloriesUnd().observe(getViewLifecycleOwner(), caloriesUndText::setText);
        setViewsVisibility(caloriesTextView);
    }

    private void initializeDistanceViews() {
        TextView distanceTextView = binding.distanceTextView;
        TextView distanceUndText = binding.distanceUndText;
        homeViewModel.getDistance().observe(getViewLifecycleOwner(), distanceTextView::setText);
        homeViewModel.getDistanceUnd().observe(getViewLifecycleOwner(), distanceUndText::setText);
        setViewsVisibility(distanceTextView);
    }

    private void initializeSpeedViews() {
        TextView speedTextView = binding.speedTextView;
        TextView speedUndText = binding.speedUndText;
        homeViewModel.getSpeed().observe(getViewLifecycleOwner(), speedTextView::setText);
        homeViewModel.getSpeedUnd().observe(getViewLifecycleOwner(), speedUndText::setText);
        setViewsVisibility(speedTextView);
    }

    private void initializeElapsedTimeViews() {
        TextView elapsedTimeTextView = binding.elapsedTimeTextView;
        homeViewModel.getElapsedTime().observe(getViewLifecycleOwner(), elapsedTimeTextView::setText);
        setViewsVisibility(elapsedTimeTextView);
    }

    private void setViewsVisibility(View view) {
        homeViewModel.isServiceBound().observe(getViewLifecycleOwner(), state -> {
            int visibility = state ? VISIBLE : INVISIBLE;
            view.setVisibility(visibility);
        });
    }

    private void initializeTrackingButton() {
        FloatingActionButton trackingButton = binding.trackingButton;
        trackingButton.setOnClickListener(this);
        homeViewModel.isServiceBound().observe(getViewLifecycleOwner(), state -> {
            int id = state ? ic_stop_black_24dp : ic_arrow_black_24dp;
            trackingButton.setImageResource(id);
        });
    }

    private void runTrackingTriggers() {
        boolean state = TRUE.equals(homeViewModel.isServiceBound().getValue());
        Intent intent;
        if(state) {
            intent = new Intent(activity, StopTrackingConfirmationActivity.class);
            getStopTrackingConfirmation.launch(intent);
        } else {
            intent = new Intent(activity, SportSelectionActivity.class);
            getSportType.launch(intent);
        }
    }

}