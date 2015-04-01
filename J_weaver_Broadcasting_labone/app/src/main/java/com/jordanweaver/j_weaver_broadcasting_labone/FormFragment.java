package com.jordanweaver.j_weaver_broadcasting_labone;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Normalizer;

/**
 * Created by jordanweaver on 3/31/15.
 */
public class FormFragment extends Fragment {

    public static final String TAG = "FormFragment.TAG";

    public static FormFragment newInstance(){
        FormFragment formFragment = new FormFragment();
        return formFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_fragment, container, false);
        return view;
    }
}
