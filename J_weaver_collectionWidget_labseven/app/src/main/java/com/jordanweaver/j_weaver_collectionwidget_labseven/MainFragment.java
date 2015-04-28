package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class MainFragment extends ListFragment {

    public static final String TAG = "MainFragment.TAG";

    private ArrayAdapter<IdiotScale> arrayAdapter;

    public static MainFragment newInstance(){
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        DataBaseHelper helper = new DataBaseHelper();
        ArrayList<IdiotScale> listArray = helper.loadArray(getActivity());

        Log.e("Worked", listArray.toString());

        arrayAdapter = new ArrayAdapter<IdiotScale>(getActivity(),
                android.R.layout.simple_list_item_1, listArray);

        setListAdapter(arrayAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DataBaseHelper helper = new DataBaseHelper();
        IdiotScale detailObject = helper.loadArray(getActivity()).get(position);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_OBJECT, detailObject);
        intent.putExtra(DetailsActivity.EXTRA_POSITION, position);
        startActivity(intent);

    }
}
