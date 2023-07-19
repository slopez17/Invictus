package co.com.uma.mseei.invictus.model;


import static android.content.Context.MODE_PRIVATE;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.time.LocalDate.now;

import android.app.Application;
import android.content.SharedPreferences;

import java.time.LocalDate;

/**
 * AppPreferences is a model class which represents business logic about application preferences.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public class AppPreferences {

    public static final String APP_PREFERENCES_FILE = "data";

    private static final String PREF_PROFILE_GENDER = "gender";
    private static final String PREF_PROFILE_BIRTHDATE = "birthDate";
    private static final String PREF_PROFILE_WEIGHT = "weight";
    private static final String PREF_PROFILE_HEIGHT = "height";
    private static final String PREF_PROFILE_UPDATE_DATE = "profileUpdateDate";
    private static final int DEF_PROFILE_GENDER = 0;
    private static final float DEF_PROFILE_WEIGHT_KG = 56.7f; //If you need to modify this, you must change corresponding string value on res directory
    private static final float DEF_PROFILE_HEIGHT_M = 1.70f; //If you need to modify this, you must change corresponding string value on res directory

    private static final String PREF_SETTINGS_AUTO_FINISH = "autoFinish"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_SAMPLES_LIMIT = "samplesLimit"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_SAMPLES_ON_MEMORY = "samplesOnMemory"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_SAVE_ON_SD = "saveOnSD"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_FILE_NAME = "fileName"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_MULTIPLE_FILES = "multipleFiles"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_REFRESH_PERIOD = "refreshPeriod"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_UNIT_SYSTEM = "unitSystem"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final String PREF_SETTINGS_DEBUG = "debug"; //If you need to modify this, you must change corresponding key/default value on fragment_settings
    private static final boolean DEF_SETTINGS_AUTO_FINISH = false;
    private static final String DEF_SETTINGS_SAMPLES_LIMIT = "2000";
    private static final String DEF_SETTINGS_SAMPLES_ON_MEMORY = "200";
    private static final boolean DEF_SETTINGS_SAVE_ON_SD = false;
    private static final String DEF_SETTINGS_FILE_NAME = "Invictus";
    private static final boolean DEF_SETTINGS_MULTIPLE_FILES = false;
    private static final String DEF_SETTINGS_REFRESH_PERIOD = "5";
    private static final String DEF_SETTINGS_UNIT_SYSTEM = "false";
    private static final boolean DEF_SETTINGS_DEBUG = false;

    private static final String PREF_SERVICE_BOUND = "serviceBound";
    private static final String PREF_SERVICE_SPORT_ID = "sportId";
    private static final boolean DEF_SERVICE_BOUND = false;
    private static final int DEF_SERVICE_SPORT_ID = 0;

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public AppPreferences(Application application) {
        this.preferences = application.getSharedPreferences(APP_PREFERENCES_FILE, MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    public int getGender() {
        return preferences.getInt(PREF_PROFILE_GENDER, DEF_PROFILE_GENDER);
    }

    public void setGender(int gender) {
        editor.putInt(PREF_PROFILE_GENDER, gender);
        editor.apply();
    }

    public LocalDate getBirthDate() {
        String birthDate = preferences.getString(PREF_PROFILE_BIRTHDATE, now().toString());
        return LocalDate.parse(birthDate);
    }

    public void setBirthDate(LocalDate birthDate) {
        editor.putString(PREF_PROFILE_BIRTHDATE, birthDate.toString());
        editor.apply();
    }

    public float getWeight() {
        return preferences.getFloat(PREF_PROFILE_WEIGHT, DEF_PROFILE_WEIGHT_KG);
    }

    public float getDefaultWeight() {
        return DEF_PROFILE_WEIGHT_KG;
    }

    public void setWeight(float weight) {
        editor.putFloat(PREF_PROFILE_WEIGHT, weight);
        editor.apply();
    }

    public float getHeight() {
        return preferences.getFloat(PREF_PROFILE_HEIGHT, DEF_PROFILE_HEIGHT_M);
    }

    public void setHeight(float height ) {
        editor.putFloat(PREF_PROFILE_HEIGHT, height);
        editor.apply();
    }

    public float getDefaultHeight() {
        return DEF_PROFILE_HEIGHT_M;
    }

    public String getProfileUpdateDate() {
        return preferences.getString(PREF_PROFILE_UPDATE_DATE, now().toString());
    }

    public void setProfileUpdateDate(LocalDate updateDate) {
        editor.putString(PREF_PROFILE_UPDATE_DATE, updateDate.toString());
        editor.apply();
    }

    public boolean isAutoFinishOn() {
        return preferences.getBoolean(PREF_SETTINGS_AUTO_FINISH, DEF_SETTINGS_AUTO_FINISH);
    }

    public int getSamplesLimit() {
        String samplesLimit = preferences.getString(PREF_SETTINGS_SAMPLES_LIMIT, DEF_SETTINGS_SAMPLES_LIMIT);
        return parseInt(samplesLimit);
    }

    public int getSamplesOnMemory() {
        String samplesOnMemory = preferences.getString(PREF_SETTINGS_SAMPLES_ON_MEMORY, DEF_SETTINGS_SAMPLES_ON_MEMORY);
        return parseInt(samplesOnMemory);
    }

    public boolean isSaveOnSdOn() {
        return preferences.getBoolean(PREF_SETTINGS_SAVE_ON_SD, DEF_SETTINGS_SAVE_ON_SD);
    }

    public String getFileName() {
        return preferences.getString(PREF_SETTINGS_FILE_NAME, DEF_SETTINGS_FILE_NAME);
    }

    public boolean isMultipleFilesOn() {
        return preferences.getBoolean(PREF_SETTINGS_MULTIPLE_FILES, DEF_SETTINGS_MULTIPLE_FILES);
    }

    public int getRefreshPeriod() {
        String refreshPeriod = preferences.getString(PREF_SETTINGS_REFRESH_PERIOD, DEF_SETTINGS_REFRESH_PERIOD);
        return parseInt(refreshPeriod);
    }

    public boolean isUnitSystemImperial() {
        String unitSystem = preferences.getString(PREF_SETTINGS_UNIT_SYSTEM, DEF_SETTINGS_UNIT_SYSTEM);
        return parseBoolean(unitSystem);
    }

    public boolean isDebugOn() {
        return preferences.getBoolean(PREF_SETTINGS_DEBUG, DEF_SETTINGS_DEBUG);
    }

    public boolean isServiceBound() {
        return preferences.getBoolean(PREF_SERVICE_BOUND, DEF_SERVICE_BOUND);
    }

    public void setServiceBound(boolean serviceBound) {
        editor.putBoolean(PREF_SERVICE_BOUND, serviceBound);
        editor.apply();
    }

    public int getSportId() {
        return preferences.getInt(PREF_SERVICE_SPORT_ID, DEF_SERVICE_SPORT_ID);
    }

    public void setSportId(int sportId) {
        editor.putInt(PREF_SERVICE_SPORT_ID, sportId);
        editor.apply();
    }

}
