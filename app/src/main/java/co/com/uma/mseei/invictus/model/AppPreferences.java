package co.com.uma.mseei.invictus.model;


import static android.content.Context.MODE_PRIVATE;

import static java.lang.Boolean.*;
import static java.lang.Integer.parseInt;
import static java.time.LocalDate.now;

import static co.com.uma.mseei.invictus.model.Profile.NO_ANSWER;
import static co.com.uma.mseei.invictus.util.GeneralConstants.WEIGHT;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import java.time.LocalDate;

public class AppPreferences {
    /*
     * Profile constants
     * If you need to modify DEFAULT_WEIGHT/DEFAULT_HEIGHT , you must change corresponding string
     * value on res directory
     */
    public static final String PREF_GENDER = "gender";
    public static final String PREF_BIRTH_DATE = "birthDate";
    public static final String PREF_WEIGHT = "weight";
    public static final String PREF_HEIGHT = "height";
    public static final String PREF_PROFILE_UPDATE_DATE = "profileUpdateDate";

    public static final int DEFAULT_GENDER = NO_ANSWER;
    public static final float DEFAULT_WEIGHT_KG = 56.7f;
    public static final float DEFAULT_HEIGHT_M = 1.70f;

    /*
     * Settings constants
     * If you need to modify some value, you must change corresponding key/default value on
     * fragment_settings
     */
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
    public static final String DEFAULT_UNIT_SYSTEM = "false";

    /*
     * AppPreferences constants
     */
    public static final String PREF_FILE = "data";
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    /*
     * Constructor
     */
    public AppPreferences(Application application) {
        this.preferences = application.getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    public AppPreferences(Activity activity) {
        this.preferences = activity.getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    /*
     * Getters and setters Profile
     */
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
        return preferences.getFloat(PREF_WEIGHT, DEFAULT_WEIGHT_KG);
    }

    public void setWeight(float weight) {
        editor.putFloat(PREF_WEIGHT, weight);
        editor.apply();
    }

    public float getHeight() {
        return preferences.getFloat(PREF_HEIGHT, DEFAULT_HEIGHT_M);
    }

    public void setHeight(float height ) {
        editor.putFloat(PREF_HEIGHT, height);
        editor.apply();
    }

    public String getProfileUpdateDate() {
        return preferences.getString(PREF_PROFILE_UPDATE_DATE, now().toString());
    }

    public void setProfileUpdateDate(LocalDate updateDate) {
        editor.putString(PREF_PROFILE_UPDATE_DATE, updateDate.toString());
        editor.apply();
    }

    /*
     * Getters and setters Settings
     */
    public boolean isAutoFinishOn() {
        return preferences.getBoolean(PREF_AUTO_FINISH, DEFAULT_AUTO_FINISH);
    }

    public int getSamplesLimit() {
        String samplesLimit = preferences.getString(PREF_SAMPLES_LIMIT, DEFAULT_SAMPLES_LIMIT);
        return parseInt(samplesLimit);
    }

    public int getSamplesOnMemory() {
        String samplesOnMemory = preferences.getString(PREF_SAMPLES_ON_MEMORY, DEFAULT_SAMPLES_ON_MEMORY);
        return parseInt(samplesOnMemory);
    }

    public boolean isSaveOnSdOn() {
        return preferences.getBoolean(PREF_SAVE_ON_SD, DEFAULT_SAVE_ON_SD);
    }

    public String getFileName() {
        return preferences.getString(PREF_FILE_NAME, DEFAULT_FILE_NAME);
    }

    public boolean isMultipleFilesOn() {
        return preferences.getBoolean(PREF_MULTIPLE_FILES, DEFAULT_MULTIPLE_FILES);
    }

    public int getRefreshTime() {
        String refreshTime = preferences.getString(PREF_REFRESH_TIME, DEFAULT_REFRESH_TIME);
        return parseInt(refreshTime);
    }

    public boolean isUnitSystemImperial() {
        String unitSystem = preferences.getString(PREF_UNIT_SYSTEM, DEFAULT_UNIT_SYSTEM);
        return parseBoolean(unitSystem);
    }
}
