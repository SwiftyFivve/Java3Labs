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
 * Created by jordanweaver on 4/29/15.
 */
public class FlipWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals(Strings.ACTION_NEXT)){
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.flip_widget_layout);
            rv.showNext(R.id.flipView);
            AppWidgetManager.getInstance(context).partiallyUpdateAppWidget(
                    intent.getIntExtra(Strings.EXTRA_WIDGET_ID, -1), rv);
        } else if (intent.getAction().equals(Strings.ACTION_PREVIOUS)){
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.flip_widget_layout);
            rv.showPrevious(R.id.flipView);
            AppWidgetManager.getInstance(context).partiallyUpdateAppWidget(
                    intent.getIntExtra(Strings.EXTRA_WIDGET_ID, -1), rv);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for(int i = 0; i<appWidgetIds.length; i++){
            int appWidgetId = appWidgetIds[i];

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.flip_widget_layout);


            Intent serviceIntent = new Intent(context, FlipWidgetService.class);
            rv.setRemoteAdapter(R.id.flipView, serviceIntent);

            Intent nextIntent = new Intent(Strings.ACTION_NEXT);
            nextIntent.putExtra(Strings.EXTRA_WIDGET_ID, appWidgetId);
            PendingIntent nextPending = PendingIntent.getBroadcast(context, 0, nextIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.nextButton, nextPending);

            Intent previousIntent = new Intent(Strings.ACTION_PREVIOUS);
            previousIntent.putExtra(Strings.EXTRA_WIDGET_ID, appWidgetId);
            PendingIntent previousPending = PendingIntent.getBroadcast(context, 0, previousIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.previousButton, previousPending);

            Intent intent1 = new Intent(context, ConfigFlipActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.settingsFlip, pendingIntent);

            Intent intent = new Intent(context, DetailsActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.flipView, pIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);

        }

    }
}
