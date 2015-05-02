package com.jordanweaver.j_weaver_longtwowidgetapp;

//
//
//
//Jordan Weaver
//
//
//
//

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity1 extends Activity implements ActionBar.OnNavigationListener,
        NetworkUtilsAsync.TaskComplete {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity1);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[]{
                                getString(R.string.title_feed1),
                                getString(R.string.title_favorite2),
                        }),
                this);


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        if(position == 0){
            NetworkUtilsAsync connectNetwork = new NetworkUtilsAsync(this, this);
            connectNetwork.execute("http://api.yummly.com/v1/api/recipes?_app_id=2c32f872&_" +
                    "app_key=983dbe6253fe7dbcee128668ec143eee&q=&requirePictures=true&" +
                    "maxResult=25&start=10");
        } else {

            getFragmentManager().beginTransaction().replace(R.id.container,
                    FavoritesFragment.newInstance(), FavoritesFragment.TAG).commit();

        }
        return true;
    }

    @Override
    public void imgInterface(ArrayList<RecipeObject> allRecipes) {
        getFragmentManager().beginTransaction().replace(R.id.container,
                RecipeFragment.newInstance(allRecipes), RecipeFragment.TAG).commit();

        ComponentName componentName = new ComponentName(this.getPackageName(),
                FlipWidgetProvider.class.getName());


        int[] widgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(componentName);


        AppWidgetManager.getInstance(this).notifyAppWidgetViewDataChanged(widgetIds,
                R.id.title);
    }
}
