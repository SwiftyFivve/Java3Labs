package com.jordanweaver.javaii_notes_widgets_day7;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by jordanweaver on 4/23/15.
 */
public class ConfigActivity extends Activity implements View.OnClickListener {

    int widgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        findViewById(R.id.update_button).setOnClickListener(this);

        Intent intent = getIntent();
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        if(widgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }

    }

    private void saveWidget(){
        //TODO: Update Our Widget

        WidgetUpdateHelper.updateWidget(this, widgetId, null);


        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onClick(View v) {
        saveWidget();
    }
}
