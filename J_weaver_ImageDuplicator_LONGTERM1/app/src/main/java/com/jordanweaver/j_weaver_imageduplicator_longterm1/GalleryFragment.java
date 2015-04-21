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

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;


/**
 * Created by jordanweaver on 4/18/15.
 */
public class GalleryFragment extends Fragment implements AdapterView.OnItemClickListener,
    AdapterView.OnItemLongClickListener{

    public static final String TAG = "GalleryFragment.TAG";

    int mPostion;

    public static GalleryFragment newInstance() {
        GalleryFragment galleryFragment = new GalleryFragment();
        return galleryFragment;
    }

    public interface showImg{
        public void showGallery(Intent intent);
    }

    showImg mShow;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof showImg){
            mShow = (showImg)activity;
        } else {
            throw new IllegalArgumentException("Need to Implement showImg");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        GridView galleryGrid = (GridView)getActivity().findViewById(R.id.galleryGrid);

        GridAdaptor adaptor = new GridAdaptor(getActivity());

        galleryGrid.setOnItemClickListener(this);
//        galleryGrid.setOnLongClickListener(this);

        galleryGrid.setAdapter(adaptor);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mPostion = position;

        Intent intent = new Intent(Intent.ACTION_VIEW);

        File external = getActivity().getExternalFilesDir(null);

        if(external != null){
            File[] mFiles = external.listFiles();
            Uri showImg = Uri.fromFile(mFiles[position]);
            intent.setDataAndType(showImg, "image/jpeg");
            mShow.showGallery(intent);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {



        return false;
    }


    public class GridAdaptor extends BaseAdapter {

        Context mContext;
        File[] imgs;

        public GridAdaptor(Context mContext) {
            this.mContext = mContext;

            File manager = getActivity().getExternalFilesDir(null);

            if (manager != null){
                imgs = manager.listFiles();
            }

        }

        @Override
        public int getCount() {
            if(imgs != null){
                return imgs.length;
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (imgs != null){
                File img = imgs[position];
                return Uri.fromFile(img);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.grid_cell, parent, false
                );
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 4;

            imageView.setImageBitmap(BitmapFactory.decodeFile(imgs[position].getAbsolutePath(), options));

            return convertView;
        }
    }

}
