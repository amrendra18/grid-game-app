package com.amrendra.gridgame;

import android.app.Application;

/**
 * Created by Amrendra Kumar on 23/10/15.
 */
public class GridGameApp extends Application {
    private static GridGameApp gridGameApp;

    @Override
    public void onCreate() {
        super.onCreate();
        gridGameApp = this;
    }

}
