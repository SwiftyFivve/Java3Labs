package com.jordanweaver.j_weaver_broadcasting_labone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by jordanweaver on 3/31/15.
 */
public class FormActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.listContainer,
                FormFragment.newInstance(), FormFragment.TAG).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActionsExtras.ACTION_UPDATE);
        registerReceiver(updateList, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            EditText first = (EditText) findViewById(R.id.firstInput);
            EditText last = (EditText) findViewById(R.id.lastInput);
            EditText age = (EditText) findViewById(R.id.ageInput);

            String firstText = first.getText().toString();
            String lastText = last.getText().toString();
            String ageText = age.getText().toString();

            if(!firstText.trim().equals("") &&
                    !lastText.trim().equals("") &&
                    !ageText.trim().equals("")){

                int mAge = Integer.parseInt(ageText);

                Intent intent = new Intent(ActionsExtras.ACTION_SAVE);
                intent.putExtra(ActionsExtras.EXTRA_FIRST, firstText);
                intent.putExtra(ActionsExtras.EXTRA_LAST, lastText);
                intent.putExtra(ActionsExtras.EXTRA_AGE, mAge);

                sendBroadcast(intent);


            }



            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    BroadcastReceiver updateList = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
            unregisterReceiver(updateList);
        }
    };

}
