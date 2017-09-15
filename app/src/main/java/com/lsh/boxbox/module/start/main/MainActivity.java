package com.lsh.boxbox.module.start.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lsh.boxbox.R;
import com.lsh.boxbox.app.BaseApplication;
import com.lsh.boxbox.base.BaseCustomActivity;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.config.StatusBarCompat;
import com.lsh.boxbox.model.RefreshNewsFragmentEvent;
import com.lsh.boxbox.module.find.FindFragment;
import com.lsh.boxbox.module.me.MeFragment;
import com.lsh.boxbox.module.news.NewsFragment;
import com.lsh.boxbox.module.news.news_category.CategoryActivity;
import com.lsh.boxbox.module.wechat.WeChatFragment;
import com.lsh.boxbox.update.AppUtils;
import com.lsh.boxbox.utils.SPUtils;
import com.lsh.boxbox.utils.StateBarTranslucentUtils;
import com.lsh.boxbox.utils.inputmethodmanager_leak.IMMLeaks;
import com.lsh.boxbox.widget.TabBar_Mains;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * APP首页
 */
public class MainActivity extends BaseCustomActivity {

    public MeFragment mMeFragment;
    public NewsFragment mNewsFragment;
    public WeChatFragment mWechatFragment;
    public FindFragment mFindFragment;

    private static final String NEWS_FRAGMENT = "NEWS_FRAGMENT";
    private static final String WECHAT_FRAGMENT = "WECHAT_FRAGMENT";
    private static final String FIND_FRAGMENT = "FIND_FRAGMENT";
    public static final String ME_FRAGMENT = "ME_FRAGMENT";

    @BindView(R.id.frame_container)
    FrameLayout sFramelayoutMains;
    @BindView(R.id.recommend_news)
    TabBar_Mains sRecommendNews;
    @BindView(R.id.recommend_wechat)
    TabBar_Mains sRecommendWechat;
    @BindView(R.id.recommend_find)
    TabBar_Mains sRecommendFind;
    @BindView(R.id.recommend_me)
    TabBar_Mains sRecommendMe;

    private FragmentManager sBaseFragmentManager;
    /**
     * 存储当前Fragment的标记
     */
    private String mCurrentIndex;

    @Override
    public void initContentView() {
        StateBarTranslucentUtils.setStateBarTranslucent(this);
        setContentView(R.layout.activity_main);
        BaseApplication.setMainActivity(this);
        StatusBarCompat.compat(this);
    }


    boolean isRestart = false;

    private void initByRestart(Bundle savedInstanceState) {

        mCurrentIndex = savedInstanceState.getString("mCurrentIndex");

        isRestart = true;
        Logger.e("恢复状态：" + mCurrentIndex);
        mMeFragment = (MeFragment) sBaseFragmentManager.findFragmentByTag(ME_FRAGMENT);
        if (sRecommendNews.getVisibility() == View.VISIBLE) {
            mNewsFragment = (NewsFragment) sBaseFragmentManager.findFragmentByTag(NEWS_FRAGMENT);
        }
        mWechatFragment = (WeChatFragment) sBaseFragmentManager.findFragmentByTag(WECHAT_FRAGMENT);
        mFindFragment = (FindFragment) sBaseFragmentManager.findFragmentByTag(FIND_FRAGMENT);

        switchToFragment(mCurrentIndex);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        sBaseFragmentManager = getBaseFragmentManager();

        String startPage = WECHAT_FRAGMENT;
        String s = (String) SPUtils.get(this, Const.OPENNEWS, "nomagic");
        if (s.equals("nomagic")) {
            sRecommendWechat.setVisibility(View.VISIBLE);
            startPage = NEWS_FRAGMENT;
        }
        if (savedInstanceState != null) {
            initByRestart(savedInstanceState);
        } else {
            switchToFragment(startPage);
            mCurrentIndex = startPage;
        }

//        int qbox_version = (int) SPUtils.get(this, Const.QBOX_NEW_VERSION, 0);
//        if (qbox_version != 0 && qbox_version > AppUtils.getVersionCode(this)) {
//            UpdateChecker.checkForNotification(this); //通知提示升级
//        }

        //订阅事件
        EventBus.getDefault().register(this);
    }


    private void switchToFragment(String index) {
        hideAllFragment();
        switch (index) {
            case NEWS_FRAGMENT:
                if (sRecommendNews.getVisibility() == View.VISIBLE) {
                    showNewsFragment();
                }
                break;
            case WECHAT_FRAGMENT:
                showWechatFragment();
                break;
            case FIND_FRAGMENT:
                showFindFragment();
                break;
            case ME_FRAGMENT:
                showMeFragment();
                break;
            default:
                break;
        }
        mCurrentIndex = index;
    }

    private void showMeFragment() {
        if (false == sRecommendMe.isSelected())
            sRecommendMe.setSelected(true);
        if (mMeFragment == null) {
            mMeFragment = MeFragment.newInstance();
            addFragment(R.id.frame_container, mMeFragment, ME_FRAGMENT);
        } else if (isRestart = true) {
            getFragmentTransaction().show(mMeFragment).commit();
            isRestart = false;
        } else {
            showFragment(mMeFragment);
        }
    }

    private void showFindFragment() {
        if (false == sRecommendFind.isSelected()) {
            sRecommendFind.setSelected(true);
        }
        if (mFindFragment == null) {
            mFindFragment = FindFragment.newInstance("", "");
            addFragment(R.id.frame_container, mFindFragment, FIND_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mFindFragment).commit();
        } else {
            showFragment(mFindFragment);
        }

    }

    private void showWechatFragment() {
        if (!sRecommendWechat.isSelected()) {
            sRecommendWechat.setSelected(true);
        }
        if (mWechatFragment == null) {
            mWechatFragment = mWechatFragment.newInstance("", "");
            addFragment(R.id.frame_container, mWechatFragment, WECHAT_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mWechatFragment).commit();
        } else {
            showFragment(mWechatFragment);
        }

    }

    private void showNewsFragment() {
        if (sRecommendNews.getVisibility() != View.VISIBLE) {
            return;
        }
        if (false == sRecommendNews.isSelected()) {
            sRecommendNews.setSelected(true);
        }
        if (mNewsFragment == null) {
            Logger.e("恢复状态：" + "null");
            mNewsFragment = NewsFragment.newInstance("a", "b");
            addFragment(R.id.frame_container, mNewsFragment, NEWS_FRAGMENT);
        } else if (isRestart = true) {
            isRestart = false;
            getFragmentTransaction().show(mNewsFragment).commit();
        } else {
            showFragment(mNewsFragment);
        }

    }

    private void hideAllFragment() {
        if (mNewsFragment != null) {
            hideFragment(mNewsFragment);
        }
        if (mWechatFragment != null) {
            hideFragment(mWechatFragment);
        }
        if (mFindFragment != null) {
            hideFragment(mFindFragment);
        }
        if (mMeFragment != null) {
            hideFragment(mMeFragment);
        }
        if (sRecommendNews.getVisibility() == View.VISIBLE) {
            sRecommendNews.setSelected(false);
        }
        sRecommendFind.setSelected(false);
        sRecommendWechat.setSelected(false);
        sRecommendMe.setSelected(false);
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.recommend_news, R.id.recommend_wechat, R.id.recommend_find, R.id.recommend_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recommend_news:
                if (!mCurrentIndex.equals(NEWS_FRAGMENT))
                    switchToFragment(NEWS_FRAGMENT);
                break;
            case R.id.recommend_wechat:
                if (!mCurrentIndex.equals(WECHAT_FRAGMENT))
                    switchToFragment(WECHAT_FRAGMENT);
                break;
            case R.id.recommend_find:
                if (!mCurrentIndex.equals(FIND_FRAGMENT))
                    switchToFragment(FIND_FRAGMENT);
                break;
            case R.id.recommend_me:
                if (!mCurrentIndex.equals(ME_FRAGMENT))
                    switchToFragment(ME_FRAGMENT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Const.NEWSFRAGMENT_CATEGORYACTIVITY_REQUESTCODE
                && resultCode == Const.NEWSFRAGMENT_CATEGORYACTIVITY_RESULTCODE) {
            mNewsFragment.initView();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshNewsFragmentEvent event) {
        startActivityForResult(new Intent(MainActivity.this, CategoryActivity.class), event.getMark_code());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        for (Fragment fragment :
                getBaseFragmentManager().getFragments()) {
            getFragmentTransaction().remove(fragment);
        }
        super.onDestroy();
        BaseApplication.setMainActivity(null);
        IMMLeaks.fixFocusedViewLeak(getApplication());//解决 Android 输入法造成的内存泄漏问题。
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentIndex", mCurrentIndex);
        Logger.e("保存状态");
    }

    /**
     * 监听用户按返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    private boolean mIsExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 用于优雅的退出程序(当从其他地方退出应用时会先返回到此页面再执行退出)
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(Const.TAG_EXIT, false);
            if (isExit) {
                finish();
            }
        }
    }

}
