package com.jordanweaver.j_weaver_mappingphotos_labfive;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Details_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_layout);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView title = (TextView)findViewById(R.id._title);
        TextView extra = (TextView)findViewById(R.id._extra);
        ImageView img = (ImageView)findViewById(R.id._img);

        Intent intent = getIntent();

        DatabaseHelper helper = new DatabaseHelper();
        ArrayList<LocationObject> displayObject = helper.loadArray(this);

        title.setText(displayObject.get(intent.getIntExtra(Intent.EXTRA_PHONE_NUMBER, -1)).title);
        extra.setText(displayObject.get(intent.getIntExtra(Intent.EXTRA_PHONE_NUMBER, -1)).extra);

        Uri uri = Uri.parse(displayObject.get(intent.getIntExtra(Intent.EXTRA_PHONE_NUMBER, -1)).photo);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap image = BitmapFactory.decodeFile(uri.getPath(), options);
        img.setImageBitmap(image);

    }

    //latitude
    //longitude
    //

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {

            DatabaseHelper helper = new DatabaseHelper();
            ArrayList<LocationObject> displayObject = helper.loadArray(this);

            Intent intent = getIntent();
            displayObject.remove(intent.getIntExtra(Intent.EXTRA_PHONE_NUMBER, -1));
            helper.saveObject(displayObject, this);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);
            return rootView;
        }
    }
}
