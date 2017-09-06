package com.lsh.boxbox.module.news;

import android.os.Bundle;
import android.view.View;

import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonFragment;
import com.lsh.boxbox.base.BaseFragment;

/**
 * Created by LSH on 2017/8/29.
 */

public class NewsFragment extends BaseFragment {

    private String mParam1;
    private String mParam2;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public NewsFragment() {
        // Required empty public constructor
    }


    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragemnt_news;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
