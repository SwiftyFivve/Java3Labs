package com.jordanweaver.billsplitter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by jordanweaver on 5/25/15.
 */
public class ContactSelectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.container,
                ContactsFragment.newInstance(), ContactsFragment.TAG);
    }
}
