package com.cancercarecompany.ccc.ccc;

import android.app.Application;
import android.content.Context;

/**
 * Created by Josef on 2016-07-22.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}