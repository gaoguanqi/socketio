package com.maple.socketio.app;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {

    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initSDK();
    }

    private void initSDK() {
        Utils.init(this);
        SPUtils.getInstance(AppUtils.getAppPackageName());
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
