package co.com.uma.mseei.invictus.view.historical;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static co.com.uma.mseei.invictus.R.color.green;
import static co.com.uma.mseei.invictus.R.id.nextPeriodButton;
import static co.com.uma.mseei.invictus.R.id.previousPeriodButton;
import static co.com.uma.mseei.invictus.R.string.error_read;
import static co.com.uma.mseei.invictus.util.Debug.getMethodName;
import static co.com.uma.mseei.invictus.util.Resource.getStringById;
import static co.com.uma.mseei.invictus.viewmodel.historical.HistoricalViewModel.ALL;
import static co.com.uma.mseei.invictus.viewmodel.historical.HistoricalViewModel.SPORT;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import co.com.uma.mseei.invictus.databinding.FragmentHistoricalSportOptionBinding;
import co.com.uma.mseei.invictus.model.SportType;
import co.com.uma.mseei.invictus.model.chart.LineChart;
import co.com.uma.mseei.invictus.model.chart.SportLineChart;
import co.com.uma.mseei.invictus.model.database.Sport;
import co.com.uma.mseei.invictus.viewmodel.historical.HistoricalViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SportPlaceholderFragment extends Fragment implements OnClickListener {

    private static final String SECTION_NUMBER = "section_number";
    private static final String SPORT_TYPE = "sport_type";

    private int index;
    private SportType sportType;
    private Activity activity;
    private HistoricalViewModel sportPageViewModel;
    private FragmentHistoricalSportOptionBinding binding;
    private CompositeDisposable compositeDisposable;

    public static SportPlaceholderFragment newInstance(int index, SportType sportType) {
        SportPlaceholderFragment fragment = new SportPlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SECTION_NUMBER, index);
        bundle.putSerializable(SPORT_TYPE,sportType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportPageViewModel = new ViewModelProvider(this).get(HistoricalViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(SECTION_NUMBER);
            sportType = (SportType) getArguments().getSerializable(SPORT_TYPE);
        }
        sportPageViewModel.initializeValues(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentHistoricalSportOptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        compositeDisposable = new CompositeDisposable();
        activity = requireActivity();

        getSportData();
        initializePeriodViews();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getSportData() {
        if (index == ALL) {
            compositeDisposable.add(getAllBySportType());
        } else {
            sportPageViewModel.getTime().observe(getViewLifecycleOwner(), time -> {
                String[] period = time.toStringArray(ISO_LOCAL_DATE);
                compositeDisposable.add(findSportsBySportTypeAndPeriod(period));
            });
        }

    }

    @NonNull
    private Disposable getAllBySportType() {
        return sportPageViewModel.getAllBySportType(sportType.toString())
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        sportList -> {
                            setActualPeriod(sportList);
                            initializeSportViews(sportList);
                        },
                        throwable -> Log.e(getMethodName(), getStringById(activity, error_read), throwable)
                );
    }

    @NonNull
    private Disposable findSportsBySportTypeAndPeriod(String[] period) {
        return sportPageViewModel.findSportsBySportTypeAndPeriod(sportType.toString(), period[0], period[1])
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        this::initializeSportViews,
                        throwable -> Log.e(getMethodName(), getStringById(activity, error_read), throwable)
                );
    }

    private void setActualPeriod(List<Sport> sportList) {
        if (!sportList.isEmpty()) {
            String dateFrom = sportList.get(sportList.size() - 1).getStartDateTime().substring(0,10);
            String dateTo = sportList.get(0).getStartDateTime().substring(0,10);
            sportPageViewModel.setActualPeriod(dateFrom, dateTo);
        }
    }

    private void initializeSportViews(List<Sport> sportList) {
        initializeSportListView(sportList);
        initializeSportLineChartView(sportList);
        initializeNoRecordSportTextView(sportList);
        initializeTotalSportViews(sportList);
    }

    private void initializeSportListView(List<Sport> sportList) {
        ListView sportListView = binding.sportListView;
        SportListViewAdapter adapter = new SportListViewAdapter(activity, sportList);
        sportListView.setAdapter(adapter);
        sportListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(activity, SpecificSportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(SPORT, sportList.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void initializeSportLineChartView(List<Sport> sportList) {
        LineChartView sportLineChartView = binding.sportChart;

        if (sportList.isEmpty()) {
            sportLineChartView.setVisibility(INVISIBLE);
        } else {
            sportLineChartView.setVisibility(VISIBLE);
            Disposable disposable = sportPageViewModel.getSportLimits()
                    .subscribeOn(io())
                    .observeOn(mainThread())
                    .subscribe(
                            sportLimits -> {
                                LineChart chartSport = new SportLineChart(activity, sportLineChartView);
                                chartSport.setIndex(index);
                                chartSport.setColor(green);
                                chartSport.setDataList(new ArrayList<>(sportList));
                                chartSport.setLimits(sportLimits);
                                chartSport.setChart();
                            },
                            throwable -> Log.e(getMethodName(), getStringById(activity, error_read), throwable)
                    );

            compositeDisposable.add(disposable);
        }
    }

    private void initializeNoRecordSportTextView(List<Sport> sportList) {
        TextView noRecordSportTextView = binding.noRecordSportTextView;
        noRecordSportTextView.setVisibility(sportList.isEmpty() ? VISIBLE : INVISIBLE);
    }

    private void initializeTotalSportViews(List<Sport> sportList){
        LinearLayout totalSportLayout = binding.totalSportLayout;
        if (sportList.isEmpty()) {
            totalSportLayout.setVisibility(INVISIBLE);
        } else {
            totalSportLayout.setVisibility(VISIBLE);
            initializeTotalSportTextViews(sportList);
        }
    }

    private void initializeTotalSportTextViews(List<Sport> sportList) {
        TextView totalFallsTextView = binding.totalFallsTextView;
        TextView totalStepsJumpsTextView = binding.totalStepsJumpsTextView;
        TextView totalCaloriesTextView = binding.totalCaloriesTextView;
        String[] sportValues = sportPageViewModel.getTotalSport(sportList);
            totalFallsTextView.setText(sportValues[0]);
            totalStepsJumpsTextView.setText(sportValues[1]);
            totalCaloriesTextView.setText(sportValues[2]);
    }

    private void initializePeriodViews() {
        initializePreviousPeriodButton();
        initializePeriodTextView();
        initializeNextPeriodButton();
    }

    private void initializePreviousPeriodButton() {
        ImageButton previousPeriodButton = binding.previousPeriodButton;
        if (index == ALL){
            previousPeriodButton.setVisibility(INVISIBLE);
        } else {
            previousPeriodButton.setOnClickListener(this);
        }
    }

    private void initializePeriodTextView() {
        TextView periodTextView = binding.periodTextView;
        sportPageViewModel.getPeriod().observe(getViewLifecycleOwner(), periodTextView::setText);
    }

    private void initializeNextPeriodButton() {
        ImageButton nextPeriodButton = binding.nextPeriodButton;
        if (index == ALL){
            nextPeriodButton.setVisibility(INVISIBLE);
        } else {
            nextPeriodButton.setOnClickListener(this);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case previousPeriodButton:
                sportPageViewModel.setPreviousPeriod();
                break;
            case nextPeriodButton:
                sportPageViewModel.setNextPeriod();
                break;
        }
    }
}