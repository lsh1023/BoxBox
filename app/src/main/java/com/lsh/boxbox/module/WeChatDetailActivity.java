package com.lsh.boxbox.module;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.model.WechatItem;
import com.lsh.boxbox.utils.SPUtils;

import com.lsh.boxbox.widget.TbsWebView;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import onekeyshare.OnekeyShare;

public class WeChatDetailActivity extends BaseCommonActivity {

    @BindView(R.id.ivImage)
    ImageView mImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    //TBS浏览器
//    com.tencent.smtt.sdk.WebView webView;
    TbsWebView webView;

    WechatItem.ResultBean.ListBean mWechat;


    @Override
    public void initContentView() {
        setContentView(R.layout.activity_we_chat_detail);
    }

    @Override
    public void initView() {
        ShareSDK.initSDK(this);
        initToolbar();
        initDataByGetIntent();
        initFAB();
        initWebView();
    }

    private WebViewClient client = new WebViewClient() {
        // 防止加载网页时调起系统浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };

    private void initWebView() {
        webView = (TbsWebView) findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.loadUrl(mWechat.getUrl());

    }


    private void initFAB() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWechat != null) {
                    showShare();
                }
            }
        });
    }

    private void initDataByGetIntent() {
        mWechat = getIntent().getParcelableExtra("wechat");

        initWebView();

        boolean isNotLoad = (boolean) SPUtils.get(this, Const.SLLMS, false);
        if (!isNotLoad) {
            Glide.with(this)
                    .load(mWechat.getFirstImg())
                    .placeholder(R.drawable.lodingview)
                    .error(R.drawable.errorview)
                    .crossFade(1000)
                    .into(mImageView);
        }
        getSupportActionBar().setTitle(mWechat.getTitle());
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShare() {
//        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(mWechat.getTitle() + "");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(mWechat.getUrl() + "");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("文章标题：" + mWechat.getTitle() + "\n地址：" + mWechat.getUrl() + "\n-来自：小秋魔盒");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mWechat.getUrl() + "");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("很喜欢这篇文章，写的很不错。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://ocnyang.com");

        // 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
