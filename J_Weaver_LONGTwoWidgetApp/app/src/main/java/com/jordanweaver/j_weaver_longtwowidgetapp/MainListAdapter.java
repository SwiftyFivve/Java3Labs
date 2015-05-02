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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/30/15.
 */
public class MainListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<RecipeObject> mRecipes;
    private static final int ID_CONSTANT = 0x01000000;

    public MainListAdapter(Context context, ArrayList<RecipeObject> mRecipes) {
        this.mContext = context;
        this.mRecipes = mRecipes;
    }

    @Override
    public int getCount() {
        if(mRecipes != null){
            return mRecipes.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(mRecipes != null && position<mRecipes.size() && position >= 0){
            return mRecipes.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(mRecipes != null){
            return ID_CONSTANT + position;
        } else {
           return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_cell,
                    parent, false);
        }

        RecipeObject object = (RecipeObject) getItem(position);

        ((SmartImageView)convertView.findViewById(R.id.recipeImage)).setImageUrl(object.getImg());
        ((TextView)convertView.findViewById(R.id.nameText)).setText(object.getName());

        return convertView;
    }
}
