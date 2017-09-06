package com.lsh.boxbox.module.start.splash;

import android.os.Handler;

/**
 * Created by LSH on 2017/8/30.
 */

public class SplashInteractorImpl implements SplashInteractor{
    @Override
    public void enterInfo(boolean isFirstOpen, final OnEnterIntoFinishListener listener) {
        if (!isFirstOpen){
            listener.isFirstOpen();
        }else {
            listener.showContentView();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    listener.isNotFirstOpen();
                }
            }, 2000);
        }
    }
}
