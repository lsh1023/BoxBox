package com.lsh.boxbox.module.me;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseFragment;
import com.lsh.boxbox.utils.AppToastMgr;

/**
 * Created by LSH on 2017/8/29.
 */

public class MeFragment extends BaseFragment {


    public MeFragment() {
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        AppToastMgr.ToastShortCenter(getContext(), "Me");
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}
