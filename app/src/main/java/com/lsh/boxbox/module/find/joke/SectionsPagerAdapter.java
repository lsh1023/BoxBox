package com.lsh.boxbox.module.find.joke;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LSH on 2017/9/28.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    List<Fragment> mFragments;
    List<String> mTitleStrings;

    public SectionsPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragment, List<String> titleStrings) {
        super(fm);
        mContext = context;
        mFragments = fragment;
        mTitleStrings = titleStrings;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleStrings == null || mTitleStrings.size() < position) {
            return null;
        }
        return mTitleStrings.get(position);
    }
}
