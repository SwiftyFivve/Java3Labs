package com.jordanweaver.billsplitter;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by jordanweaver on 5/13/15.
 */
public class LoginFragment extends Fragment {

    public static final String TAG = "LoginFragment.TAG";

    public static LoginFragment newInstance(){
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_frag, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageView logo =(ImageView)getActivity().findViewById(R.id.logoPhoto);
    }
}
