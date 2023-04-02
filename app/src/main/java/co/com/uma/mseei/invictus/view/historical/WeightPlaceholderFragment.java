package co.com.uma.mseei.invictus.view.historical;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static co.com.uma.mseei.invictus.R.color.yellow;
import static co.com.uma.mseei.invictus.R.id.nextPeriodButton;
import static co.com.uma.mseei.invictus.R.id.previousPeriodButton;
import static co.com.uma.mseei.invictus.model.HistoricalOptions.ALL;
import static co.com.uma.mseei.invictus.model.HistoricalOptions.SECTION_NUMBER;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import co.com.uma.mseei.invictus.databinding.FragmentHistoricalWeightOptionBinding;
import co.com.uma.mseei.invictus.model.LineChart;
import co.com.uma.mseei.invictus.model.Weight;
import co.com.uma.mseei.invictus.viewmodel.historical.HistoricalViewModel;
import co.com.uma.mseei.invictus.viewmodel.historical.WeightListViewAdapter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeightPlaceholderFragment extends Fragment implements View.OnClickListener {

    private int index;
    private Activity activity;
    private HistoricalViewModel weightPageViewModel;
    private FragmentHistoricalWeightOptionBinding binding;
    private CompositeDisposable compositeDisposable;

    public static WeightPlaceholderFragment newInstance(int index) {
        WeightPlaceholderFragment fragment = new WeightPlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weightPageViewModel = new ViewModelProvider(this).get(HistoricalViewModel.class);
        index = getArguments() != null ? getArguments().getInt(SECTION_NUMBER) : 1;
        weightPageViewModel.initializeValues(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentHistoricalWeightOptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        compositeDisposable = new CompositeDisposable();

        activity = requireActivity();
        getWeightData();
        initializePeriodViews();

        return root;
    }

    private void getWeightData() {
        if (index == ALL) {
            compositeDisposable.add(getAllWeigths());
        } else {
            weightPageViewModel.getTime().observe(getViewLifecycleOwner(), time -> {
                String[] period = time.periodToStringArray(ISO_LOCAL_DATE);
                compositeDisposable.add(findWeightsByPeriod(period));
            });
        }

    }

    @NonNull
    private Disposable getAllWeigths() {
        return weightPageViewModel.getAllWeights()
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        weightList -> {
                            setActualPeriod(weightList);
                            initializeWeightViews(weightList);
                        }
                );
    }

    @NonNull
    private Disposable findWeightsByPeriod(String[] period) {
        return weightPageViewModel.findWeightsByPeriod(period[0], period[1])
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(
                        this::initializeWeightViews
                );
    }

    private void setActualPeriod(List<Weight> weightList) {
        String dateFrom = weightList.get(weightList.size()-1).getDate();
        String dateTo = weightList.get(0).getDate();
        weightPageViewModel.setActualPeriod(dateFrom, dateTo);
    }

    private void initializeWeightViews(List<Weight> weightList) {
        initializeWeightListView(weightList);
        initializeWeightLineChartView(weightList);
        initializeNoRecordWeightTextView(weightList);
        initializeCurrentWeightViews(weightList);
    }

    private void initializeWeightListView(List<Weight> weightList) {
        ListView weightListView = binding.weightListView;
        WeightListViewAdapter adapter = new WeightListViewAdapter(activity, weightList);
        weightListView.setAdapter(adapter);
    }

    private void initializeWeightLineChartView(List<Weight> weightList) {
        LineChartView weightLineChartView = binding.weightChart;

        if (weightList.isEmpty()) {
            weightLineChartView.setVisibility(INVISIBLE);
        } else {
            weightLineChartView.setVisibility(VISIBLE);
            Disposable disposable = weightPageViewModel.getWeightLimits()
                    .subscribeOn(io())
                    .observeOn(mainThread())
                    .subscribe(
                            weightLimits -> {
                                LineChart chartWeight = new LineChart(activity, weightLineChartView);
                                chartWeight.setColor(yellow);
                                chartWeight.setDataList(weightList);
                                chartWeight.setLimits(weightLimits);
                                chartWeight.setChart();
                            }
                    );

            compositeDisposable.add(disposable);
        }
    }

    private void initializeNoRecordWeightTextView(List<Weight> weightList) {
        TextView noRecordWeightTextView = binding.noRecordWeightTextView;
        noRecordWeightTextView.setVisibility(weightList.isEmpty() ? VISIBLE : INVISIBLE);
    }

    private void initializeCurrentWeightViews(List<Weight> weightList){
        LinearLayout currentWeightLayout = binding.currentWeightLayout;
        if (weightList.isEmpty()) {
            currentWeightLayout.setVisibility(INVISIBLE);
        } else {
            currentWeightLayout.setVisibility(VISIBLE);
            initializeCurrentWeightTextView();
            initializeCurrentWeightUndTextView();
        }
    }

    private void initializeCurrentWeightTextView() {
        TextView currentWeightTextView = binding.currentWeightTextView;
        weightPageViewModel.getCurrentWeight().observe(getViewLifecycleOwner(), currentWeightTextView::setText);
    }

    private void initializeCurrentWeightUndTextView() {
        TextView currentWeightUndTextView = binding.currentWeightUndTextView;
        weightPageViewModel.getCurrentWeightUnd().observe(getViewLifecycleOwner(), currentWeightUndTextView::setText);
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
        weightPageViewModel.getPeriod().observe(getViewLifecycleOwner(), periodTextView::setText);
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
                weightPageViewModel.setPreviousPeriod();
                break;
            case nextPeriodButton:
                weightPageViewModel.setNextPeriod();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        compositeDisposable.dispose();
    }
}