package co.com.uma.mseei.invictus.viewmodel.navigation;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static co.com.uma.mseei.invictus.R.array.gender_array;
import static co.com.uma.mseei.invictus.R.string.successfully_saved;
import static co.com.uma.mseei.invictus.model.AppPreferences.DEFAULT_HEIGHT_M;
import static co.com.uma.mseei.invictus.model.AppPreferences.DEFAULT_WEIGHT_KG;
import static co.com.uma.mseei.invictus.model.Profile.calculateAge;
import static co.com.uma.mseei.invictus.model.Profile.calculateBmi;
import static co.com.uma.mseei.invictus.model.Profile.calculateBmiClassification;
import static co.com.uma.mseei.invictus.model.Profile.fixHeightToLimits;
import static co.com.uma.mseei.invictus.model.Profile.fixWeightToLimits;
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
import co.com.uma.mseei.invictus.model.Weight;
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
        unitSystem();
        userBasic();
        userBody();
        lastUpdate();
    }

    public String[] getGenderOptions() {
        return genderOptions;
    }

    public LiveData<Integer> getGender(){
        return gender;
    }

    public void setGender(int position) {
       gender.setValue(position);
    }

    public LiveData<LocalDate> getBirthdate(){
        return birthdate;
    }

    public void setBirthdate(int year, int month, int dayOfMonth) {
        LocalDate date = LocalDate.of(year, month, dayOfMonth);
        birthdate.setValue(date);
    }

    public LiveData<Integer> getAge(){
        return age;
    }

    public void setAge() {
        int age = calculateAge(birthdate.getValue());
        this.age.setValue(age);
    }

    public LiveData<Float> getWeight(){
        return weightOnScreen;
    }

    public void setWeight(float weightOnScreen) {
        float weight = isUnitSystemImperial ? lbs2kg(weightOnScreen) : weightOnScreen;
        fixWeightToLimits(weight);
        this.weight.setValue(weight);
        setWeight();
    }

    public LiveData<Float> getWeightHint() {
        return weightHint;
    }

    public void setWeightHint() {
        this.weightHint.setValue(isUnitSystemImperial ? kg2lbs(DEFAULT_WEIGHT_KG) : DEFAULT_WEIGHT_KG);
    }

    public LiveData<String> getWeightUnd(){
        return weightUnd;
    }

    public void setWeightUnd() {
        String und = isUnitSystemImperial ? LBS_UND : KG_UND;
        this.weightUnd.setValue(und);
    }

    public LiveData<Float> getHeight(){
        return heightOnScreen;
    }

    public void setHeight(float heightOnScreen) {
        float height = isUnitSystemImperial ? in2m(heightOnScreen) : heightOnScreen;
        this.height.setValue(fixHeightToLimits(height));
        setHeight();
    }

    public LiveData<Float> getHeightHint() {
        return heightHint;
    }

    public void setHeightHint() {
        this.heightHint.setValue(isUnitSystemImperial ? m2in(DEFAULT_HEIGHT_M) : DEFAULT_HEIGHT_M);
    }

    public LiveData<String> getHeightUnd(){
        return heightUnd;
    }

    public void setHeightUnd() {
        String und = isUnitSystemImperial ? IN_UND : M_UND;
        this.heightUnd.setValue(und);
    }

    public LiveData<Float> getBmi(){
        return bmi;
    }

    public LiveData<String> getBmiClassification(){
        return bmiClassification;
    }

    public void setBmiValues(){
        float bmi = calculateBmi(requireNonNull(weight.getValue()), requireNonNull(height.getValue()));
        this.bmi.setValue(bmi);
        String bmiClassification = calculateBmiClassification(getApplication(), bmi);
        this.bmiClassification.setValue(bmiClassification);
    }

    public LiveData<String> getUpdateDate() {
        return updateDate;
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
        this.updateDate.setValue(now().toString());
        makeText(getApplication(), successfully_saved, LENGTH_SHORT).show();
    }

    private void unitSystem() {
        this.isUnitSystemImperial = appPreferences.isUnitSystemImperial();
    }

    private void userBasic() {
        this.gender.setValue(appPreferences.getGender());
        this.birthdate.setValue(appPreferences.getBirthDate());
        setAge();
    }

    private void userBody() {
        this.weight.setValue(fixWeightToLimits(appPreferences.getWeight()));
        setWeight();
        setWeightUnd();
        setWeightHint();
        this.height.setValue(fixHeightToLimits(appPreferences.getHeight()));
        setHeight();
        setHeightUnd();
        setHeightHint();
        setBmiValues();
    }

    private void lastUpdate() {
        this.updateDate.setValue(appPreferences.getProfileUpdateDate());
    }

    private void setWeight() {
        float weight = requireNonNull(this.weight.getValue());
        this.weightOnScreen.setValue(isUnitSystemImperial ? kg2lbs(weight) : weight);
    }

    private void setHeight() {
        float height = requireNonNull(this.height.getValue());
        this.heightOnScreen.setValue(isUnitSystemImperial ? m2in(height) : height);
    }
}
