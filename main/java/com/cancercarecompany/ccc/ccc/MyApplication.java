package com.cancercarecompany.ccc.ccc;

import android.app.Application;
import android.content.Context;
import org.acra.*;
import org.acra.annotation.*;
import org.acra.sender.HttpSender;

@ReportsCrashes(
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON,
//        formUri = "http://collector.tracepot.com/6686741d",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text
)
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }

    public static Context getContext() {
        return mContext;
    }
}