package com.jordanweaver.j_weaver_broadcasting_labone;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 3/31/15.
 */
public class FileHelper {

    Context mContext;

    public FileHelper(Context context){
        this.mContext = context;
    }

    public ArrayList<SerializeObject> loadArray(){

        ArrayList<SerializeObject> fullArray = null;

        try {
            FileInputStream fis = mContext.openFileInput("array.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            fullArray = (ArrayList<SerializeObject>)ois.readObject();
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

        if(fullArray == null){
            fullArray = new ArrayList<>();
        }

        return fullArray;
    }

    public void onSave(ArrayList<SerializeObject> saveArray){

        try {
            Log.e("Saving Array", saveArray+"");
            FileOutputStream fos = mContext.openFileOutput("array.txt", Context.MODE_PRIVATE);
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
