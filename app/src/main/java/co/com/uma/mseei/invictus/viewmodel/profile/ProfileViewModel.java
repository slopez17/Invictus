package co.com.uma.mseei.invictus.viewmodel.profile;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.R.array.gender_array;
import static co.com.uma.mseei.invictus.R.string.successfully_saved;
import static co.com.uma.mseei.invictus.model.profile.Profile.calculateAge;
import static co.com.uma.mseei.invictus.model.profile.Profile.calculateBmi;
import static co.com.uma.mseei.invictus.model.profile.Profile.defineBmiClassification;
import static co.com.uma.mseei.invictus.model.profile.Profile.fixHeightToLimits;
import static co.com.uma.mseei.invictus.model.profile.Profile.fixWeightToLimits;
import static co.com.uma.mseei.invictus.util.GeneralConstants.CLEAN;
import static co.com.uma.mseei.invictus.util.GeneralConstants.IN_UND;
import static co.com.uma.mseei.invictus.util.GeneralConstants.KG_UND;
import static co.com.uma.mseei.invictus.util.GeneralConstants.LBS_UND;
import static co.com.uma.mseei.invictus.util.GeneralConstants.M_UND;
import static co.com.uma.mseei.invictus.util.MathOperations.in2m;
import static co.com.uma.mseei.invictus.util.MathOperations.kg2lbs;
import static co.com.uma.mseei.invictus.util.MathOperations.lbs2kg;
import static co.com.uma.mseei.invictus.util.MathOperations.m2in;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getStringArrayById;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.model.database.Weight;
import co.com.uma.mseei.invictus.viewmodel.database.WeightRepository;
import io.reactivex.Completable;

public class ProfileViewModel extends AndroidViewModel {

    private final AppPreferences appPreferences;
    private final WeightRepository weightRepository;
    private final String[] genderOptions;
    private Boolean  isUnitSystemImperial;
    private final MutableLiveData<Integer> gender;
    private final MutableLiveData<LocalDate> birthdate;
    private final MutableLiveData<Integer> age;
    private final MutableLiveData<Float> weight;
    private final MutableLiveData<Float> weightHint;
    private final MutableLiveData<Float> weightOnScreen;
    private final MutableLiveData<String> weightUnd;
    private final MutableLiveData<Float> height;
    private final MutableLiveData<Float> heightHint;
    private final MutableLiveData<Float> heightOnScreen;
    private final MutableLiveData<String> heightUnd;
    private final MutableLiveData<Float> bmi;
    private final MutableLiveData<String> bmiClassification;
    private final MutableLiveData<String> updateDate;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        appPreferences = new AppPreferences(application);
        weightRepository = new WeightRepository(application);
        genderOptions = getStringArrayById(application, gender_array);
        gender = new MutableLiveData<>();
        birthdate = new MutableLiveData<>();
        age = new MutableLiveData<>();
        weight = new MutableLiveData<>();
        weightUnd = new MutableLiveData<>();
        weightHint = new MutableLiveData<>();
        weightOnScreen = new MutableLiveData<>();
        height = new MutableLiveData<>();
        heightUnd = new MutableLiveData<>();
        heightHint = new MutableLiveData<>();
        heightOnScreen = new MutableLiveData<>();
        bmi = new MutableLiveData<>();
        bmiClassification = new MutableLiveData<>();
        updateDate = new MutableLiveData<>();
    }

    public void initializeValues(){
        isUnitSystemImperial = appPreferences.isUnitSystemImperial();
        setGender(appPreferences.getGender());
        setBirthdate(appPreferences.getBirthDate());
        setWeight(appPreferences.getWeight());
        setHeight(appPreferences.getHeight());
        setUpdateDate(appPreferences.getProfileUpdateDate());
        setWeightUnd();
        setWeightHint();
        setHeightUnd();
        setHeightHint();
    }

    public String[] getGenderOptions() {
        return genderOptions;
    }

    public LiveData<Integer> getGender(){
        return gender;
    }

    public void setGender(int gender) {
       this.gender.setValue(gender);
    }

    public LiveData<LocalDate> getBirthdate(){
        return birthdate;
    }

    public void setBirthdate(LocalDate date) {
        this.birthdate.setValue(date);
        setAge(date);
    }

    public LiveData<Integer> getAge(){
        return age;
    }

    public LiveData<Float> getWeight(){
        return weightOnScreen;
    }

    public void setWeight(float weight) {
        this.weight.setValue(weight);
        setWeightOnScreen(weight);
        setBmiValues();
    }

    public void updateWeight(float weightOnScreen) {
        float weight = isUnitSystemImperial ? lbs2kg(weightOnScreen) : weightOnScreen;
        weight = fixWeightToLimits(weight);
        setWeight(weight);
    }

    public LiveData<String> getWeightUnd(){
        return weightUnd;
    }

    public void setWeightUnd() {
        this.weightUnd.setValue(isUnitSystemImperial ? LBS_UND : KG_UND);
    }

    public LiveData<Float> getWeightHint() {
        return weightHint;
    }

    public void setWeightHint() {
        float weight = appPreferences.getDefaultWeight();
        this.weightHint.setValue(isUnitSystemImperial ? kg2lbs(weight) : weight);
    }

    public LiveData<Float> getHeight(){
        return heightOnScreen;
    }

    public void setHeight(float height) {
        this.height.setValue(height);
        setHeightOnScreen(height);
        setBmiValues();
    }

    public void updateHeight(float heightOnScreen) {
        float height = isUnitSystemImperial ? in2m(heightOnScreen) : heightOnScreen;
        height = fixHeightToLimits(height);
        setHeight(height);
    }

    public LiveData<String> getHeightUnd(){
        return heightUnd;
    }

    public void setHeightUnd() {
        this.heightUnd.setValue(isUnitSystemImperial ? IN_UND : M_UND);
    }

    public LiveData<Float> getHeightHint() {
        return heightHint;
    }

    public void setHeightHint() {
        float height = appPreferences.getDefaultHeight();
        this.heightHint.setValue(isUnitSystemImperial ? m2in(height) : height);
    }

    public LiveData<Float> getBmi(){
        return bmi;
    }

    public LiveData<String> getBmiClassification(){
        return bmiClassification;
    }

    public LiveData<String> getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String date) {
        this.updateDate.setValue(date);
    }

    public void saveProfilePreferences() {
        appPreferences.setGender(requireNonNull(gender.getValue()));
        appPreferences.setBirthDate(requireNonNull(birthdate.getValue()));
        appPreferences.setWeight(requireNonNull(weight.getValue()));
        appPreferences.setHeight(requireNonNull(height.getValue()));
        appPreferences.setProfileUpdateDate(now());
    }

    public Completable saveWeightOnDatabase() {
        Weight weight = new Weight(now().toString(), requireNonNull(this.weight.getValue()));
        return weightRepository.insertWeights(weight);
    }

    public void showSavedFeedback() {
        setUpdateDate(now().toString());
        makeText(getApplication(), successfully_saved, LENGTH_SHORT).show();
    }

    private void setAge(LocalDate birthdate) {
        this.age.setValue(calculateAge(birthdate));
    }

    private void setWeightOnScreen(float weight) {
        this.weightOnScreen.setValue(isUnitSystemImperial ? kg2lbs(weight) : weight);
    }

    private void setHeightOnScreen(float height) {
        this.heightOnScreen.setValue(isUnitSystemImperial ? m2in(height) : height);
    }

    private void setBmiValues(){
        float weight;
        float height;
        float bmi;
        String bmiClassification;
        try {
            weight = requireNonNull(this.weight.getValue());
            height = requireNonNull(this.height.getValue());
            bmi = calculateBmi(weight, height);
            bmiClassification = defineBmiClassification(getApplication(), bmi);
        } catch (NullPointerException e){
            bmi = 0.0f;
            bmiClassification = CLEAN;
        }
        this.bmi.setValue(bmi);
        this.bmiClassification.setValue(bmiClassification);
    }
}
