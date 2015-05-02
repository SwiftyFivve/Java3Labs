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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/29/15.
 */
public class RecipeFragment extends ListFragment {

    public static final String TAG = "RecipeFragment.TAG";

    public static RecipeFragment newInstance(ArrayList<RecipeObject> allRecipes){
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(Strings.EXTRA_SERIALIZABLE, allRecipes);
        recipeFragment.setArguments(args);
        return recipeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        ArrayList<RecipeObject> recipeObjects =
                (ArrayList<RecipeObject>) args.getSerializable(Strings.EXTRA_SERIALIZABLE);

        MainListAdapter baseAdapter = new MainListAdapter(getActivity(), recipeObjects);

        setListAdapter(baseAdapter);

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle args = getArguments();
        ArrayList<RecipeObject> objects =
                (ArrayList<RecipeObject>) args.getSerializable(Strings.EXTRA_SERIALIZABLE);

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Strings.EXTRA_SERIALIZABLE, objects.get(position));
        startActivity(intent);
    }

}
