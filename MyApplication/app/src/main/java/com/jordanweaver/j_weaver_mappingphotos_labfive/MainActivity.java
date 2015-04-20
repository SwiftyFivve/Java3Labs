package com.jordanweaver.j_weaver_mappingphotos_labfive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity implements LocationListener, MappingFrag.ChangeFrag
{

    LocationManager mManager;
    Location mLocation;
    MappingFrag mappingFrag;

    public static final String EXTRA_LAT = "EXTRA_LAT";
    public static final String EXTRA_LNG = "EXTRA_LNG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {

            mappingFrag = new MappingFrag();

            mManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            mManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    10000,
                    0,
                    this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mLocation != null) {
            getFragmentManager().beginTransaction().replace(
                    R.id.container, MappingFrag.newInstance(mLocation.getLatitude(), mLocation.getLongitude()
                    ), MappingFrag.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_current_location) {

            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra(EXTRA_LAT, mLocation.getLatitude());
            intent.putExtra(EXTRA_LNG, mLocation.getLongitude());
            startActivityForResult(intent, RESULT_OK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {

        mManager.removeUpdates(this);

        mLocation = location;

        getFragmentManager().beginTransaction().replace(
            R.id.container, mappingFrag.newInstance(location.getLatitude(), location.getLongitude()
                ), mappingFrag.TAG).commit();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * End of Location
     */


    /**
     * Beginning of Fragments
     */
    @Override
    public void fragChange(LatLng latLng, int id) {

        if(latLng == null){
            Intent intent = new Intent(this, Details_Activity.class);
            intent.putExtra(Intent.EXTRA_PHONE_NUMBER, id);
            startActivity(intent);

        } else {

            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra(EXTRA_LAT, latLng.latitude);
            intent.putExtra(EXTRA_LNG, latLng.longitude);
            startActivity(intent);
        }

    }

    /**
     * End of Fragments
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){


        }
    }
}
