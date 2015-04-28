package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class DataBaseHelper {



    public ArrayList<IdiotScale> loadArray(Context context){

        ArrayList<IdiotScale> loadedArray = null;

        try {
            FileInputStream fis = context.openFileInput("idiot.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            loadedArray = (ArrayList<IdiotScale>) ois.readObject();
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

        if(loadedArray == null){
            loadedArray = new ArrayList<>();
        }
        return loadedArray;
    }


    public void saveObject(Context context, ArrayList<IdiotScale> saveArray){

        try {
            FileOutputStream fos = context.openFileOutput("idiot.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saveArray);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
