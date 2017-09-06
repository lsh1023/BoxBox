package com.lsh.boxbox.module.start.splash;

/**
 * Created by LSH on 2017/8/30.
 */

public interface SplashInteractor {

    public interface OnEnterIntoFinishListener {
        void isFirstOpen();

        void isNotFirstOpen();

        void showContentView();
    }

    void enterInfo(boolean isFirstOpen, OnEnterIntoFinishListener listener);

}
