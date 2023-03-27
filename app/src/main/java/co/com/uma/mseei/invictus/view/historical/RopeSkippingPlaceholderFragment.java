package co.com.uma.mseei.invictus.view.historical;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import co.com.uma.mseei.invictus.databinding.FragmentHistoricalWeightOptionBinding;
import co.com.uma.mseei.invictus.viewmodel.historical.HistoricalViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class RopeSkippingPlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private HistoricalViewModel pageViewModel;
    private FragmentHistoricalWeightOptionBinding binding;

    public static RopeSkippingPlaceholderFragment newInstance(int index) {
        RopeSkippingPlaceholderFragment fragment = new RopeSkippingPlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(HistoricalViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentHistoricalWeightOptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

/*        final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}