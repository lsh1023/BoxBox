package com.lsh.boxbox.module.find;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.database.FunctionDao;
import com.lsh.boxbox.model.FunctionBean;
import com.lsh.boxbox.model.RefreshFindFragmentEvent;
import com.lsh.boxbox.model.RefreshMeFragmentEvent;
import com.lsh.boxbox.utils.SPUtils;
import com.squareup.haha.trove.THash;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * 发现--更多
 */
public class FindMoreActivity extends BaseCommonActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar_find_more)
    Toolbar mToolbarFindMore;
    @BindView(R.id.recyclerview_find_more)
    RecyclerView mRecyclerViewFindMore;

    public FindMoreAdapter mFindMoreAdapter;
    List<FunctionBean> mFunctionBeenList;
    public ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    public ItemTouchHelper mItemTouchHelper;
    public FunctionDao mFunctionDao;

    public int flagSmall;
    public int flagBig;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_find_more);
    }

    @Override
    public void initView() {
        flagSmall = 0;
        flagBig = 0;
        initToolbar();
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerViewFindMore.setLayoutManager(new LinearLayoutManager(this));
        mFindMoreAdapter = new FindMoreAdapter(mFunctionBeenList, this);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mFindMoreAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerViewFindMore);

        mFindMoreAdapter.enableDragItem(mItemTouchHelper);
        mFindMoreAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                View itemView = source.itemView;
                TextView viewById = (TextView) itemView.findViewById(R.id.icon_item_findmore);
                viewById.setText(String.valueOf(to + 1));
                View itemView1 = target.itemView;
                ((TextView) itemView1.findViewById(R.id.icon_item_findmore)).setText(String.valueOf(from + 1));
                if (from == 4 && to == 5) {
                    itemView.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    itemView1.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    return;
                }

                if (from == 5 && to == 4) {
                    itemView.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    itemView1.findViewById(R.id.flag_item_findmore).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                flagSmall++;
            }
        });

        addHeaderView();

        addFooterView();

        mRecyclerViewFindMore.setAdapter(mFindMoreAdapter);
        mRecyclerViewFindMore.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

    private void addFooterView() {
        View footerView = getLayoutInflater().inflate(R.layout.activity_find_more_footer,
                ((ViewGroup) mRecyclerViewFindMore.getParent()), false);

        footerView.findViewById(R.id.history_findmore_footer).setOnClickListener(this);
        footerView.findViewById(R.id.laohuangli_findmore_footer).setOnClickListener(this);
        footerView.findViewById(R.id.xiaohua_findmore_footer).setOnClickListener(this);

        Switch historySwitch = (Switch) footerView.findViewById(R.id.switch_history_findmore_footer);
        Switch laohuangliSwitch = (Switch) footerView.findViewById(R.id.switch_laohuangli_findmore_footer);
        Switch xiaohuaSwitch = (Switch) footerView.findViewById(R.id.switch_xiaohua_findmore_footer);
        TextView userStar = (TextView) footerView.findViewById(R.id.userstar_fondmore_footer);

        historySwitch.setChecked((Boolean) SPUtils.get(FindMoreActivity.this, Const.STAR_IS_OPEN, true));
        laohuangliSwitch.setChecked((Boolean) SPUtils.get(FindMoreActivity.this, Const.STUFF_IS_OPEN, true));
        xiaohuaSwitch.setChecked((Boolean) SPUtils.get(FindMoreActivity.this, Const.JOKE_IS_OPEN, true));
        userStar.setText(((String) SPUtils.get(FindMoreActivity.this, Const.USER_STAR, "白羊座")));

        historySwitch.setOnCheckedChangeListener(this);
        laohuangliSwitch.setOnCheckedChangeListener(this);
        xiaohuaSwitch.setOnCheckedChangeListener(this);

        mFindMoreAdapter.addFooterView(footerView);
    }

    private void addHeaderView() {
        View headerView = getLayoutInflater().inflate(R.layout.activity_find_more_header, (ViewGroup) mRecyclerViewFindMore.getParent(), false);
        mFindMoreAdapter.addHeaderView(headerView);
    }

    private void initData() {
        mFunctionDao = new FunctionDao(getApplicationContext());
        mFunctionBeenList = mFunctionDao.queryFunctionBeanListSmallNoMore();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_findmore_footer:
                break;
            case R.id.laohuangli_findmore_footer:
                break;
            case R.id.xiaohua_findmore_footer:
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        flagBig++;

        switch (buttonView.getId()) {
            case R.id.switch_history_findmore_footer:
                SPUtils.put(this, Const.STAR_IS_OPEN, isChecked);
                break;
            case R.id.switch_laohuangli_findmore_footer:
                SPUtils.put(this, Const.STUFF_IS_OPEN, isChecked);
                break;
            case R.id.switch_xiaohua_findmore_footer:
                SPUtils.put(this, Const.JOKE_IS_OPEN, isChecked);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        List<FunctionBean> data = mFindMoreAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            FunctionBean functionBean = data.get(i);

            int j = i + 1;
            if (functionBean.getId() != j) {
                if (j < 6) {
                    functionBean.setId(j);
                } else {
                    functionBean.setId(j + 1);
                }
            }
        }

        mFunctionDao.updateAllFunctionBean(data);
        mFunctionDao.updateMoreFunctionBean();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (flagSmall > 0 || flagBig > 0) {
            EventBus.getDefault().post(new RefreshFindFragmentEvent(flagSmall, flagBig));
        }
        super.onDestroy();
    }
}
