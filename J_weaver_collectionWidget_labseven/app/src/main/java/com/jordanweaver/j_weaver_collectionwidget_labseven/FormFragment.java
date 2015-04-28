package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class FormFragment extends Fragment {

    public static final String TAG = "FormFragment.TAG";

    public static FormFragment newInstance(){
        FormFragment formFragment = new FormFragment();
        return formFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_layout, container, false);
        return view;
    }




}
