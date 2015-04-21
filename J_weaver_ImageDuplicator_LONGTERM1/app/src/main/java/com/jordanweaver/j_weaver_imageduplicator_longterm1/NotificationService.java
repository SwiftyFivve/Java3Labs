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

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jordanweaver on 4/20/15.
 */
public class NotificationService extends IntentService {


    public static final int NOTIFICATION = 0x01001;

    public NotificationService() {
        super("NotificationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        String photoString = getFileUri(intent);

        createNDisplayNot(photoString);

    }

    public void createNDisplayNot(String photoPath){


        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
        Date date = new Date(System.currentTimeMillis());
        String imageName = sdf.format(date);


        Bitmap img = BitmapFactory.decodeFile(photoPath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 20, baos);

        DatabaseHelper helper = new DatabaseHelper();

        helper.savePhotos(baos.toByteArray(), imageName, this);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_stat_picture);

        byte[] loadedImg = helper.loadPhotos(imageName, this);

        builder.setLargeIcon(BitmapFactory.decodeByteArray(loadedImg, 0, loadedImg.length));
        builder.setContentTitle("New Picture");

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("Click to view");
        style.bigPicture(BitmapFactory.decodeByteArray(loadedImg, 0, loadedImg.length));


        File dir = getExternalFilesDir(null);
        File[] allFiles = dir.listFiles();

        Uri theFile = Uri.fromFile(allFiles[allFiles.length - 1]);

        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(theFile, "image/jpeg");
        PendingIntent pView = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pView);

        Intent deleteIntent = new Intent(MainActivity.ACTION_DELETE);
        deleteIntent.putExtra(Intent.EXTRA_TEXT, photoPath);
        PendingIntent pDelete = PendingIntent.getBroadcast(
                this, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(photoPath));
        PendingIntent pShare = PendingIntent.getActivity(
                this, 0, shareIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(android.R.drawable.ic_menu_delete, "Delete", pDelete);
        builder.addAction(android.R.drawable.ic_menu_share, "Share", pShare);


        builder.setStyle(style);

        manager.notify(NOTIFICATION, builder.build());

    }

    public String getFileUri(Intent capturedIntent) {

        Uri selectedImage = capturedIntent.getData();
        String[] pathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage, pathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(pathColumn[0]);
        String imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;
    }



}
