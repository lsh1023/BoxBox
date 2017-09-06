package com.lsh.boxbox.module.start.splash;

/**
 * Created by LSH on 2017/8/30.
 */

public class SplashPresenterImpl implements SplashPresenter, SplashInteractor.OnEnterIntoFinishListener {

    private SplashView mSplashView;
    private SplashInteractor mSplashInteractor;

    public SplashPresenterImpl(SplashView mSplashView) {
        this.mSplashView = mSplashView;
        mSplashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void isFirstOpen(boolean isFirstOpen) {
        mSplashInteractor.enterInfo(isFirstOpen, this);
    }

    @Override
    public void onDestroy() {
        mSplashView = null;
    }


    @Override
    public void isFirstOpen() {
        SplashActivityPermissionsDispatcher.startWelcomeGuideActivityWithCheck((SplashActivity) mSplashView);
    }

    @Override
    public void isNotFirstOpen() {
        mSplashView.startHomeActivity();
    }

    @Override
    public void showContentView() {
        mSplashView.initContentView();
    }
}
