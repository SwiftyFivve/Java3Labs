package com.jordanweaver.j_weaver_broadcasting_labone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 3/31/15.
 */
public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent startingIntent = getIntent();

        if(startingIntent.hasExtra(ActionsExtras.EXTRA_FIRST) &&
                startingIntent.hasExtra(ActionsExtras.EXTRA_LAST) &&
                startingIntent.hasExtra(ActionsExtras.EXTRA_AGE)) {

            String fName = startingIntent.getStringExtra(ActionsExtras.EXTRA_FIRST);
            String lName = startingIntent.getStringExtra(ActionsExtras.EXTRA_LAST);
            int age = startingIntent.getIntExtra(ActionsExtras.EXTRA_AGE, -1);

            Log.e("Worked", fName + " " + lName + " Age: " + age);

            getFragmentManager().beginTransaction().replace(R.id.listContainer,
                    DetailsFragment.newInstance(fName, lName, age), DetailsFragment.TAG).commit();

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActionsExtras.ACTION_UPDATE);
        registerReceiver(updateList, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            Intent startingIntent = getIntent();

            FileHelper helper = new FileHelper(this);

            ArrayList<SerializeObject> deleteObject = helper.loadArray();

            String fName = startingIntent.getStringExtra(ActionsExtras.EXTRA_FIRST);
            String lName = startingIntent.getStringExtra(ActionsExtras.EXTRA_LAST);
            int age = startingIntent.getIntExtra(ActionsExtras.EXTRA_AGE, -1);

            int counter = -1;

            for(int i=0; i< deleteObject.size(); i++){
                counter++;
                if(fName.equals(deleteObject.get(i).firstName) &&
                        lName.equals(deleteObject.get(i).lastName) &&
                        age == deleteObject.get(i).age){
                    deleteObject.remove(counter);
                }
            }


            helper.onSave(deleteObject);

            Intent intent = new Intent(ActionsExtras.ACTION_UPDATE);

            sendBroadcast(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    BroadcastReceiver updateList = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
            //unregisterReceiver(updateList);
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(updateList);
    }
}
