package com.lsh.boxbox.module.find;

import android.os.Bundle;

import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseFragment;

/**
 * Created by LSH on 2017/8/29.
 */

public class FindFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public FindFragment() {
    }


    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragemnt_find;
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
