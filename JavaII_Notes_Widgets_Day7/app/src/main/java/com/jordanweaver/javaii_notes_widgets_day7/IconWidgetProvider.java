package com.jordanweaver.javaii_notes_widgets_day7;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by jordanweaver on 4/23/15.
 */
public class IconWidgetProvider extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for(int i = 0; i<appWidgetIds.length; i++){

            int widgetId = appWidgetIds[i];

            WidgetUpdateHelper.updateWidget(context, widgetId, appWidgetManager);

        }
    }
}
