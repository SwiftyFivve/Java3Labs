package com.jordanweaver.j_weaver_services_labtwo;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jordanweaver on 4/2/15.
 */
public class FileManager {

    Context mContext;

    public FileManager(Context context){
        this.mContext = context;
    }


    public void onSave(byte[] img, String _name){

        try {
            File external = mContext.getExternalFilesDir(null);
            File file = new File(external, _name);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(img);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public byte[] onLoad(String _name){
        byte[] img = null;

        try {
            File external = mContext.getExternalFilesDir(null);
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




}
