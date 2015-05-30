package com.jordanweaver.billsplitter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by jordanweaver on 5/25/15.
 */
public class ContactsFragment extends Fragment {

    public static final String TAG = "ContactsFragment.TAG";
    private SimpleCursorAdapter adapter;

    public static ContactsFragment newInstance(){
        ContactsFragment contactsFragment = new ContactsFragment();
        return contactsFragment;
    }


}
