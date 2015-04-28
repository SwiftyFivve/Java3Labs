package com.jordanweaver.j_weaver_collectionwidget_labseven;

import java.io.Serializable;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class IdiotScale implements Serializable {

    String mFirst;
    String mLast;
    int mScale;

    public IdiotScale(String first, String last, int scale) {
        this.mFirst = first;
        this.mLast = last;
        this.mScale = scale;
    }

    @Override
    public String toString() {
        return getFirst() + " " + getLast();
    }

    public String getFirst() {
        return mFirst;
    }

    public String getLast() {
        return mLast;
    }

    public int getScale() {
        return mScale;
    }

    public void setmFirst(String mFirst) {
        this.mFirst = mFirst;
    }

    public void setmLast(String mLast) {
        this.mLast = mLast;
    }

    public void setmScale(int mScale) {
        this.mScale = mScale;
    }
}
