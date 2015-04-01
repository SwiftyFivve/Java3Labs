package com.jordanweaver.j_weaver_broadcasting_labone;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jordanweaver on 4/1/15.
 */
public class DetailsFragment extends Fragment {

    public static final String TAG = "DetailsFragment.TAG";

    private static final String ARG_FIRST = "BottomFragment.ARG_FIRST";
    private static final String ARG_LAST = "BottomFragment.ARG_LAST";
    private static final String ARG_AGE = "BottomFragment.ARG_AGE";

    public static DetailsFragment newInstance(String _first, String _last, int _age){
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle profileBundle = new Bundle();
        profileBundle.putString(ARG_FIRST, _first);
        profileBundle.putString(ARG_LAST, _last);
        profileBundle.putInt(ARG_AGE, _age);
        detailsFragment.setArguments(profileBundle);
        return detailsFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle fragBundle = getArguments();

        TextView firstDisplay = (TextView) getActivity().findViewById(R.id.firstText);
        TextView lastDisplay = (TextView) getActivity().findViewById(R.id.lastText);
        TextView ageDisplay = (TextView) getActivity().findViewById(R.id.ageText);

        firstDisplay.setText(fragBundle.getString(ARG_FIRST));
        lastDisplay.setText(fragBundle.getString(ARG_LAST));

        String age = Integer.toString(fragBundle.getInt(ARG_AGE));

        ageDisplay.setText(age);
    }
}
