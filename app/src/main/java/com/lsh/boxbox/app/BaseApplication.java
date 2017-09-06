package com.lsh.boxbox.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.lsh.boxbox.module.start.main.MainActivity;
import com.tencent.smtt.sdk.TbsDownloader;

/**
 * Created by LSH on 2017/8/29.
 */

public class BaseApplication extends Application {

    public static final boolean DEBUG = false;
    public static final boolean USE_SAMPLE_DATA = false;
    private static BaseApplication mInstance;

    private static MainActivity sMainActivity = null;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    public static Context getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initScreenSize();

        TbsDownloader.needDownload(getApplicationContext(), false);
    }

    private void initScreenSize() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        screenDensity = displayMetrics.density;
    }

    /**
     * 图片64K问题，MultiDex构建
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void setMainActivity(MainActivity activity) {
        sMainActivity = activity;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
