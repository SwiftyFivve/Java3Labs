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

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jordanweaver on 4/29/15.
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";

    public static DetailsFragment newInstance(){
        DetailsFragment detailsFragment = new DetailsFragment();
        return detailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_frag_layout, container, false);
        return view;
    }

}
