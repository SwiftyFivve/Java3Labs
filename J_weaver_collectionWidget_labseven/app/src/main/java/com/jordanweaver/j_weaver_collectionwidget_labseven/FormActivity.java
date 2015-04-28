package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class FormActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container, FormFragment.newInstance(),
                FormFragment.TAG).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

            DataBaseHelper helper = new DataBaseHelper();

            ArrayList<IdiotScale> middleArray = helper.loadArray(this);

            EditText firstText = (EditText)findViewById(R.id.firstInput);
            EditText lastText = (EditText)findViewById(R.id.lastInput);
            EditText idiotNum = (EditText)findViewById(R.id.idiotNumInput);


            String firstName = firstText.getText().toString();
            String lastName = lastText.getText().toString();
            String idiot = idiotNum.getText().toString();

            if(!firstName.trim().equals("") && !lastName.trim().equals("") &&
                    !idiot.trim().equals("")) {

                IdiotScale idiotObject = new IdiotScale(firstName, lastName,
                        Integer.parseInt(idiot));

                if (middleArray.size() != 0) {
                    middleArray.add(middleArray.size(), idiotObject);
                } else {
                    middleArray.add(0, idiotObject);
                }

                helper.saveObject(this, middleArray);

                ComponentName componentName = new ComponentName(this.getPackageName(),
                        CollectionWidgetProvider.class.getName());


                int[] widgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(componentName);


                AppWidgetManager.getInstance(this).notifyAppWidgetViewDataChanged(widgetIds,
                        R.id.idiot_list);

            }

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
