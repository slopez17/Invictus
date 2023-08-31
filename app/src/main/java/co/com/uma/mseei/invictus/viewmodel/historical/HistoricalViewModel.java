package co.com.uma.mseei.invictus.viewmodel.historical;

import static android.icu.text.DateTimePatternGenerator.DAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.DD_MMM_YYYY;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.KG_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.LBS_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.floatToString;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.kg2lbs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.database.Sport;
import co.com.uma.mseei.invictus.model.database.SportLimit;
import co.com.uma.mseei.invictus.model.database.Weight;
import co.com.uma.mseei.invictus.model.database.WeightLimit;
import co.com.uma.mseei.invictus.model.time.All;
import co.com.uma.mseei.invictus.model.time.Day;
import co.com.uma.mseei.invictus.model.time.Month;
import co.com.uma.mseei.invictus.model.time.Time;
import co.com.uma.mseei.invictus.model.time.Week;
import co.com.uma.mseei.invictus.viewmodel.database.SportRepository;
import co.com.uma.mseei.invictus.viewmodel.database.WeightRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class HistoricalViewModel extends AndroidViewModel {

    public static final int WEEK = 1;
    public static final int MONTH = 2;

    private final AppPreferences appPreferences;
    private final WeightRepository weightRepository;
    private final SportRepository sportRepository;
    private final Boolean  isUnitSystemImperial;

    private final MutableLiveData<Time> time;
    private final MutableLiveData<String> period;
    private final MutableLiveData<String> currentWeight;
    private final MutableLiveData<String> currentWeightUnd;

    public HistoricalViewModel(@NonNull Application application) {
        super(application);
        time = new MutableLiveData<>();
        period = new MutableLiveData<>();
        currentWeight = new MutableLiveData<>();
        currentWeightUnd = new MutableLiveData<>();

        weightRepository = new WeightRepository(application);
        sportRepository = new SportRepository(application);
        appPreferences = new AppPreferences(application);
        this.isUnitSystemImperial = appPreferences.isUnitSystemImperial();
    }

    public void initializeValues(int index) {
        setTime(index);
        setPeriod();
        setCurrentWeight();
        setCurrentWeightUnd();
    }

    public MutableLiveData<Time> getTime(){
        return time;
    }

    public void setTime(int index) {
        switch (index){
            case DAY:
                this.time.setValue(new Day());
                break;
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
        this.period.setValue(time.toString(DD_MMM_YYYY));
    }

    public void setActualPeriod(String... period) {
        Time time = requireNonNull(this.time.getValue());
        time.setCurrent(period);
        this.time.setValue(time);
        setPeriod();
    }

    public void setNextPeriod() {
        Time time = requireNonNull(this.time.getValue());
        time.setNext();
        this.time.setValue(time);
        setPeriod();
    }

    public void setPreviousPeriod() {
        Time time = requireNonNull(this.time.getValue());
        time.setPrevious();
        this.time.setValue(time);
        setPeriod();
    }

    public LiveData<String> getCurrentWeight(){
        return currentWeight;
    }

    public void setCurrentWeight() {
        float weight = appPreferences.getWeight();
        if (isUnitSystemImperial) weight = kg2lbs(weight);
        this.currentWeight.setValue(floatToString(weight));
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

    public Single<WeightLimit> getWeightLimits(){
        Time time = requireNonNull(this.time.getValue());
        String[] period = time.toStringArray(ISO_LOCAL_DATE);
        return weightRepository.getWeightLimits(period[0], period[1]);
    }

    public Flowable<List<Sport>> getAllBySportType(String sportType) {
        return sportRepository.getAllBySportType(sportType);
    }

    public Single<List<Sport>> findSportsBySportTypeAndPeriod(String sportType, String dateFrom, String dateTo) {
        return sportRepository.findSportsBySportTypeAndPeriod(sportType, dateFrom, dateTo);
    }

    public Single<SportLimit> getSportLimits(){
        Time time = requireNonNull(this.time.getValue());
        String[] period = time.toStringArray(ISO_LOCAL_DATE);
        return sportRepository.getSportLimits(period[0], period[1]);
    }

    public String[] getTotalSport(List<Sport> sportList) {
        int falls = 0;
        int stepsJumps = 0;
        float calories = 0;

        for (Sport sport : sportList){
            falls = falls + sport.getFalls();
            stepsJumps = stepsJumps + sport.getSteps();
            calories = calories + sport.getCalories();
        }

        return new String[] {String.valueOf(falls), String.valueOf(stepsJumps), floatToString(calories)};
    }
}