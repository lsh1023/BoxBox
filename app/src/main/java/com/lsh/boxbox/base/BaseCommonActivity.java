package com.lsh.boxbox.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.lsh.boxbox.utils.AppToastMgr;
import com.lsh.boxbox.widget.custom.CustomConfirmDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by LSH on 2017/8/31.
 * 基础类
 */

public abstract class BaseCommonActivity extends AppCompatActivity implements IBaseView {

    private ProgressDialog mProgressDialog;

    /**
     * 初始化布局
     */
    public abstract void initContentView();

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化控制中心
     */
    public abstract void initPresenter();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        initContentView();
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    /**
     * 显示单选对话框
     *
     * @param title           标题
     * @param message         提示信息
     * @param strings         默认选中
     * @param checkedItem     选项数组
     * @param onClickListener 点击事件的监听
     */
    public void showRadioButtonDialog(String title, String message, String[] strings, int checkedItem, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setSingleChoiceItems(strings, checkedItem, onClickListener);
        builder.show();
    }

    /**
     * 显示单选对话框
     *
     * @param title           标题
     * @param strings         选项数组
     * @param onClickListener 点击事件的监听
     */
    public void showRadioButtonDialog(String title, String[] strings, DialogInterface.OnClickListener onClickListener) {
        showRadioButtonDialog(title, null, strings, 0, onClickListener);
    }

    public void showConfirmDialog(String title, View.OnClickListener positiveListener) {
        CustomConfirmDialog confirmDialog = new CustomConfirmDialog(this, title, positiveListener);
        confirmDialog.show();
    }

    @Override
    public void showProgress(boolean flag, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(flag);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
        }
        mProgressDialog.show();
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void showProgress(boolean flag) {
        showProgress(false, "");
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog == null) {
            return;
        }
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        if (isFinishing()) {
            AppToastMgr.ToastShortCenter(this, msg);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
