package com.hnxind.zscj;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by mangocandy on 16-1-4.
 */
public class MyApplication extends Application {
    public static final String THEME_SP="theme";
    public static final String MAIN_COLOR="maincolor";
    public static final String TITLE_COLOR="titlecolor";
    public static final String CHANGE_THEME="com.hnxind.changetheme";
    public static final String COLOR_NAME="colorname";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
