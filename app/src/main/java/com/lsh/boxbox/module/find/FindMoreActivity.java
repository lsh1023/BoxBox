package com.lsh.boxbox.module.find;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;

import butterknife.BindView;

/**
 * 发现--更多
 */
public class FindMoreActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar_find_more)
    Toolbar mToolbarFindMore;
    @BindView(R.id.recyclerview_find_more)
    RecyclerView mRecyclerviewFindMore;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_find_more);
    }

    @Override
    public void initView() {
        initToolbar();
    }

    private void initToolbar() {
        mToolbarFindMore.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbarFindMore.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initPresenter() {

    }

}
