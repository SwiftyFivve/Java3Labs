package com.jordanweaver.billsplitter;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.parse.ParseUser;

/**
 * Created by jordanweaver on 5/25/15.
 */
public class SettingFrag extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    public static final String TAG = "SettingFrag.TAG";
    public static SettingFrag newInstance(){
        SettingFrag fragment = new SettingFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferencescreen);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Preference preference = findPreference("PREF_CLICKABLE");
        preference.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        ParseUser.logOut();

//        Intent intent = new Intent(getActivity(), MainActivity.class);
//        startActivity(intent);
//        getActivity().finish();

        ParseUser currentUser = ParseUser.getCurrentUser();



        return true;
    }
}
