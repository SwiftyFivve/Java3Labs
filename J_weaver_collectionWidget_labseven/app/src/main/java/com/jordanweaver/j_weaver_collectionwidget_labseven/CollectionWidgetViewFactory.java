package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class CollectionWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    private ArrayList<IdiotScale> mIdiotList;

    public CollectionWidgetViewFactory(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {

        DataBaseHelper helper = new DataBaseHelper();

        mIdiotList = helper.loadArray(mContext);



    }

    @Override
    public void onDataSetChanged() {


        DataBaseHelper helper = new DataBaseHelper();

        mIdiotList = helper.loadArray(mContext);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIdiotList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        IdiotScale idiot = mIdiotList.get(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_layout);

        rv.setTextViewText(R.id.name, idiot.getFirst() + " " + idiot.getLast());
        rv.setTextViewText(R.id.idiot, Integer.toString(
                idiot.getScale()) + "/10 on the Idiot Scale");

        Intent detailsIntent = new Intent(mContext, DetailsActivity.class);
        detailsIntent.putExtra(DetailsActivity.EXTRA_OBJECT, idiot);
        detailsIntent.putExtra(DetailsActivity.EXTRA_POSITION, position);
        rv.setOnClickFillInIntent(R.id.item_layout, detailsIntent);

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
        return false;
    }
}
