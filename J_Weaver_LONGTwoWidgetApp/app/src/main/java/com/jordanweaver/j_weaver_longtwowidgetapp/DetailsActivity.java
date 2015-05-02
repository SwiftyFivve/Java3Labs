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

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 4/29/15.
 */
public class DetailsActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container,
                DetailsFragment.newInstance(), DetailsFragment.TAG).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView rName = (TextView)findViewById(R.id.recipeName);
        SmartImageView rImg = (SmartImageView) findViewById(R.id.rImg);
        TextView ratingText = (TextView)findViewById(R.id.rating);
        TextView rIngredients = (TextView)findViewById(R.id.ingredientsList);

        Intent intent = getIntent();

        if(intent.hasExtra(Strings.EXTRA_SERIALIZABLE)){
            RecipeObject object = (RecipeObject) intent.getSerializableExtra
                    (Strings.EXTRA_SERIALIZABLE);

            rName.setText(object.getName());
            rImg.setImageUrl(object.getImg());
            ratingText.setText(object.getRating() + "/5 Rating");
            rIngredients.setText(object.getIngredients());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {

            DataBaseHelper helper = new DataBaseHelper();
            ArrayList<RecipeObject> middleArray = helper.loadRecipes(this);

            Intent intent = getIntent();

            if(intent.hasExtra(Strings.EXTRA_SERIALIZABLE)) {
                RecipeObject object = (RecipeObject) intent.getSerializableExtra
                        (Strings.EXTRA_SERIALIZABLE);

                if(!middleArray.contains(object)) {

                    if (middleArray.size() != 0) {
                        middleArray.add(middleArray.size(), object);
                    } else {
                        middleArray.add(0, object);
                    }

                    helper.saveRecipes(this, middleArray);
                }

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
