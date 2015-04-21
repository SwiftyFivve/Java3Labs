package com.jordanweaver.j_weaver_imageduplicator_longterm1;
//
//
//
//
//
//Jordan Weaver
//
//
//
//
//

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jordanweaver on 4/18/15.
 */
public class ImageReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.hardware.action.NEW_PICTURE")){

            intent.setClass(context, NotificationService.class);
            context.startService(intent);
        }
    }
}
