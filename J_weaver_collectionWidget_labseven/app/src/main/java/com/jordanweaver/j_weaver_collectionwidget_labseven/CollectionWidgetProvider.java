package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class CollectionWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0; i<appWidgetIds.length; i++){
            int appWidgetId = appWidgetIds[i];

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            Intent formIntent = new Intent(context, FormActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, formIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.addButton, pendingIntent);

            Intent serviceIntent = new Intent(context, CollectionWidgetService.class);
            rv.setRemoteAdapter(R.id.idiot_list, serviceIntent);

            rv.setEmptyView(R.id.idiot_list, R.id.empty);

            Intent detailsIntent = new Intent(context, DetailsActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, detailsIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.idiot_list, pIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);

        }
    }
}
