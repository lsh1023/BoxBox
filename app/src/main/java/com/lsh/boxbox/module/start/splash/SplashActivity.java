package com.lsh.boxbox.module.start.splash;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.lsh.boxbox.R;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.module.start.main.MainActivity;
import com.lsh.boxbox.module.start.welcome.WelcomeActivity;
import com.lsh.boxbox.utils.AppToastMgr;
import com.lsh.boxbox.utils.SPUtils;
import com.lsh.boxbox.utils.StateBarTranslucentUtils;
import com.umeng.analytics.MobclickAgent;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 引导页
 */

@RuntimePermissions
public class SplashActivity extends AppCompatActivity implements SplashView {

    private KenBurnsView mKenBurns;
    private ImageView mLogo;
    private TextView welcomeText;

    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mPresenter = new SplashPresenterImpl(this);

        //判断是否是第一次开启应用
        boolean isFirstOpen = (boolean) SPUtils.get(this, Const.FIRST_OPEN, false);
        mPresenter.isFirstOpen(isFirstOpen);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_splash);

        StateBarTranslucentUtils.setStateBarTranslucent(this);
        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mLogo = (ImageView) findViewById(R.id.logo_splash);
        welcomeText = (TextView) findViewById(R.id.welcome_text);
        Glide.with(this).load(R.drawable.welcometoqbox).into(mKenBurns);
        animation2();
        animation3();
    }

    Animation anim;

    private void animation2() {
        mLogo.setAlpha(1.0F);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
    }

    ObjectAnimator alphaAnimation;

    private void animation3() {
        alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }


    //================  动态权限的申请============
    @Override
    @NeedsPermission({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void startWelcomeGuideActivity() {
        WelcomeActivity.start(this);
        finish();
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("有部分权限需要你的授权")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                }).show();
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForCamera() {
        showPermissionDenied();
    }

    public void showPermissionDenied() {
        new AlertDialog.Builder(this)
                .setTitle("权限说明")
                .setCancelable(false)
                .setMessage("本应用需要部分必要的权限，如果不授予可能会影响正常使用！")
                .setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("赋予权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SplashActivityPermissionsDispatcher.startWelcomeGuideActivityWithCheck(SplashActivity.this);
                    }
                })
                .create().show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain({Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            /*Manifest.permission.WRITE_CONTACTS,*/
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForCamera() {
        AppToastMgr.ToastShortCenter(this, "不再询问授权权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //=========动态权限的申请========

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mKenBurns != null) {
            mKenBurns.pause();
        }
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
        if (anim != null) {
            anim.cancel();
        }
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mKenBurns != null) {
            mKenBurns.resume();
        }
        MobclickAgent.onResume(this);
    }
}
