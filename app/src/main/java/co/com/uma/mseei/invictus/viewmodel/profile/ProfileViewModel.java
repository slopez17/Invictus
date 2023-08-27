package co.com.uma.mseei.invictus.viewmodel.profile;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.R.array.gender_array;
import static co.com.uma.mseei.invictus.R.string.successfully_saved;
import static co.com.uma.mseei.invictus.model.AppPreferences.DEF_PROFILE_HEIGHT_M;
import static co.com.uma.mseei.invictus.model.AppPreferences.DEF_PROFILE_WEIGHT_KG;
import static co.com.uma.mseei.invictus.model.profile.Profile.calculateAge;
import static co.com.uma.mseei.invictus.model.profile.Profile.calculateBmi;
import static co.com.uma.mseei.invictus.model.profile.Profile.defineBmiClassification;
import static co.com.uma.mseei.invictus.model.profile.Profile.fixHeightToLimits;
import static co.com.uma.mseei.invictus.model.profile.Profile.fixWeightToLimits;
import static co.com.uma.mseei.invictus.util.Resource.getStringArrayById;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.IN_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.KG_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.LBS_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.M_UND;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.floatToString;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.in2m;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.kg2lbs;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.lbs2kg;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.m2in;
import static co.com.uma.mseei.invictus.util.UnitsAndConversions.stringToFloat;

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

    private final WeightRepository weightRepository;
    private final AppPreferences appPreferences;
    private Boolean isUnitSystemImperial;
    private final String[] genderOptions;
    private final MutableLiveData<Integer> gender;
    private final MutableLiveData<LocalDate> birthdate;
    private final MutableLiveData<String> age;
    private float weight;
    private final MutableLiveData<String> weightOnScreen;
    private final MutableLiveData<String> weightHint;
    private final MutableLiveData<String> weightUnd;
    private float height;
    private final MutableLiveData<String> heightOnScreen;
    private final MutableLiveData<String> heightHint;
    private final MutableLiveData<String> heightUnd;
    private final MutableLiveData<String> bmi;
    private final MutableLiveData<String> bmiClassification;
    private final MutableLiveData<String> updateDate;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        gender = new MutableLiveData<>();
        birthdate = new MutableLiveData<>();
        age = new MutableLiveData<>();
        weightOnScreen = new MutableLiveData<>();
        weightHint = new MutableLiveData<>();
        weightUnd = new MutableLiveData<>();
        heightOnScreen = new MutableLiveData<>();
        heightHint = new MutableLiveData<>();
        heightUnd = new MutableLiveData<>();
        bmi = new MutableLiveData<>();
        bmiClassification = new MutableLiveData<>();
        updateDate = new MutableLiveData<>();

        weightRepository = new WeightRepository(application);
        appPreferences = new AppPreferences(application);
        genderOptions = getStringArrayById(application, gender_array);
    }

    public void initializeValues(){
        isUnitSystemImperial = appPreferences.isUnitSystemImperial();
        setGender(appPreferences.getGender());
        setBirthdate(appPreferences.getBirthDate());
        setWeight(appPreferences.getWeight());
        setHeight(appPreferences.getHeight());
        setUpdateDate(appPreferences.getProfileUpdateDate());
        setWeightHint();
        setWeightUnd();
        setHeightHint();
        setHeightUnd();
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

    public LiveData<String> getAge(){
        return age;
    }

    public LiveData<String> getWeight(){
        return weightOnScreen;
    }

    public void updateWeight(String weightOnScreen) {
        float weight = stringToFloat(weightOnScreen);
        if(isUnitSystemImperial) weight = lbs2kg(weight);
        setWeight(fixWeightToLimits(weight));
    }

    public LiveData<String> getWeightUnd(){
        return weightUnd;
    }

    public void setWeightUnd() {
        this.weightUnd.setValue(isUnitSystemImperial ? LBS_UND : KG_UND);
    }

    public LiveData<String> getWeightHint() {
        return weightHint;
    }

    public void setWeightHint() {
        float weight = isUnitSystemImperial ? kg2lbs(DEF_PROFILE_WEIGHT_KG) : DEF_PROFILE_WEIGHT_KG;
        this.weightHint.setValue(floatToString(weight));
    }

    public LiveData<String> getHeight(){
        return heightOnScreen;
    }

    public void updateHeight(String heightOnScreen) {
        float height = stringToFloat(heightOnScreen);
        if(isUnitSystemImperial) height = in2m(height);
        setHeight(fixHeightToLimits(height));
    }

    public LiveData<String> getHeightUnd(){
        return heightUnd;
    }

    public void setHeightUnd() {
        this.heightUnd.setValue(isUnitSystemImperial ? IN_UND : M_UND);
    }

    public LiveData<String> getHeightHint() {
        return heightHint;
    }

    public void setHeightHint() {
        float height = isUnitSystemImperial ? m2in(DEF_PROFILE_HEIGHT_M) : DEF_PROFILE_HEIGHT_M;
        this.heightHint.setValue(floatToString(height));
    }

    public LiveData<String> getBmi(){
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
        appPreferences.setWeight(weight);
        appPreferences.setHeight(height);
        appPreferences.setProfileUpdateDate(now());
    }

    public Completable saveWeightOnDatabase() {
        Weight weight = new Weight(now().toString(), this.weight);
        return weightRepository.insertWeights(weight);
    }

    public void showSavedFeedback() {
        setUpdateDate(now().toString());
        makeText(getApplication(), successfully_saved, LENGTH_SHORT).show();
    }

    private void setAge(LocalDate birthdate) {
        int age = calculateAge(birthdate);
        this.age.setValue(Integer.toString(age));
    }

    private void setWeight(float weight) {
        this.weight = weight;
        setWeightOnScreen();
        setBmiValues();
    }

    private void setWeightOnScreen() {
        if(isUnitSystemImperial) weight = kg2lbs(weight);
        this.weightOnScreen.setValue(floatToString(weight));
    }

    public void setHeight(float height) {
        this.height = height;
        setHeightOnScreen();
        setBmiValues();
    }

    private void setHeightOnScreen() {
        if(isUnitSystemImperial) height = m2in(height);
        this.heightOnScreen.setValue(floatToString(height));
    }

    private void setBmiValues(){
        float bmi = calculateBmi(weight, height);
        String bmiClassification = defineBmiClassification(getApplication(), bmi);
        this.bmi.setValue(floatToString(bmi));
        this.bmiClassification.setValue(bmiClassification);
    }
}
