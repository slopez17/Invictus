package co.com.uma.mseei.invictus.viewmodel;

import static co.com.uma.mseei.invictus.R.array.gender_array;
import static co.com.uma.mseei.invictus.model.Profile.calculateBmi;
import static co.com.uma.mseei.invictus.model.Profile.calculateAge;
import static co.com.uma.mseei.invictus.model.Profile.calculateBmiClassification;
import static co.com.uma.mseei.invictus.util.ResourceOperations.getStringArrayById;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;

import co.com.uma.mseei.invictus.model.AppPreferences;

public class ProfileViewModel extends AndroidViewModel {

    private final String[] genderOptions;
    private final MutableLiveData<Integer> gender;
    private final MutableLiveData<LocalDate> birthdate;
    private final MutableLiveData<Integer> age;
    private final MutableLiveData<Float> weight;
    private final MutableLiveData<Float> height;
    private final MutableLiveData<Float> bmi;
    private final MutableLiveData<String> bmiClassification;
    private final AppPreferences appPreferences;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        appPreferences = new AppPreferences(application);
        gender = new MutableLiveData<>();
        birthdate = new MutableLiveData<>();
        age = new MutableLiveData<>();
        weight = new MutableLiveData<>();
        height = new MutableLiveData<>();
        bmi = new MutableLiveData<>();
        bmiClassification = new MutableLiveData<>();
        genderOptions = getStringArrayById(application, gender_array);
    }

    public void initializeValues(){
        this.gender.setValue(appPreferences.getGender());
        this.birthdate.setValue(appPreferences.getBirthDate());
        setAge();
        this.weight.setValue(appPreferences.getWeight());
        this.height.setValue(appPreferences.getHeight());
        setBmiValues();
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
        setAge();
    }

    public LiveData<Integer> getAge(){
        return age;
    }

    public void setAge() {
        this.age.setValue(calculateAge(birthdate.getValue()));
    }

    public LiveData<Float> getWeight(){
        return weight;
    }

    public void setWeight(float weight) {
        this.weight.setValue(weight);
        setBmiValues();
    }

    public LiveData<Float> getHeight(){
        return height;
    }

    public void setHeight(float height) {
        this.height.setValue(height);
        setBmiValues();
    }

    public LiveData<Float> getBmi(){
        return bmi;
    }

    public LiveData<String> getBmiClassification(){
        return bmiClassification;
    }

    private void setBmiValues(){
        assert weight.getValue() != null && height.getValue() != null;
        float bmi = calculateBmi(weight.getValue(), height.getValue());
        this.bmi.setValue(bmi);
        String bmiClassification = calculateBmiClassification(getApplication(), bmi);
        this.bmiClassification.setValue(bmiClassification);
    }
}
