package com.jordanweaver.j_weaver_notifications_labthree;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/15/15.
 */
public class SaveNUpdateReceiver extends BroadcastReceiver {


    public static final String ACTION_SAVE = "com.fullsail.jweaver.Notifications.ACTION_SAVE";
    public static final String EXTRA_OBJECT = "com.fullsail.jweaver.Notifications.EXTRA_OBJECT";


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_SAVE)){
            if(intent.hasExtra(EXTRA_OBJECT)){

                DataBaseHelper helper = new DataBaseHelper();

                ArrayList<ArticleObject> allArticles = helper.loadArray(context);

                Log.e("Got here", allArticles.toString());

                ArticleObject intentObject = (ArticleObject)
                        intent.getSerializableExtra(EXTRA_OBJECT);

                if(allArticles.size() == 0){
                    allArticles.add(0, intentObject);
                } else {
                    allArticles.add(allArticles.size(), intentObject);
                }

                helper.saveArray(allArticles, context);

                Intent updateIntent = new Intent(MainActivity.ACTION_UPDATE);

                context.sendBroadcast(updateIntent);


            }

        }

    }
}
