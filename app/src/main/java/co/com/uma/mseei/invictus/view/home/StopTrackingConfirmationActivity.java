package co.com.uma.mseei.invictus.view.home;

import static co.com.uma.mseei.invictus.R.id.okButton;
import static co.com.uma.mseei.invictus.databinding.ActivityStopTrackingConfirmationBinding.inflate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import co.com.uma.mseei.invictus.databinding.ActivityStopTrackingConfirmationBinding;

public class StopTrackingConfirmationActivity extends AppCompatActivity implements OnClickListener {
    public static final String STOP_TRACKING = "stop_tracking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStopTrackingConfirmationBinding binding = inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button okButton = binding.okButton;
        okButton.setOnClickListener(this);
        Button cancelButton = binding.cancelButton;
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        boolean answer = view.getId() == okButton;
        Intent i = new Intent();
        i.putExtra(STOP_TRACKING, answer);
        setResult(RESULT_OK, i);
        finish();
    }
}
