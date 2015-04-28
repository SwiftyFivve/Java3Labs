package com.jordanweaver.j_weaver_collectionwidget_labseven;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jordanweaver on 4/27/15.
 */
public class CollectionWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CollectionWidgetViewFactory(getApplicationContext());
    }


}
