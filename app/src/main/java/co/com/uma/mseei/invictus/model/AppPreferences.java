package co.com.uma.mseei.invictus.model;


import static android.content.Context.MODE_PRIVATE;

import static java.time.LocalDate.now;

import static co.com.uma.mseei.invictus.model.Profile.NO_ANSWER;

import android.app.Application;
import android.content.SharedPreferences;

import java.time.LocalDate;

public class AppPreferences {

    /**
     * Settings constants
     * If you need to modify some value, you must change corresponding key/default value on
     * fragment_settings
     **/
    public static final String PREF_AUTO_FINISH = "autoFinish";
    public static final String PREF_SAMPLES_LIMIT = "samplesLimit";
    public static final String PREF_SAMPLES_ON_MEMORY = "samplesOnMemory";
    public static final String PREF_SAVE_ON_SD = "saveOnSD";
    public static final String PREF_FILE_NAME = "fileName";
    public static final String PREF_MULTIPLE_FILES = "multipleFiles";
    public static final String PREF_REFRESH_TIME = "refreshTime";
    public static final String PREF_UNIT_SYSTEM = "unitSystem";

    public static final boolean DEFAULT_AUTO_FINISH = false;
    public static final String DEFAULT_SAMPLES_LIMIT = "2000";
    private static final String DEFAULT_SAMPLES_ON_MEMORY = "200";
    private static final boolean DEFAULT_SAVE_ON_SD = false;
    private static final String DEFAULT_FILE_NAME = "Invictus";
    private static final boolean DEFAULT_MULTIPLE_FILES = false;
    private static final String DEFAULT_REFRESH_TIME = "5";
    public static final String DEFAULT_UNIT_SYSTEM = "0";

    /**
     * Profile constants
     * If you need to modify DEFAULT_WEIGHT/DEFAULT_HEIGHT , you must change corresponding string
     * value on res directory
     **/
    public static final String PREF_GENDER = "gender";
    public static final String PREF_BIRTH_DATE = "birthDate";
    public static final String PREF_WEIGHT = "weight";
    public static final String PREF_HEIGHT = "height";
    public static final String PREF_PROFILE_UPDATE_DATE = "profileUpdateDate";

    public static final int DEFAULT_GENDER = NO_ANSWER;
    public static final float DEFAULT_WEIGHT = 56.7f;
    public static final float DEFAULT_HEIGHT = 1.70f;

    /**
     * AppPreferences constants
     **/
    public static final String PREF_FILE = "data";
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    /**
     * Constructor
     **/
    public AppPreferences(Application application) {
        this.preferences = application.getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    /**
     * Getters and setters
     **/
    public int getGender() {
        return preferences.getInt(PREF_GENDER, DEFAULT_GENDER);
    }

    public void setGender(int gender) {
        editor.putInt(PREF_GENDER, gender);
        editor.apply();
    }

    public LocalDate getBirthDate() {
        String birthDate = preferences.getString(PREF_BIRTH_DATE, now().toString());
        return LocalDate.parse(birthDate);
    }

    public void setBirthDate(LocalDate birthDate) {
        editor.putString(PREF_BIRTH_DATE, birthDate.toString());
        editor.apply();
    }

    public float getWeight() {
        return preferences.getFloat(PREF_WEIGHT, DEFAULT_WEIGHT);
    }

    public void setWeight(float weight) {
        editor.putFloat(PREF_WEIGHT, weight);
        editor.apply();
    }

    public float getHeight() {
        return preferences.getFloat(PREF_HEIGHT, DEFAULT_HEIGHT);
    }

    public void setHeight(float height ) {
        editor.putFloat(PREF_HEIGHT, height);
        editor.apply();
    }

    public LocalDate getProfileUpdateDate() {
        String profileUpdateDate = preferences.getString(PREF_PROFILE_UPDATE_DATE, now().toString());
        return LocalDate.parse(profileUpdateDate);
    }
}
