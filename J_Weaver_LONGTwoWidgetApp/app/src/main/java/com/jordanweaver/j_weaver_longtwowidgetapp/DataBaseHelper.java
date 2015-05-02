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

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/29/15.
 */
public class DataBaseHelper {


    public ArrayList<RecipeObject> loadRecipes(Context context){
        ArrayList<RecipeObject> loadedRecipes = null;

        try {
            FileInputStream fis = context.openFileInput("recipes.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            loadedRecipes = (ArrayList<RecipeObject>) ois.readObject();
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(loadedRecipes == null){
            loadedRecipes = new ArrayList<>();
        }

        return loadedRecipes;
    }

    public void saveRecipes(Context context, ArrayList<RecipeObject> saveArray){

        try {
            FileOutputStream fos = context.openFileOutput("recipes.txt", Context.MODE_PRIVATE);
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
