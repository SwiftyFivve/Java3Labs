package com.jordanweaver.j_weaver_mappingphotos_labfive;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/19/15.
 */
public class DatabaseHelper {


    public ArrayList<LocationObject> loadArray (Context mContext){

        ArrayList<LocationObject> savedObjects = null;

        try {
            FileInputStream fis = mContext.openFileInput("array.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            savedObjects = (ArrayList<LocationObject>)ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(savedObjects == null){
            savedObjects = new ArrayList<>();
        }

        return savedObjects;

    }


    public void saveObject(ArrayList<LocationObject> saveObjects, Context mContext){

        try {
            FileOutputStream fos = mContext.openFileOutput("array.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saveObjects);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
