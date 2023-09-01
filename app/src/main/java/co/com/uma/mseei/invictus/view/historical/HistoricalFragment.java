package co.com.uma.mseei.invictus.view.historical;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static co.com.uma.mseei.invictus.R.id.bikeCardView;
import static co.com.uma.mseei.invictus.R.id.climbCardView;
import static co.com.uma.mseei.invictus.R.id.jogCardView;
import static co.com.uma.mseei.invictus.R.id.ropeSkippingCardView;
import static co.com.uma.mseei.invictus.R.id.runCardView;
import static co.com.uma.mseei.invictus.R.id.walkCardView;
import static co.com.uma.mseei.invictus.R.string.no_implemented;
import static co.com.uma.mseei.invictus.databinding.FragmentHistoricalBinding.inflate;
import static co.com.uma.mseei.invictus.model.SportType.BIKE;
import static co.com.uma.mseei.invictus.model.SportType.CLIMB;
import static co.com.uma.mseei.invictus.model.SportType.JOG;
import static co.com.uma.mseei.invictus.model.SportType.ROPE_SKIPPING;
import static co.com.uma.mseei.invictus.model.SportType.RUN;
import static co.com.uma.mseei.invictus.model.SportType.WALK;

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
import co.com.uma.mseei.invictus.model.SportType;

public class HistoricalFragment extends Fragment implements OnClickListener {

    public static final String SELECTED_OPTION = "selectedOption";
    private Activity activity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentHistoricalBinding binding = inflate(inflater, container, false);
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        SportType selectedOption = null;
        switch (v.getId()){
            case walkCardView:
                selectedOption = WALK;
                break;
            case jogCardView:
                selectedOption = JOG;
                break;
            case ropeSkippingCardView:
                selectedOption = ROPE_SKIPPING;
                break;
            case climbCardView:
                selectedOption = CLIMB;
                break;
            case runCardView:
                selectedOption = RUN;
                break;
            case bikeCardView:
                selectedOption = BIKE;
                break;
        }

        if (selectedOption == null || selectedOption.isImplemented()){
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