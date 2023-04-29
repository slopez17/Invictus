package co.com.uma.mseei.invictus.view.navigation;

import static co.com.uma.mseei.invictus.R.id.monitorButton;
import static co.com.uma.mseei.invictus.R.string.start;
import static co.com.uma.mseei.invictus.R.string.stop;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getStringById;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import co.com.uma.mseei.invictus.databinding.FragmentHomeBinding;
import co.com.uma.mseei.invictus.viewmodel.navigation.HomeViewModel;

public class HomeFragment
        extends Fragment implements View.OnClickListener {


    private Activity activity;
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = requireActivity();

        initializeMonitorButton();
        return root;
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

    private void initializeMonitorButton() {
        Button monitorButton = binding.monitorButton;
        monitorButton.setOnClickListener(this);
        homeViewModel.getMonitoringState().observe(getViewLifecycleOwner(), state -> {
            int id = state ? stop : start;
            monitorButton.setText(getStringById(activity, id));
        });
    }

}