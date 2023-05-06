package co.com.uma.mseei.invictus.view.navigation;

import static android.app.Activity.RESULT_OK;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static co.com.uma.mseei.invictus.R.id.monitorButton;
import static co.com.uma.mseei.invictus.R.string.start;
import static co.com.uma.mseei.invictus.R.string.stop;
import static co.com.uma.mseei.invictus.util.GeneralConstants.SELECTED_SPORT;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getStringById;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import co.com.uma.mseei.invictus.databinding.FragmentHomeBinding;
import co.com.uma.mseei.invictus.view.home.SportSelectionActivity;
import co.com.uma.mseei.invictus.viewmodel.navigation.HomeViewModel;

public class HomeFragment
        extends Fragment implements View.OnClickListener {


    private Activity activity;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ActivityResultLauncher<Intent> getSelectedSport;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = requireActivity();
        initializeFallsViews();
        initializeStepsViews();
        initializeCaloriesViews();
        initializeDistanceViews();
        initializeSpeedViews();
        initializeElapsedTimeViews();
        initializeMonitorButton();
        initilizeMonitoringService();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO debo mover esta rutina de aquí
        getSelectedSport = registerForActivityResult(new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        int selectedSport = data.getExtras().getInt(SELECTED_SPORT);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == monitorButton) {
            homeViewModel.changeMonitoringState();
        }
    }

    private void initializeFallsViews() {
        TextView fallsTextView = binding.fallsTextView;
        setViewsVisibility(fallsTextView);
    }

    private void initializeStepsViews() {
        TextView stepsJumpsTextView = binding.stepsJumpsTextView;
        setViewsVisibility(stepsJumpsTextView);
    }

    private void initializeCaloriesViews() {
        TextView caloriesTextView = binding.caloriesTextView;
        setViewsVisibility(caloriesTextView);
    }

    private void initializeDistanceViews() {
        TextView distanceTextView = binding.distanceTextView;
        setViewsVisibility(distanceTextView);
    }

    private void initializeSpeedViews() {
        TextView speedTextView = binding.speedTextView;
        setViewsVisibility(speedTextView);
    }

    private void initializeElapsedTimeViews() {
        TextView elapsedTimeTextView = binding.elapsedTimeTextView;
        setViewsVisibility(elapsedTimeTextView);
    }

    private void setViewsVisibility(TextView textView) {
        homeViewModel.getMonitoringState().observe(getViewLifecycleOwner(), state -> {
            int visibility = state ? VISIBLE : INVISIBLE;
            textView.setVisibility(visibility);
        });
    }

    private void initializeMonitorButton() {
        Button monitorButton = binding.monitorButton;
        monitorButton.setOnClickListener(this);
        homeViewModel.getMonitoringState().observe(getViewLifecycleOwner(), state -> {
            int id = state ? stop : start;
            monitorButton.setText(getStringById(activity, id));
        });
    }

    private void initilizeMonitoringService() {
        homeViewModel.getMonitoringState().observe(getViewLifecycleOwner(), state -> {
            Intent intent = new Intent(activity, SportSelectionActivity.class);
            getSelectedSport.launch(intent);
        });
    }
}