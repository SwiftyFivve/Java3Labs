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

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by jordanweaver on 4/29/15.
 */
public class FlipWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FlipWidgetViewFactory(getApplicationContext());
    }
}
