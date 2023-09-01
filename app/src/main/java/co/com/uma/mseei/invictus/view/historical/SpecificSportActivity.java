package co.com.uma.mseei.invictus.view.historical;

import static co.com.uma.mseei.invictus.R.color.purple;
import static co.com.uma.mseei.invictus.R.string.error_read;
import static co.com.uma.mseei.invictus.util.Debug.getMethodName;
import static co.com.uma.mseei.invictus.util.Resource.getStringById;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.KCAL_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.KM_H_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.KM_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.floatToString;
import static co.com.uma.mseei.invictus.viewmodel.historical.HistoricalViewModel.SPORT;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.com.uma.mseei.invictus.databinding.ActivitySpecificSportBinding;
import co.com.uma.mseei.invictus.model.SportType;
import co.com.uma.mseei.invictus.model.chart.LineChart;
import co.com.uma.mseei.invictus.model.chart.SpeedLineChart;
import co.com.uma.mseei.invictus.model.database.SpeedLimit;
import co.com.uma.mseei.invictus.model.database.Sport;
import co.com.uma.mseei.invictus.viewmodel.database.FeedbackRepository;
import co.com.uma.mseei.invictus.viewmodel.database.SpeedRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lecho.lib.hellocharts.view.LineChartView;

public class SpecificSportActivity extends AppCompatActivity {

    private ActivitySpecificSportBinding binding;
    private SpeedRepository speedRepository;
    private FeedbackRepository feedbackRepository;
    private CompositeDisposable compositeDisposable;
    private Sport sport;
    private Float maxSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySpecificSportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sport = getIntent().getParcelableExtra(SPORT);
        compositeDisposable = new CompositeDisposable();
        speedRepository = new SpeedRepository(this.getApplication());
        feedbackRepository = new FeedbackRepository(this.getApplication());

        if(sport != null){
            int id = sport.getSportId();
            initializeDatabaseReads(id);
            initializeSportTypeViews(id);
            initializeTimeViews();
            initializeSportValuesViews();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initializeDatabaseReads(int id) {
        compositeDisposable.add(getMaxSpeed(id));
        compositeDisposable.add(getAvgSpeed(id));
        compositeDisposable.add(getFeedback(id));
    }

    private void initializeSportTypeViews(int id) {
        SportType sportType = SportType.valueOf(sport.getSportType());
        TextView idTextView = binding.idTextView;
        TextView sportTextView = binding.sportTextView;
        ImageView sportImageView = binding.sportImageView;
        idTextView.setText(String.valueOf(id));
        sportTextView.setText(sportType.getName());
        sportImageView.setImageResource(sportType.getIcon());
    }

    private void initializeTimeViews() {
        TextView startTimeTextView = binding.startTimeTextView;
        TextView endTimeTextView = binding.endTimeTextView;
        TextView elapsedTimeTextView = binding.elapsedTimeTextView;
        startTimeTextView.setText(sport.getStartDateTime());
        endTimeTextView.setText(sport.getEndDateTime());
        elapsedTimeTextView.setText(sport.getElapsedTime());
    }

    private void initializeSportValuesViews() {
        TextView fallsTextView = binding.fallsTextView;
        TextView stepsJumpsTextView = binding.stepsJumpsTextView;
        TextView caloriesTextView = binding.caloriesTextView;
        TextView distanceTextView = binding.distanceTextView;
        fallsTextView.setText(String.valueOf(sport.getFalls()));
        stepsJumpsTextView.setText(String.valueOf(sport.getSteps()));
        caloriesTextView.setText(String.format("%s %s", floatToString(sport.getCalories()), KCAL_UND));
        distanceTextView.setText(String.format("%s%s", floatToString(sport.getDistance()), KM_UND));
    }

    @NonNull
    private Disposable getMaxSpeed(int id) {
        return speedRepository.getMaxSpeed(id)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        maxSpeed -> {
                            this.maxSpeed = maxSpeed;
                            compositeDisposable.add(getSpeedsById(id));
                            TextView maxSpeedTextView = binding.maxSpeedTextView;
                            maxSpeedTextView.setText(String.format("%s %s", floatToString(maxSpeed), KM_H_UND));
                        },
                        throwable -> Log.e(getMethodName(), getStringById(this, error_read), throwable)
                );
    }
    @NonNull
    private Disposable getAvgSpeed(int id) {
        return speedRepository.getAvgSpeed(id)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        avgSpeed -> {
                            TextView avgSpeedTextView = binding.avgSpeedTextView;
                            avgSpeedTextView.setText(String.format("%s %s", floatToString(avgSpeed), KM_H_UND));
                        },
                        throwable -> Log.e(getMethodName(), getStringById(this, error_read), throwable)
                );
    }

    @NonNull
    private Disposable getSpeedsById(int id) {
        return speedRepository.getSpeedsById(id)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        speedList -> {
                            LineChartView speedLineChartView = binding.speedChart;
                            LineChart chartSpeed = new SpeedLineChart(this, speedLineChartView);
                            chartSpeed.setColor(purple);
                            chartSpeed.setDataList(new ArrayList<>(speedList));
                            SpeedLimit speedLimits = new SpeedLimit(speedList.get(0).getElapsedTime(),
                                    speedList.get(speedList.size()-1).getElapsedTime(), 0f, maxSpeed);
                            chartSpeed.setLimits(speedLimits);
                            chartSpeed.setChart();
                        },
                        throwable -> Log.e(getMethodName(), getStringById(this, error_read), throwable)
                );
    }

    @NonNull
    private Disposable getFeedback(int id) {
        return feedbackRepository.getFeedback(id)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        feedback -> {
                            TextView commentTextView = binding.commentTextView;
                            commentTextView.setText(feedback.getComments());
                        },
                        throwable -> Log.e(getMethodName(), getStringById(this, error_read), throwable)
                );
    }

}
