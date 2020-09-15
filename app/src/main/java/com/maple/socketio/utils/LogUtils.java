package com.maple.socketio.utils;

import android.util.Log;


public class LogUtils {
    private static final boolean isLog = true;

    public static void logGGQ(String msg) {
        if (isLog) {
            Log.d("GGQ", msg);
        }
    }

}