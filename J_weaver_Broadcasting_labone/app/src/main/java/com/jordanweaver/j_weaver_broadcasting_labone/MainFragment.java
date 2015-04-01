package com.jordanweaver.j_weaver_broadcasting_labone;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 3/31/15.
 */
public class MainFragment extends ListFragment {

    public static final String TAG = "MainFragment.TAG";

    ArrayAdapter<SerializeObject> arrayAdapter;

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    public interface showDetails{
        public void passIntent(Intent _intent);
    }

    public showDetails mDetails;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof showDetails){
            mDetails = (showDetails)activity;
        } else {
            throw new IllegalArgumentException("Activity must implement showDetails interface");
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String firstName = arrayAdapter.getItem(position).firstName;
        String lastName = arrayAdapter.getItem(position).lastName;
        int age = arrayAdapter.getItem(position).age;

        Log.e("Worked", firstName + " " +lastName +" Age: " +age);

        Intent intent = new Intent(ActionsExtras.ACTION_VIEW);
        intent.putExtra(ActionsExtras.EXTRA_FIRST, firstName);
        intent.putExtra(ActionsExtras.EXTRA_LAST, lastName);
        intent.putExtra(ActionsExtras.EXTRA_AGE, age);

        mDetails.passIntent(intent);

    }

    @Override
    public void onResume() {
        super.onResume();

        FileHelper helper = new FileHelper(getActivity());
        ArrayList<SerializeObject> listArray = helper.loadArray();

        arrayAdapter = new
                ArrayAdapter<SerializeObject>(getActivity(),
                android.R.layout.simple_list_item_1, listArray);

        setListAdapter(arrayAdapter);
    }
}
