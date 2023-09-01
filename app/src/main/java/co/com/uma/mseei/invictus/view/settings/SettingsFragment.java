package co.com.uma.mseei.invictus.view.settings;

import static co.com.uma.mseei.invictus.R.xml.fragment_settings;
import static co.com.uma.mseei.invictus.model.AppPreferences.APP_PREFERENCES_FILE;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(APP_PREFERENCES_FILE);
        setPreferencesFromResource(fragment_settings, rootKey);
    }
}