package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.util.UnitsAndConversions.floatToString;
import static co.com.uma.mseei.invictus.viewmodel.historical.HistoricalViewModel.SPORT;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.com.uma.mseei.invictus.databinding.ActivitySpecificSportBinding;
import co.com.uma.mseei.invictus.model.SportType;
import co.com.uma.mseei.invictus.model.database.Sport;
import lecho.lib.hellocharts.view.LineChartView;

public class SpecificSportActivity extends AppCompatActivity {

    ActivitySpecificSportBinding binding;
    Sport sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpecificSportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView idTextView = binding.idTextView;
        TextView sportTextView = binding.sportTextView;
        ImageView sportImageView = binding.sportImageView;
        TextView startTimeTextView = binding.startTimeTextView;
        TextView endTimeTextView = binding.endTimeTextView;
        TextView elapsedTimeTextView = binding.elapsedTimeTextView;
        TextView fallsTextView = binding.fallsTextView;
        TextView stepsJumpsTextView = binding.stepsJumpsTextView;
        TextView caloriesTextView = binding.caloriesTextView;
        TextView distanceTextView = binding.distanceTextView;
        TextView maxSpeedTextView = binding.maxSpeedTextView;
        TextView meanSpeedTextView = binding.avgSpeedTextView;
        TextView commentTextView = binding.commentTextView;
        LineChartView speedLineChartView = binding.speedChart;

        sport = getIntent().getParcelableExtra(SPORT);

        if(sport != null){
            idTextView.setText(String.valueOf(sport.getSportId()));
            SportType sportType = SportType.valueOf(sport.getSportType());
            sportTextView.setText(sportType.getName());
            sportImageView.setImageResource(sportType.getIcon());
            startTimeTextView.setText(sport.getStartDateTime());
            endTimeTextView.setText(sport.getEndDateTime());
            elapsedTimeTextView.setText(sport.getElapsedTime());
            fallsTextView.setText(String.valueOf(sport.getFalls()));
            stepsJumpsTextView.setText(String.valueOf(sport.getSteps()));
            caloriesTextView.setText(floatToString(sport.getCalories()));
            distanceTextView.setText(floatToString(sport.getDistance()));
        }
    }
}
