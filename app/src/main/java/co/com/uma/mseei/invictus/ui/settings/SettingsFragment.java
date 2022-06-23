package co.com.uma.mseei.invictus.ui.settings;

import static co.com.uma.mseei.invictus.R.xml.fragment_settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(fragment_settings, rootKey);
    }
}