package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";

    public static DetailsFragment newInstance(){
        DetailsFragment detailsFragment = new DetailsFragment();
        return detailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_layout, container, false);
        return view;
    }
}
