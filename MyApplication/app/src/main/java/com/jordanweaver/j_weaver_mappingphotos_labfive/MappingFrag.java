package com.jordanweaver.j_weaver_mappingphotos_labfive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/18/15.
 */
public class MappingFrag extends MapFragment implements
        GoogleMap.OnMapLongClickListener, GoogleMap.InfoWindowAdapter,
        GoogleMap.OnInfoWindowClickListener{

    private static String ARG_LATITUDE = "AddForm.ARG_LATITUDE";
    private static String ARG_LONGITUDE = "AddForm.ARG_LONGITUDE";

    public static final String TAG = "MappingFrag.TAG";




    public static MappingFrag newInstance(double _lat, double _long){
        MappingFrag mappingFrag = new MappingFrag();
        Bundle args = new Bundle();
        args.putDouble(ARG_LATITUDE, _lat);
        args.putDouble(ARG_LONGITUDE, _long);
        mappingFrag.setArguments(args);
        return mappingFrag;
    }


    @Override
    public void onResume() {
        super.onResume();

        GoogleMap map = getMap();

        loadMarkers(map);

        Bundle args = getArguments();

        Double _lat = args.getDouble(ARG_LATITUDE);
        Double _long = args.getDouble(ARG_LONGITUDE);

        map.setInfoWindowAdapter(this);
        map.setOnInfoWindowClickListener(this);
        map.setOnMapLongClickListener(this);


        MarkerOptions opt = new MarkerOptions();
        opt.position(new LatLng(_lat, _long));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(opt.getPosition(), 11));

    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        DatabaseHelper helper = new DatabaseHelper();
        ArrayList<LocationObject> loadedArray;
        loadedArray = helper.loadArray(getActivity());

        View view = getActivity().getLayoutInflater().
                inflate(R.layout.map_info_layout, null);
        LatLng latLng = marker.getPosition();

        String markerID = marker.getId().substring(1);
        int id = Integer.parseInt(markerID);

        TextView titleText = (TextView) view.findViewById(R.id.locationTitle);
        TextView extraText = (TextView) view.findViewById(R.id.locationExtra);
        ImageView imageView = (ImageView) view.findViewById(R.id.mapImg);

        titleText.setText(loadedArray.get(id).title);
        extraText.setText(loadedArray.get(id).extra);

        Uri uri = Uri.parse(loadedArray.get(id).photo);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap image = BitmapFactory.decodeFile(uri.getPath(), options);
        imageView.setImageBitmap(image);
        return view;

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        String markerID = marker.getId().substring(1);
        int id = Integer.parseInt(markerID);

        mChange.fragChange(null, id);

    }

    public interface ChangeFrag{
        public void fragChange(LatLng latLng, int id);
    }

    ChangeFrag mChange;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof ChangeFrag){
            mChange = (ChangeFrag)activity;
        } else {
            throw new IllegalArgumentException
                    ("Main Activity needs to implement ChangeFrag interface");
        }

    }


    @Override
    public void onMapLongClick(LatLng latLng) {

        Log.e("This Worked", latLng+"");

        mChange.fragChange(latLng, -1);

    }

    public void loadMarkers(GoogleMap map){
        DatabaseHelper helper = new DatabaseHelper();
        ArrayList<LocationObject> loadedArray;
        loadedArray = helper.loadArray(getActivity());

        if(loadedArray.size() != 0) {
            for (int i = 0; i < loadedArray.size(); i++) {

                MarkerOptions opt = new MarkerOptions();

                double _lat = Double.parseDouble(loadedArray.get(i).latitude);
                double _lng = Double.parseDouble(loadedArray.get(i).longitude);

                opt.position(new LatLng(_lat, _lng));

                opt.title(loadedArray.get(i).title);
                map.addMarker(opt);

            }
        }
    }



}
