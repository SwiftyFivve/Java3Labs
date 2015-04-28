package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class DetailsActivity extends Activity {

    public static final String EXTRA_OBJECT = "DetailsActivity.EXTRA_OBJECT";
    public static final String EXTRA_POSITION = "DetailsActivity.EXTRA_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container,
                DetailsFragment.newInstance(), DetailsFragment.TAG).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView firstText = (TextView) findViewById(R.id.firstText);
        TextView lastText = (TextView) findViewById(R.id.lastText);
        TextView idiotText = (TextView) findViewById(R.id.idiotText);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_OBJECT)){
            IdiotScale setTextObject = (IdiotScale) intent.getSerializableExtra(EXTRA_OBJECT);

            firstText.setText(setTextObject.getFirst());
            lastText.setText(setTextObject.getLast());
            String iNum = Integer.toString(setTextObject.getScale());
            idiotText.setText(iNum);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {

            Intent intent = getIntent();

            if(intent.hasExtra(EXTRA_POSITION)){
                DataBaseHelper helper = new DataBaseHelper();

                ArrayList<IdiotScale> deleteItem = helper.loadArray(this);

                deleteItem.remove(intent.getIntExtra(EXTRA_POSITION, -1));

                helper.saveObject(this, deleteItem);

                ComponentName componentName = new ComponentName(this.getPackageName(),
                        CollectionWidgetProvider.class.getName());


                int[] widgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(componentName);


                AppWidgetManager.getInstance(this).notifyAppWidgetViewDataChanged(widgetIds,
                        R.id.idiot_list);

                finish();

            }



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
