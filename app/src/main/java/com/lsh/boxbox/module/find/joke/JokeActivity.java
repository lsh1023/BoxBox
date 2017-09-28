package com.lsh.boxbox.module.find.joke;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;
import com.lsh.boxbox.model.RefreshJokeStyleEvent;
import com.lsh.boxbox.model.RefreshMeFragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 发现--笑话的activity
 */
public class JokeActivity extends BaseCommonActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<String> mTitleStrings;
    private List<Fragment> mFragmentList;

    public static final int JOKE_STYLE_NEW = 1;
    public static final int JOKE_STYLE_RANDOM = 2;

    public int mJokeStyle;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_joke);
    }

    @Override
    public void initView() {
        mJokeStyle = JOKE_STYLE_RANDOM;
        initToolbar();
        initViewPager();
    }

    private void initViewPager() {
        mTitleStrings = Arrays.asList("故事", "趣图");
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < mTitleStrings.size(); i++) {
            switch (mTitleStrings.get(i)) {
                case "故事":
                    mFragmentList.add(i, TextJokeFragment.newInstance("", ""));
                    break;
                case "趣图":
                    mFragmentList.add(i, ImgJokeFragment.newInstance("", ""));
                    break;
                default:
                    break;
            }
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this, mFragmentList, mTitleStrings);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_joke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_new) {
            mJokeStyle = JOKE_STYLE_NEW;
            EventBus.getDefault().post(new RefreshJokeStyleEvent(mJokeStyle));
            return true;
        }
        if (id == R.id.action_random) {
            mJokeStyle = JOKE_STYLE_RANDOM;
            EventBus.getDefault().post(new RefreshJokeStyleEvent(mJokeStyle));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initPresenter() {

    }

    public int getJokeStyle() {
        return mJokeStyle;
    }
}
