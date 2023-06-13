package co.com.uma.mseei.invictus.view.historical;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static co.com.uma.mseei.invictus.R.id.ropeSkippingCardView;
import static co.com.uma.mseei.invictus.R.id.walkCardView;
import static co.com.uma.mseei.invictus.R.id.weightCardView;
import static co.com.uma.mseei.invictus.R.string.no_implemented;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.NO_IMPLEMENTED;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.ROPE_SKIPPING;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.WALK;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.WEIGHT;
import static co.com.uma.mseei.invictus.model.historical.HistoricalOptions.SELECTED_OPTION;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import co.com.uma.mseei.invictus.databinding.FragmentHistoricalBinding;
import co.com.uma.mseei.invictus.view.historical.HistoricalActivity;

public class HistoricalFragment extends Fragment implements OnClickListener {

    private Activity activity;
    private FragmentHistoricalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHistoricalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = requireActivity();

        initialize(binding.weightCardView);
        initialize(binding.walkCardView);
        initialize(binding.jogCardView);
        initialize(binding.ropeSkippingCardView);
        initialize(binding.climbCardView);
        initialize(binding.runCardView);
        initialize(binding.bikeCardView);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int selectedOption;

        switch (v.getId()){
            case weightCardView:
                selectedOption = WEIGHT;
                break;
            case walkCardView:
                selectedOption = WALK;
                break;
            case ropeSkippingCardView:
                selectedOption = ROPE_SKIPPING;
                break;
            default:
                selectedOption = NO_IMPLEMENTED;
                break;
        }

        if (selectedOption != NO_IMPLEMENTED){
            Intent intent = new Intent(activity, HistoricalActivity.class);
            intent.putExtra(SELECTED_OPTION, selectedOption);
            startActivity(intent);
        } else {
            makeText(activity, getString(no_implemented), LENGTH_LONG).show();
        }
    }

    private void initialize(CardView cardView) {
        cardView.setOnClickListener(this);
    }
}