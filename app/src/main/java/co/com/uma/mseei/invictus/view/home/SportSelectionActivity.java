package co.com.uma.mseei.invictus.view.home;

import static co.com.uma.mseei.invictus.R.id.confirmButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import co.com.uma.mseei.invictus.databinding.ActivitySportSelectionBinding;

public class SportSelectionActivity extends AppCompatActivity implements OnClickListener {

    public static final String SELECTED_SPORT = "selected_sport";
    private ActivitySportSelectionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySportSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button confirmButton = binding.confirmButton;
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == confirmButton) {
            Intent i = new Intent();
            i.putExtra(SELECTED_SPORT, getSelectedSport());
            setResult(RESULT_OK, i);
            finish();
        }
    }

    private int getSelectedSport() {
        RadioGroup sportsRadioGroup = binding.sportsRadioGroup;
        int id = sportsRadioGroup.getCheckedRadioButtonId();
        View radioButton = sportsRadioGroup.findViewById(id);
        return sportsRadioGroup.indexOfChild(radioButton);
    }
}

