package com.jordanweaver.j_weaver_notifications_labthree;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/15/15.
 */
public class DataBaseHelper {


    public static ArrayList<ArticleObject> loadArray(Context mContext){

        ArrayList<ArticleObject> savedArticles = null;

        try {
            FileInputStream fis = mContext.openFileInput("articles.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            savedArticles = (ArrayList<ArticleObject>)ois.readObject();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(savedArticles == null){
            return savedArticles = new ArrayList<>();
        }

        return savedArticles;
    }


    public static void saveArray(ArrayList<ArticleObject> articles, Context mContext){

        ArrayList<ArticleObject> saveArray = articles;

        try {
            FileOutputStream fos = mContext.openFileOutput("articles.txt", Context.MODE_PRIVATE);
            Log.e("Saved", saveArray.toString());
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
