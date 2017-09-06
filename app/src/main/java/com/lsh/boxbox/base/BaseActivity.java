package com.lsh.boxbox.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.lsh.boxbox.utils.AppManager;

import butterknife.ButterKnife;

/**
 * Created by LSH on 2017/8/30.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initData();
        initView();

    }

    //数据初始化完成操作
    protected abstract void initView();

    //界面加载类
    protected abstract void initData();

    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
