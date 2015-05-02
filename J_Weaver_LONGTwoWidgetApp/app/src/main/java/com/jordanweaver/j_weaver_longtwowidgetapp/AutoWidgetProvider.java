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

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by jordanweaver on 4/30/15.
 */
public class AutoWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int i=0; i<appWidgetIds.length; i++){

            int appWidgetId = appWidgetIds[i];

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.auto_widget_layout);

            Intent autoService = new Intent(context, AutoWidgetService.class);
            rv.setRemoteAdapter(R.id.autoView, autoService);

            Intent intent1 = new Intent(context, ConfigAutoActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.setting_button, pendingIntent);

            Intent intent = new Intent(context, DetailsActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.autoView, pIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);

        }

    }
}
