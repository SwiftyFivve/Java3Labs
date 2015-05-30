package com.jordanweaver.billsplitter;

import android.content.Context;
import android.util.Log;

import com.parse.ParseObject;

import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 5/22/15.
 */
public class DataBaseHelper {


    public ArrayList<BillsObject> loadData(Context context, String fileName){
        ArrayList<BillsObject> loadedData = null;

        try {
            FileInputStream fis = context.openFileInput(fileName+".txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            loadedData = (ArrayList<BillsObject>) ois.readObject();
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

        return loadedData;
    }

    public void saveData(Context context, ArrayList<BillsObject> saveArray, String fileName){

        try {
            FileOutputStream fos = context.openFileOutput(fileName+".txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(saveArray);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void saveObject(Context context, ParseObject object, String fileName){

        try {
            FileOutputStream fos = context.openFileOutput(fileName+".txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ParseObject loadObject(Context context, String fileName){
        ParseObject loadedData = null;

        try {
            FileInputStream fis = context.openFileInput(fileName+".txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            loadedData = (ParseObject) ois.readObject();
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

        return loadedData;
    }


}
