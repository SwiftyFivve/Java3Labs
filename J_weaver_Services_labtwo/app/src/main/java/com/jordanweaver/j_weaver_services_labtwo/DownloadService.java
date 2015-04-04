package com.jordanweaver.j_weaver_services_labtwo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jordanweaver on 4/2/15.
 */
public class DownloadService extends IntentService {

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        String[] urlString = {"http://i.imgur.com/MgmzpOJ.jpg", "http://i.imgur.com/hNFO7tn.jpg",
                "http://i.imgur.com/K6b7OTK.jpg", "http://i.imgur.com/VZmFngH.jpg",
                "http://i.imgur.com/ptE5z9u.jpg", "http://i.imgur.com/4QKO8Up.jpg",
                "http://i.imgur.com/Vm2UdDH.jpg", "http://i.imgur.com/C040ctB.jpg",
                "http://i.imgur.com/MScR8za.jpg", "http://i.imgur.com/tM1bsAH.jpg",
                "http://i.imgur.com/fS1lKZx.jpg", "http://i.imgur.com/h8e5rBX.jpg",
                "http://i.imgur.com/KBtUxzq.jpg", "http://i.imgur.com/wYXWJZz.jpg",
                "http://i.imgur.com/LOUwRC4.jpg", "http://i.imgur.com/7ZSQfIu.jpg",
                "http://i.imgur.com/XLJiKqp.jpg", "http://i.imgur.com/nXVLE9W.jpg",
                "http://i.imgur.com/HYQuj4b.jpg", "http://i.imgur.com/R8YIb8d.jpg",
                "http://i.imgur.com/cLv3TVc.jpg", "http://i.imgur.com/f7pMMdA.jpg",
                "http://i.imgur.com/Dl1aIHV.jpg", "http://i.imgur.com/UE3ng26.jpg",
                "http://i.imgur.com/1oyYfr0.jpg", "http://i.imgur.com/YSJ28fr.jpg",
                "http://i.imgur.com/Ey39hl5.jpg", "http://i.imgur.com/HAnhjCI.jpg",
                "http://i.imgur.com/En3J4ZF.jpg", "http://i.imgur.com/wr65Geg.jpg"};
        for (int i = 0; i < urlString.length; i++) {
            FileManager helper = new FileManager(this);

            byte[] savedBytes = helper.onLoad(i + "");

            if (savedBytes == null) {

                try {

                    String fileName = Integer.toString(i);

                    Log.e("URL", urlString[i]);

                    URL url = new URL(urlString[i]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    InputStream is = connection.getInputStream();
                    byte[] data = IOUtils.toByteArray(is);
                    is.close();

                    connection.disconnect();


                    if (helper.onLoad(fileName) == null) {
                        Log.e("Saved", data + "");
                        Log.e("File Name", fileName);
                        helper.onSave(data, fileName);
                    }

                    Intent updateIntent = new Intent(Strings.ACTION_UPDATE);
                    updateIntent.putExtra(Strings.EXTRA_IMG_STRING, data);
                    sendBroadcast(updateIntent);


                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }

            } else {

                Intent updateIntent = new Intent(Strings.ACTION_UPDATE);
                updateIntent.putExtra(Strings.EXTRA_IMG_STRING, savedBytes);
                sendBroadcast(updateIntent);

            }

        }



    }
}
