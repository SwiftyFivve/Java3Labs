package com.jordanweaver.j_weaver_notifications_labthree;

import android.app.ListFragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/15/15.
 */
public class ArticleFragment extends ListFragment {


    public static final String TAG = "ArticleFragment.TAG";

    public static ArticleFragment newInstance(){
        ArticleFragment articleFragment = new ArticleFragment();
        return articleFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBaseHelper helper = new DataBaseHelper();

        ArrayList<ArticleObject> loadedArticles = helper.loadArray(getActivity());

        ArrayAdapter<ArticleObject> feedAdapter = new ArrayAdapter<ArticleObject>(
                getActivity(), android.R.layout.simple_list_item_1, loadedArticles);

        setListAdapter(feedAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        DataBaseHelper helper = new DataBaseHelper();

        ArrayList<ArticleObject> openArticle = helper.loadArray(getActivity());

        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(openArticle.get(position).url));

        startActivity(webIntent);

    }
}
