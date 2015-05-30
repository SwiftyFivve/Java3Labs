package com.jordanweaver.billsplitter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jordanweaver on 5/21/15.
 */
public class BillingFragment extends Fragment {

    public static final String TAG = "BillingFragment.TAG";

    public static BillingFragment newInstance(){
        BillingFragment billingFragment = new BillingFragment();
        return billingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.billing_layout, container, false);
        return view;
    }
}
