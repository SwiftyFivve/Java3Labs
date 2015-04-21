package com.jordanweaver.j_weaver_imageduplicator_longterm1;
//
//
//
//
//
//Jordan Weaver
//
//
//
//
//

import android.content.Context;
import android.graphics.Bitmap;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/18/15.
 */
public class DatabaseHelper {


    public byte[] loadPhotos(String _name, Context context){
        byte[] img = null;

        try {
            File external = context.getExternalFilesDir(null);
            File file = new File(external, _name);
            FileInputStream fis = new FileInputStream(file);
            img = IOUtils.toByteArray(fis);
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return img;
    }



    public void savePhotos(byte[] bitmap, String fileName, Context context){

        try {
            File external = context.getExternalFilesDir(null);
            File file = new File(external, fileName);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmap);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
