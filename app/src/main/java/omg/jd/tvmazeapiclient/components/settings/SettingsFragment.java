package omg.jd.tvmazeapiclient.components.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import omg.jd.tvmazeapiclient.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
