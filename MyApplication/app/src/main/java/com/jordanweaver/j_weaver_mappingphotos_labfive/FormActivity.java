package com.jordanweaver.j_weaver_mappingphotos_labfive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jordanweaver on 4/19/15.
 */
public class FormActivity extends Activity implements AddForm.CameraSnap, AddForm.SaveInfo{

    private static final int REQUEST_TAKE_PICTURE = 0x01001;

    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(
                R.id.container, AddForm.newInstance(), AddForm.TAG).commit();

    }

    @Override
    public void handleCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mImageUri = getOutputUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

        startActivityForResult(intent, REQUEST_TAKE_PICTURE);

    }

    /**
     *
     * Camera Methods
     */
    private Uri getOutputUri(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_ HHmmss");
        Date date = new Date(System.currentTimeMillis());

        String imageName = sdf.format(date);

        File imageDir = Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File appDir = new File(imageDir, "CameraDemoPhotos");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");

        try {
            image.createNewFile();
        } catch(Exception e){
            e.printStackTrace();
        }

        return Uri.fromFile(image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_TAKE_PICTURE && resultCode != RESULT_CANCELED){

            //mImageUri = data.getData();
            ImageView mImageView = (ImageView)findViewById(R.id.viewImg);

            if(data == null) {
                Bitmap image = BitmapFactory.decodeFile(mImageUri.getPath());
                mImageView.setImageBitmap(image);
                addImageToGallery(mImageUri);
            } else {
                Bitmap image = (Bitmap)data.getParcelableExtra("data");
                mImageView.setImageBitmap(image);
            }
        }
    }

    private void addImageToGallery(Uri imageUri){
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scannerIntent.setData(imageUri);
        sendBroadcast(scannerIntent);
    }

    @Override
    public void saveButton(String _title, String _extra) {

        Intent intent = getIntent();

        String _lat = Double.toString(intent.getDoubleExtra(MainActivity.EXTRA_LAT, 0));
        String _lng = Double.toString(intent.getDoubleExtra(MainActivity.EXTRA_LNG, 0));



        if(mImageUri != null){
            String imgUri = mImageUri.toString();

            LocationObject saveObject = new LocationObject(_title, _extra, imgUri, _lat, _lng);

            ArrayList<LocationObject> loadedArray;

            DatabaseHelper helper = new DatabaseHelper();

            loadedArray = helper.loadArray(this);

            if(loadedArray.size() == 0){
                loadedArray.add(0, saveObject);
            } else {
                loadedArray.add(loadedArray.size(), saveObject);
            }
            Log.e("Loaded data", loadedArray.get(0).latitude +"");

            helper.saveObject(loadedArray, this);

            Log.e("Loaded data", helper.loadArray(this).toString());


            finish();

        }
    }
}
