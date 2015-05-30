package com.jordanweaver.billsplitter;

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
 * Created by jordanweaver on 5/18/15.
 */
public class LoginFileStorage {


    public LoginObject loadMethod(Context context){
        LoginObject userInfo = null;

        try {
            FileInputStream fis = context.openFileInput("login.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            userInfo = (LoginObject) ois.readObject();
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

        return userInfo;

    }


    public void saveMethod(Context context, LoginObject loginObject){

        try {
            FileOutputStream fos = context.openFileOutput("login.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(loginObject);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
