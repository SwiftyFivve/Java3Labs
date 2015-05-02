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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/28/15.
 */
public class NetworkUtilsAsync extends AsyncTask<String, Void, ArrayList<RecipeObject>> {


    Context mContext;
    TaskComplete theData;

    public NetworkUtilsAsync(Context mContext, TaskComplete mData){
        this.mContext = mContext;
        this.theData = mData;
    }

    public interface TaskComplete{
        public void imgInterface(ArrayList<RecipeObject> allRecipes);
    }

    @Override
    protected ArrayList<RecipeObject> doInBackground(String... params) {

        ArrayList<RecipeObject> allRecipes = runNetwork(params);

        return allRecipes;
    }

    @Override
    protected void onPostExecute(ArrayList<RecipeObject> allRecipes) {
        super.onPostExecute(allRecipes);

        theData.imgInterface(allRecipes);

    }


    public ArrayList<RecipeObject> runNetwork(String... params){

        String results = "";

        ArrayList<RecipeObject> allRecipes = new ArrayList<>();

        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(manager != null){
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null) {

            } else{
                Log.i("Network", "Device is connected");
                if (info.isConnected()) {

                    try {
                        URL url = new URL(params[0]);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        InputStream is = connection.getInputStream();
                        results = IOUtils.toString(is);

                        connection.disconnect();

                        if (results.equals("Error")) {

                        } else {

                            String name;
                            String img;
                            int rating;
                            byte[] byteImg;

                            JSONObject mainJSON = new JSONObject(results);
                            JSONArray recipeMatches = mainJSON.getJSONArray("matches");

                            for(int i=0; i<recipeMatches.length(); i++){
                                JSONObject matches = recipeMatches.getJSONObject(i);
                                JSONObject outsideIMG = matches.getJSONObject("imageUrlsBySize");
                                img = outsideIMG.getString("90");
                                name = matches.getString("recipeName");
                                rating = matches.getInt("rating");

                                StringBuilder ingredients = new StringBuilder();

                                JSONArray ingrdnts = matches.getJSONArray("ingredients");

                                for(int j=0; j<ingrdnts.length(); j++){
                                    if(j == ingrdnts.length()){
                                        ingredients.append(ingrdnts.get(j));
                                    }
                                    ingredients.append(ingrdnts.get(j) + ", ");
                                }

                                byteImg = IOUtils.toByteArray(new URL(img));

                                RecipeObject recipeObject = new RecipeObject(name, img,
                                        ingredients.toString(), rating, byteImg);

                                if(allRecipes.size()!=0){
                                    allRecipes.add(allRecipes.size(), recipeObject);
                                } else {
                                    allRecipes.add(0, recipeObject);
                                }

                            }
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

        } else {
        }

        return allRecipes;

    }

}
