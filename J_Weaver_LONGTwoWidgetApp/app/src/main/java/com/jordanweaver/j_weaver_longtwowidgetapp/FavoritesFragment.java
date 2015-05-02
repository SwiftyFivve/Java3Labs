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

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/30/15.
 */
public class FavoritesFragment extends ListFragment {

    public static final String TAG = "FavoritesFragment.TAG";

    public static FavoritesFragment newInstance(){
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        return favoritesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBaseHelper helper = new DataBaseHelper();

        ArrayList<RecipeObject> favoritesArray = helper.loadRecipes(getActivity());

        MainListAdapter baseAdapter = new MainListAdapter(getActivity(), favoritesArray);

        setListAdapter(baseAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DataBaseHelper helper = new DataBaseHelper();
        RecipeObject detailsObject = helper.loadRecipes(getActivity()).get(position);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Strings.EXTRA_SERIALIZABLE, detailsObject);
        startActivity(intent);
    }
}
