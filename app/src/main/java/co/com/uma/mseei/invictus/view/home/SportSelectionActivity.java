package co.com.uma.mseei.invictus.view.home;

import static co.com.uma.mseei.invictus.R.id.confirmButton;
import static co.com.uma.mseei.invictus.util.GeneralConstants.SELECTED_SPORT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import co.com.uma.mseei.invictus.databinding.ActivitySportSelectionBinding;

public class SportSelectionActivity extends AppCompatActivity implements View.OnClickListener {

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
            RadioGroup sportsRadioGroup = binding.sportsRadioGroup;
            int selectedSport = sportsRadioGroup.getCheckedRadioButtonId();

            Intent i = new Intent();
            i.putExtra(SELECTED_SPORT, selectedSport);
            setResult(RESULT_OK, i);
            finish();
        }
    }
}

