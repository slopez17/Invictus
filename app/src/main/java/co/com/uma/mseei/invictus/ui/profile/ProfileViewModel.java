package co.com.uma.mseei.invictus.ui.profile;

import static java.time.LocalDate.now;
import static co.com.uma.mseei.invictus.R.array.gender_array;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.time.Period;

import co.com.uma.mseei.invictus.model.AppPreferences;
import co.com.uma.mseei.invictus.util.Resources;

public class ProfileViewModel extends AndroidViewModel {

    private final String[] genderOptions;
    private final MutableLiveData<Integer> gender;
    private final MutableLiveData<LocalDate> birthdate;
    private final MutableLiveData<Integer> age;
    private final MutableLiveData<Float> height;
    private final MutableLiveData<Float> weight;
    private final AppPreferences appPreferences;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        appPreferences = new AppPreferences(application);
        gender = new MutableLiveData<>();
        birthdate = new MutableLiveData<>();
        age = new MutableLiveData<>();
        height = new MutableLiveData<>();
        weight = new MutableLiveData<>();
        genderOptions = Resources.getStringArrayById(application, gender_array);
    }

    public void initializeValues(){
        gender.setValue(appPreferences.getGender());
        LocalDate date = appPreferences.getBirthDate();
        birthdate.setValue(date);
        calculeAge(date);
        height.setValue(appPreferences.getHeight());
        weight.setValue(appPreferences.getWeight());
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
        calculeAge(date);
    }

    public LiveData<Integer> getAge(){
        return age;
    }

    public LiveData<Float> getHeight(){
        return height;
    }

    public void setHeight(float height) {
        this.height.setValue(height);
    }

    public LiveData<Float> getWeight(){
        return weight;
    }

    public void setWeight(float weight) {
        this.weight.setValue(weight);
    }


    //TODO creo que quizás se deba reubicar
    private void calculeAge(LocalDate date) {
        age.setValue(Period.between(date, now()).getYears());
    }
}
