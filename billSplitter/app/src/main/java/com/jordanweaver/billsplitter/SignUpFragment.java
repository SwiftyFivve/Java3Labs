package com.jordanweaver.billsplitter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jordanweaver on 5/13/15.
 */
public class SignUpFragment extends Fragment {

    public static final String TAG = "SignUpFragment.TAG";

    public static SignUpFragment newInstance(){
        SignUpFragment signUpFragment = new SignUpFragment();
        return signUpFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_frag, container, false);
        return view;
    }
}
