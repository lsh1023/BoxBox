package com.lsh.boxbox.module;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;
import com.lsh.boxbox.utils.AppToastMgr;
import com.umeng.analytics.MobclickAgent;

/**
 * 利用AgentView加载H5页面，可以播放音视频
 */
public class WebViewUI extends BaseCommonActivity {

    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;
    private TextView mTitleTextView;

    private String url;
    private String title;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_web_view_ui);
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
        mTitleTextView.setText(title);
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewUI.this.finish();
            }
        });

        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()//
                .ready()
                .go(getUrl());

        mAgentWeb.getLoader().loadUrl(getUrl());
    }

    @Override
    public void initPresenter() {

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

    };


    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }
    };

    public String getUrl() {
        if (url == null) {
            AppToastMgr.ToastShortCenter(getApplicationContext(), "网络错误");
        }
        return url;
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (mTitleTextView != null)
                mTitleTextView.setText(title);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

}
