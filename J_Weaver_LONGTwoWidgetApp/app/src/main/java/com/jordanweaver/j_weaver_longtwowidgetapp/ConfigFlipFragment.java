package com.jordanweaver.j_weaver_longtwowidgetapp;
//
//
//
//
//Jordan Weaver
//
//
//
//

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by jordanweaver on 5/1/15.
 */
public class ConfigFlipFragment extends PreferenceFragment {

    public static final String TAG = "ConfigFlipFragment.TAG";

    public static ConfigFlipFragment newInstance(){
        ConfigFlipFragment configFlipFragment = new ConfigFlipFragment();
        return configFlipFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.flip_preference);

    }

}
