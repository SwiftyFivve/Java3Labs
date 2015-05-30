package com.jordanweaver.billsplitter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordanweaver on 5/27/15.
 */
public class ParseHelper {

    public void loadTransactions(final Context context){

        // Double query
        // sort by whether users number is in 'receiver' or 'sender'
        // 'sender' goes in pending
        // 'receiver' goes in bills
        //

        List<ParseQuery<ParseObject>> queries = new ArrayList<>();

        ParseUser currentUser = ParseUser.getCurrentUser();
        final String number = currentUser.getString("phone");

        ParseQuery<ParseObject> querySender = ParseQuery.getQuery("PrivateTransactionData");
        querySender.whereEqualTo("sender", number);
        queries.add(querySender);

        ParseQuery<ParseObject> queryReceiver = ParseQuery.getQuery("PrivateTransactionData");
        queryReceiver.whereEqualTo("receiver", number);
        queries.add(queryReceiver);


        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);

        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, ParseException e) {
                if(e == null){

                    ArrayList<BillsObject> billsArray = new ArrayList<BillsObject>();
                    ArrayList<BillsObject> pendingArray = new ArrayList<BillsObject>();

                    for(int i=0; i<parseObjects.size(); i++){

                        Log.e("Sender", parseObjects.get(i).get("sender")+"");
                        Log.e("Number check:", number);

                        if(parseObjects.get(i).getString("sender").equals(number)){
                            ParseObject parseObject = parseObjects.get(i);

                            BillsObject object = new BillsObject(parseObject.getString("receiver"),
                                    parseObject.getString("sender"), parseObject.getString("due"),
                                    parseObject.getString("amount"), parseObject.getString("message"),
                                    parseObject.getObjectId());

                            pendingArray.add(object);

                            Log.e("Query worked: pending ", pendingArray.toString());

                        } else if(parseObjects.get(i).getString("receiver").equals(number)){
                            ParseObject parseObject = parseObjects.get(i);

                            BillsObject object = new BillsObject(parseObject.getString("receiver"),
                                    parseObject.getString("sender"), parseObject.getString("due"),
                                    parseObject.getString("amount"), parseObject.getString("message"),
                                    parseObject.getObjectId());

                            billsArray.add(object);
                            Log.e("Query worked: bills ", billsArray.toString());

                        }
                    }
                    DataBaseHelper helper = new DataBaseHelper();
                    helper.saveData(context, billsArray, "bills");
                    helper.saveData(context, pendingArray, "pending");


                }else{
                    return;
                }

            }
        });
    }



}
