package com.jordanweaver.j_weaver_notifications_labthree;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    public static final String ACTION_UPDATE = "com.fullsail.jweaver.Notifications.ACTION_UPDATE";


    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState == null){

            getFragmentManager().beginTransaction().replace(
                    R.id.container, ArticleFragment.newInstance(), ArticleFragment.TAG).commit();

        }

        Intent intent = new Intent(this, ServiceUtils.class);
        PendingIntent pIntent = PendingIntent.getService
                (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager mgr = (AlarmManager)getSystemService(ALARM_SERVICE);
        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+ 5000, 5000, pIntent);


    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_UPDATE)){

                ArticleFragment articleFragment = new ArticleFragment();

                getFragmentManager().beginTransaction().replace(
                        R.id.container, articleFragment.newInstance(), articleFragment.TAG).commit();

            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
