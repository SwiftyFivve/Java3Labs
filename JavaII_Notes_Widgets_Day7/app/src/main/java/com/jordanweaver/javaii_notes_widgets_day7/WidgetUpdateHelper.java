package com.jordanweaver.javaii_notes_widgets_day7;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by jordanweaver on 4/23/15.
 */
public class WidgetUpdateHelper {

    public static void updateWidget(Context context, int widgetId,
                                    AppWidgetManager appWidgetManager){

        if(appWidgetManager == null){
            appWidgetManager = AppWidgetManager.getInstance(context);
        }

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, ConfigActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        rv.setOnClickPendingIntent(R.id.widget_image, pIntent);

        appWidgetManager.updateAppWidget(widgetId, rv);


    }

}
