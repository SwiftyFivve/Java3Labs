package com.jordanweaver.j_weaver_notifications_labthree;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by jordanweaver on 4/14/15.
 */
public class ServiceUtils extends IntentService {


    public static final int NOTIFICATION = 0x01001;

    public static final String EXTRA_CONTEXT = "com.fullsail.notification.jweaver.EXTRA_CONTEXT";

    public ServiceUtils() {
        super("ServiceUtils");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String urlString = "http://api.feedzilla.com/v1/categories/3/subcategories/65/articles.json";

        String results = "";

        Random random = new Random();

        ArticleObject articleObject;

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService
                (Context.CONNECTIVITY_SERVICE);

        if(manager != null){
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null){
                Log.e("No", "Network");
            } else {
                if (info.isConnected()) {
                    try {
                        URL url = new URL(urlString);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        InputStream is = connection.getInputStream();
                        results = IOUtils.toString(is);

                        if (results.equals("Error")) {

                        } else {
                            String _source;
                            String _title;
                            String _summary;
                            String _url;


                            try {
                                JSONObject mainObject = new JSONObject(results);
                                JSONArray articles = mainObject.getJSONArray("articles");
                                JSONObject randomArticle =
                                        articles.getJSONObject(random.nextInt(10) + 1);
                                _source = randomArticle.getString("source");
                                _title = randomArticle.getString("title");
                                _summary = randomArticle.getString("summary");
                                _url = randomArticle.getString("url");

                                Log.e("Connected", "Article: " + _title);

                                articleObject = new ArticleObject
                                        (_source, _title, _summary, _url);

                                connection.disconnect();

                                createNDisplayNotification(articleObject);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }



    public void createNDisplayNotification(ArticleObject object){


        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setLargeIcon(BitmapFactory.decodeResource
                (this.getResources(), R.drawable.ic_stat_name));
        builder.setContentTitle(object.title);
        builder.setContentText(object.summary);

        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.setBigContentTitle(object.title);
        style.bigText(object.summary);
        style.setSummaryText(object.source);
        builder.setStyle(style);

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.url));
        PendingIntent pWeb = PendingIntent.getActivity(
                this, 0, webIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pWeb);

        Intent intent = new Intent(SaveNUpdateReceiver.ACTION_SAVE);
        intent.putExtra(SaveNUpdateReceiver.EXTRA_OBJECT, object);
        PendingIntent pIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(android.R.drawable.ic_menu_save, "Save Article", pIntent);

        manager.notify(NOTIFICATION, builder.build());
    }

}
