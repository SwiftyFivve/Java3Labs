package com.jordanweaver.j_weaver_services_labtwo;

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
 * Created by jordanweaver on 4/2/15.
 */
public class GridFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String TAG = "GridFragment.TAG";

    public static GridFragment newInstance(){
        GridFragment gridFragment = new GridFragment();
        return gridFragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File[] imgs;
        File manager = getActivity().getExternalFilesDir(null);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        if(manager != null){
            imgs = manager.listFiles();
            intent.setDataAndType(Uri.fromFile(imgs[position]), "image/png");
        }

        mGrid.gridImg(intent);
    }

    public interface UpdateGrid{
        public void gridImg(Intent intent);
    }

    UpdateGrid mGrid;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof UpdateGrid){
            mGrid = (UpdateGrid)activity;
        } else {
            throw new IllegalArgumentException("Main Activity needs to Implement UpdateGrid Interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment, container, false);
        GridAdaptor gridAdaptor = new GridAdaptor(getActivity());
        GridView gridView = (GridView)view.findViewById(R.id.gridCollection);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(gridAdaptor);
        return view;
    }

    public class GridAdaptor extends BaseAdapter{

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
