package com.jordanweaver.j_weaver_longtwowidgetapp;
//
//
//
//
//Jordan Weaver
//
//
//
//

import java.io.Serializable;

/**
 * Created by jordanweaver on 4/30/15.
 */
public class RecipeObject implements Serializable{

    String mName;
    String mImg;
    String mIngredients;
    int mRating;
    byte[] byteImg;


    public RecipeObject(String name, String img, String ingredients, int rating, byte[] byteImg) {
        this.mName = name;
        this.mImg = img;
        this.mIngredients = ingredients;
        this.mRating = rating;
        this.byteImg = byteImg;
    }

    public String getName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setmIngredients(String mIngredients) {
        this.mIngredients = mIngredients;
    }

    public int getRating() {
        return mRating;
    }

    public void setmRating(int mRating) {
        this.mRating = mRating;
    }

    public byte[] getByteImg() {
        return byteImg;
    }

    public void setByteImg(byte[] byteImg) {
        this.byteImg = byteImg;
    }
}
