package com.jordanweaver.billsplitter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jordanweaver on 5/21/15.
 */
public class AddFriendsFragment extends Fragment {

    public static final String TAG = "AddFriendsFragment.TAG";

    public static AddFriendsFragment newInstance(){
        AddFriendsFragment addFriendsFragment = new AddFriendsFragment();
        return addFriendsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_friends_fragment, container, false);
        return view;
    }

}
