package com.yaozh.download.com.yaozh.download;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 10/22/2015.
 */
public class App extends Application {
    private static Context  context;
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "zaOPkb2XJdOPsU91s7iKoNme", "kv1W9TvD0KK6TXO7y8U1WtCt");
        AVInstallation.getCurrentInstallation().saveInBackground();
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
