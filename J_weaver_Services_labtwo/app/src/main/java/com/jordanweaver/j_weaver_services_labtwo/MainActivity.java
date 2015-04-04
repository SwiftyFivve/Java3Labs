package com.jordanweaver.j_weaver_services_labtwo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;


public class MainActivity extends Activity implements GridFragment.UpdateGrid{

    ArrayList<Image> imgArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Strings.ACTION_UPDATE);
        registerReceiver(receiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            if(imgArray == null){
                imgArray = new ArrayList<>();
            }

            Intent intent = new Intent(this, DownloadService.class);
            startService(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Strings.ACTION_UPDATE)){
            getFragmentManager().beginTransaction().replace(R.id.container,
                    GridFragment.newInstance(), GridFragment.TAG).commit();

        }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    public void gridImg(Intent intent) {
        startActivity(intent);
    }
}
