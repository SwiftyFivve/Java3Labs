package com.jordanweaver.j_weaver_mappingphotos_labfive;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by jordanweaver on 4/18/15.
 */
public class LocationObject implements Serializable{

    String title;
    String extra;
    String photo;
    String latitude;
    String longitude;


    public LocationObject(String title, String extra, String photo, String latitude, String longitude) {
        this.title = title;
        this.extra = extra;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
