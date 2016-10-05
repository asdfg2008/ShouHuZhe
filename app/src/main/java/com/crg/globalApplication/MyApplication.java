package com.crg.globalApplication;

import android.app.Application;
import android.content.Context;

import java.util.zip.CheckedOutputStream;

/**
 * Created by crg on 16/10/4.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
