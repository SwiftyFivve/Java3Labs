package com.jordanweaver.j_weaver_broadcasting_labone;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.util.ArrayList;


public class MainActivity extends Activity implements MainFragment.showDetails{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().replace(R.id.listContainer,
                    MainFragment.newInstance(), MainFragment.TAG).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            Intent intent = new Intent(this, FormActivity.class);
            startActivityForResult(intent, RESULT_OK);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("Result Code", requestCode+"");

        if(resultCode == RESULT_OK) {
            getFragmentManager().beginTransaction().replace(R.id.listContainer,
                    MainFragment.newInstance(), MainFragment.TAG).commit();
        }


    }

    @Override
    public void passIntent(Intent _intent) {
        startActivityForResult(_intent, RESULT_OK);
    }
}
