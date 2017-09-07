package com.lsh.boxbox.module.wechat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseFragment;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.model.WechatItem;
import com.lsh.boxbox.module.WeChatDetailActivity;
import com.lsh.boxbox.network.Network;
import com.lsh.boxbox.utils.AppToastMgr;
import com.lsh.boxbox.utils.PixelUtil;
import com.lsh.boxbox.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LSH on 2017/8/29.
 * 微信精选的Fragment
 */

public class WeChatFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String WECHAT_APPKEY = "26ce25ffcfc907a26263e2b0e3e23676";
    //每页请求的 item 数量
    public final int mPs = 21;
    public int mPageMark = 1;
    public boolean mRefreshMark;

    @BindView(R.id.recycler_wechat)
    RecyclerView mRecyclerWechat;
    @BindView(R.id.swiper_wechat)
    SwipeRefreshLayout mSwiperWechat;
    @BindView(R.id.floatingactionbutton_wechat)
    FloatingActionButton mFloatingActionButton;

    private View notDataView;
    private View errorView;

    private String mParam1;
    private String mParam2;

    private List<WechatItem.ResultBean.ListBean> mListBeanList;
    private WechatItemAdapter mWechatItemAdapter;
    private Subscription mSubscription;

    public WeChatFragment() {
    }

    //观察者模式
    Observer<WechatItem> mObserver = new Observer<WechatItem>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Logger.e("onError:" + e.getMessage());
            onErrorView();
            if (mSwiperWechat.isRefreshing()) {
                mSwiperWechat.setRefreshing(false);
            }
            if (mSwiperWechat.isEnabled()) {
                mSwiperWechat.setEnabled(false);
            }
        }

        @Override
        public void onNext(WechatItem wechatItem) {
            setNewDataAddList(wechatItem);
        }
    };

    private void onErrorView() {
        mWechatItemAdapter.setEmptyView(errorView);
    }

    public static WeChatFragment newInstance(String param1, String param2) {
        WeChatFragment fragment = new WeChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragemnt_wechat;
    }

    @Override
    protected void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    @Override
    public void initView() {
        initFAB();
        initSwipeRefresh();
        initEmptyView();
        initRecyclerWeChat();
        onLoading();
        requestData();
    }

    private void initFAB() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWechatItemAdapter != null && mWechatItemAdapter.getData() != null && mWechatItemAdapter.getData().size() > 0) {
                    mRecyclerWechat.scrollToPosition(0);
                }
            }
        });
    }


    private void initSwipeRefresh() {
        mSwiperWechat.setOnRefreshListener(this);
        mSwiperWechat.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwiperWechat.setEnabled(false);
    }

    @Override
    public void onRefresh() {
        mWechatItemAdapter.setEnableLoadMore(false);
        mRefreshMark = true;
        mPageMark = 1;
        requestData();
    }

    private void initEmptyView() {
        /**
         * 网络请求失败没有数据
         */
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerWechat.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });

        /**
         * 网络请求错误 | 没有网络
         */
        errorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) mRecyclerWechat.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    private void initRecyclerWeChat() {
        mRecyclerWechat.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext(), 2));
        mListBeanList = new ArrayList<>();
        boolean isNotLoad = (boolean) SPUtils.get(getContext(), Const.SLLMS, false);
        int imgWidth = PixelUtil.getWindowWidth();
        int imgHeight = imgWidth * 3 / 4;
        mWechatItemAdapter = new WechatItemAdapter(mListBeanList, isNotLoad, imgWidth, imgHeight);
        mWechatItemAdapter.openLoadAnimation();
        mWechatItemAdapter.setOnLoadMoreListener(this);
        mWechatItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if (position == 0) {
                    return 2;
                } else {
                    return mWechatItemAdapter.getData().get(position).getSpansize();
                }
            }
        });
        mRecyclerWechat.setAdapter(mWechatItemAdapter);
        mRecyclerWechat.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                WechatItem.ResultBean.ListBean listBean = (WechatItem.ResultBean.ListBean) adapter.getData().get(position);

                Intent intent = new Intent(getContext(), WeChatDetailActivity.class);
                intent.putExtra("wechat", listBean);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        view.findViewById(R.id.img_wechat_style),
                        getString(R.string.transition_wechat_img)
                );

                ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());
            }
        });

    }

    private void onLoading() {
        mWechatItemAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) mRecyclerWechat.getParent());
    }


    private void requestData() {
        unsubscribe();
        mSubscription = Network.getWechatApi()
                .getWechat(WECHAT_APPKEY, mPageMark, mPs)//key,页码,每页条数
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }


    private void setNewDataAddList(WechatItem wechatItem) {
        if (wechatItem != null && wechatItem.getError_code() == 0) {
            mPageMark++;
            List<WechatItem.ResultBean.ListBean> newData = wechatItem.getResult().getList();
            WechatItem.ResultBean.ListBean listBean = newData.get(0);
            listBean.setItemType(1);
            listBean.setSpansize(2);
            if (mRefreshMark) {
                mWechatItemAdapter.setNewData(newData);
                mRefreshMark = false;
            } else {
                mWechatItemAdapter.addData(newData);
            }

            if (mSwiperWechat.isRefreshing()) {
                mSwiperWechat.setRefreshing(false);
            }
            if (!mSwiperWechat.isEnabled()) {
                mSwiperWechat.setEnabled(true);
            }
            if (mWechatItemAdapter.isLoading()) {
                mWechatItemAdapter.loadMoreComplete();
            }
            if (!mWechatItemAdapter.isLoadMoreEnable()) {
                mWechatItemAdapter.setEnableLoadMore(true);
            }
        } else {
            if (mWechatItemAdapter.isLoading()) {
                AppToastMgr.ToastShortCenter(getContext(), String.valueOf(R.string.load_more_error));
                mWechatItemAdapter.loadMoreFail();
            } else {
                mWechatItemAdapter.setEmptyView(notDataView);
                if (mSwiperWechat.isRefreshing()) {
                    mSwiperWechat.setRefreshing(false);
                }
                if (mSwiperWechat.isEnabled()) {
                    mSwiperWechat.setEnabled(false);
                }
            }
        }

    }

    /**
     * 列表上拉加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mSwiperWechat.setEnabled(false);
        requestData();
    }

    private void unsubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName() + "-"
                + this.getClass().getSimpleName();
    }
}
