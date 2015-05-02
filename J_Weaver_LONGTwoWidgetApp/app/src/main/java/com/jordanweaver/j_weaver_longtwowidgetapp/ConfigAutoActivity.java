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

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by jordanweaver on 4/29/15.
 */
public class ConfigAutoActivity extends Activity {

    int widgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container,
                ConfigFlipFragment.newInstance(), ConfigFlipFragment.TAG).commit();

        Intent intent = getIntent();

        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preferance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

            Intent intent = new Intent(this, FlipWidgetService.class);
            startService(intent);

            Intent intent1 = new Intent();
            intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            setResult(RESULT_OK, intent1);

            ComponentName componentName1 = new ComponentName(this.getPackageName(),
                    AutoWidgetProvider.class.getName());
            int[] widgetIds1 = AppWidgetManager.getInstance(this).getAppWidgetIds(componentName1);
            AppWidgetManager.getInstance(this).notifyAppWidgetViewDataChanged(widgetIds1,
                    R.id.autoView);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
