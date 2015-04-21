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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MainActivity extends Activity implements GalleryFragment.showImg{

    public static final String ACTION_DELETE = "com.fullsail.jweaver.ACTION_DELETE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getFragmentManager().beginTransaction().replace(
                    R.id.container, GalleryFragment.newInstance(), GalleryFragment.TAG).commit();
        }

        Intent intent = new Intent(Intent.ACTION_CAMERA_BUTTON);
        sendBroadcast(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_DELETE);
        registerReceiver(notificationReceiver, filter);


        getFragmentManager().beginTransaction().replace(
                R.id.container, GalleryFragment.newInstance(), GalleryFragment.TAG).commit();


    }

    BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            if(intent.hasExtra(Intent.EXTRA_TEXT)) {
//
//                File dir = getExternalFilesDir(null);
//                File[] files = dir.listFiles();
//                File deletePhoto;
//
//                for(File i : files){
//                    if(i.getName().equals(intent.getStringExtra(Intent.EXTRA_TEXT))){
//                        deletePhoto = new File (Uri.fromFile(i).getPath());
//                        deletePhoto.delete();
//                    }
//                }
//
//            }


        }
    };




    @Override
    public void showGallery(Intent intent) {

        startActivity(intent);

    }
}
