package co.com.uma.mseei.invictus.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import co.com.uma.mseei.invictus.databinding.FragmentHistoricalBinding;
import co.com.uma.mseei.invictus.viewmodel.HistoricalViewModel;

public class HistoricalFragment extends Fragment {

    private Activity activity;
    private FragmentHistoricalBinding binding;
    private HistoricalViewModel historicalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historicalViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HistoricalViewModel.class);

        binding = FragmentHistoricalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = requireActivity();

/*        final TextView textView = binding.textDashboard;
        historicalViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}