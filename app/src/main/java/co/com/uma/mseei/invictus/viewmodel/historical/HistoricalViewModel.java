package co.com.uma.mseei.invictus.viewmodel.historical;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.model.profile.Profile.fixWeightToLimits;
import static co.com.uma.mseei.invictus.util.GeneralConstants.DD_MMM_YYYY;
import static co.com.uma.mseei.invictus.util.GeneralConstants.KG_UND;
import static co.com.uma.mseei.invictus.util.GeneralConstants.LBS_UND;
import static co.com.uma.mseei.invictus.util.GeneralConstants.TWO_DIGITS;
import static co.com.uma.mseei.invictus.util.MathOperations.kg2lbs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.database.Limits;
import co.com.uma.mseei.invictus.model.database.Weight;
import co.com.uma.mseei.invictus.model.historical.time.All;
import co.com.uma.mseei.invictus.model.historical.time.Month;
import co.com.uma.mseei.invictus.model.historical.time.Time;
import co.com.uma.mseei.invictus.model.historical.time.Week;
import co.com.uma.mseei.invictus.viewmodel.database.WeightRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class HistoricalViewModel extends AndroidViewModel {

    public static final int WEEK = 1;
    public static final int MONTH = 2;

    private final AppPreferences appPreferences;
    private final WeightRepository weightRepository;
    private final Boolean  isUnitSystemImperial;

    private final MutableLiveData<Integer> index;
    private final MutableLiveData<Time> time;
    private final MutableLiveData<String> period;
    private final MutableLiveData<String> currentWeight;
    private final MutableLiveData<String> currentWeightUnd;

    public HistoricalViewModel(@NonNull Application application) {
        super(application);
        appPreferences = new AppPreferences(application);
        weightRepository = new WeightRepository(application);

        index = new MutableLiveData<>();
        time = new MutableLiveData<>();
        period = new MutableLiveData<>();
        currentWeight = new MutableLiveData<>();
        currentWeightUnd = new MutableLiveData<>();
        this.isUnitSystemImperial = appPreferences.isUnitSystemImperial();
    }

    public void initializeValues(int index) {
        setIndex(index);
        setTime(index);
        setPeriod();
        setCurrentWeight();
        setCurrentWeightUnd();
    }

    public void setIndex(int index) {
        this.index.setValue(index);
    }

    public MutableLiveData<Time> getTime(){
        return time;
    }

    public void setTime(int index) {
        switch (index){
            case WEEK:
                this.time.setValue(new Week());
                break;
            case MONTH:
                this.time.setValue(new Month());
                break;
            default:
                this.time.setValue(new All());
                break;
        }
    }

    public LiveData<String> getPeriod() {
        return period;
    }

    public void setPeriod() {
        Time time = requireNonNull(this.time.getValue());
        this.period.setValue(time.periodToString(DD_MMM_YYYY));
    }

    public void setActualPeriod(String... period) {
        Time time = requireNonNull(this.time.getValue());
        time.setActualPeriod(period);
        this.time.setValue(time);
        setPeriod();
    }

    public void setNextPeriod() {
        Time time = requireNonNull(this.time.getValue());
        time.setNextPeriod();
        this.time.setValue(time);
        setPeriod();
    }

    public void setPreviousPeriod() {
        Time time = requireNonNull(this.time.getValue());
        time.setPreviousPeriod();
        this.time.setValue(time);
        setPeriod();
    }

    public LiveData<String> getCurrentWeight(){
        return currentWeight;
    }

    public void setCurrentWeight() {
        Float weight = appPreferences.getWeight();
        weight = fixWeightToLimits(weight);
        if (isUnitSystemImperial) weight = kg2lbs(weight);
        this.currentWeight.setValue(TWO_DIGITS.format(weight));
    }

    public LiveData<String> getCurrentWeightUnd(){
        return currentWeightUnd;
    }

    public void setCurrentWeightUnd() {
        String weightUnd = isUnitSystemImperial ? LBS_UND : KG_UND;
        this.currentWeightUnd.setValue(weightUnd);
    }

    public Flowable<List<Weight>> getAllWeights() {
        return weightRepository.getAllWeights();
    }

    public Single<List<Weight>> findWeightsByPeriod(String dateFrom, String dateTo) {
        return weightRepository.findWeightsByPeriod(dateFrom, dateTo);
    }

    public Single<Limits> getWeightLimits(){
        Time time = requireNonNull(this.time.getValue());
        String[] period = time.periodToStringArray(ISO_LOCAL_DATE);
        return weightRepository.getWeightLimits(period[0], period[1]);
    }


}