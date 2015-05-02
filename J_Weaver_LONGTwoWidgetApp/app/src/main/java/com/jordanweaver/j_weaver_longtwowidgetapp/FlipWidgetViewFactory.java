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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/29/15.
 */
public class FlipWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private ArrayList<RecipeObject> mRecipeObjects;

    public FlipWidgetViewFactory(Context context){
        mContext = context;
        mRecipeObjects = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        if(preferences.getString("PREF_FEED", "1").equals("0")) {

            NetworkUtilsAsync networkUtilsAsync = new NetworkUtilsAsync(mContext, null);

            ArrayList<RecipeObject> objects;

            objects =
                    networkUtilsAsync.runNetwork("http://api.yummly.com/v1/api/recipes?_app_id=2c32f872&_" +
                            "app_key=983dbe6253fe7dbcee128668ec143eee&q=&requirePictures=true&" +
                            "maxResult=10&start=10");

            if(objects!=null){
                mRecipeObjects = objects;
            }

        } else if (preferences.getString("PREF_FEED", "1").equals("1")){

            DataBaseHelper helper = new DataBaseHelper();
            mRecipeObjects = helper.loadRecipes(mContext);

        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipeObjects.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.flip_widget_view);

        if(mRecipeObjects!=null) {

            RecipeObject object = mRecipeObjects.get(position);

            rv.setImageViewBitmap(R.id.flip_image, BitmapFactory.decodeByteArray(object.byteImg, 0,
                    object.byteImg.length));

            rv.setTextViewText(R.id.title, object.mName);


            Intent intent = new Intent();
            intent.putExtra(Strings.EXTRA_SERIALIZABLE, object);
            rv.setOnClickFillInIntent(R.id.flip_image, intent);
        } else {
            rv.setTextViewText(R.id.title, "NO NETWORK CONNECTION");
        }

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
