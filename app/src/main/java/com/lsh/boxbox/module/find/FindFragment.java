package com.lsh.boxbox.module.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseFragment;
import com.lsh.boxbox.model.ChinaCalendar;
import com.lsh.boxbox.model.Constellation;
import com.lsh.boxbox.model.DayJoke;
import com.lsh.boxbox.model.FindBg;
import com.lsh.boxbox.model.FunctionBean;
import com.lsh.boxbox.network.Network;
import com.lsh.boxbox.utils.AppLogMessageMgr;
import com.lsh.boxbox.utils.PixelUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lib.homhomlib.design.SlidingLayout;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by LSH on 2017/8/29.
 * 发现
 */

public class FindFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.bg_find_find)
    KenBurnsView mBgFindFind;
    @BindView(R.id.recycle_find)
    RecyclerView mRecycleFind;
    @BindView(R.id.xiaohua_find)
    TextView mXiaohuaFind;
    @BindView(R.id.joke_find)
    LinearLayout mJokeFind;
    @BindView(R.id.year_calendar)
    TextView mYearCalendar;
    @BindView(R.id.years_calendar)
    TextView mYearsCalendar;
    @BindView(R.id.day_clendar)
    TextView mDayClendar;
    @BindView(R.id.nongli_calendar)
    TextView mNongliCalendar;
    @BindView(R.id.jieri_calendar)
    TextView mJieriCalendar;
    @BindView(R.id.week_calendar)
    TextView mWeekCalendar;
    @BindView(R.id.yi_calendar)
    TextView mYiCalendar;
    @BindView(R.id.ji_calendar)
    TextView mJiCalendar;
    @BindView(R.id.wannianli_find)
    LinearLayout mWannianliFind;
    @BindView(R.id.qfriend_star_find)
    TextView mQfriendStarFind;
    @BindView(R.id.yunshi_star_find)
    TextView mYunshiStarFind;
    @BindView(R.id.xz_star_find)
    TextView mXzStarFind;
    @BindView(R.id.star_find)
    LinearLayout mStarFind;
    @BindView(R.id.the_footer_find)
    LinearLayout mTheFooterFind;
    @BindView(R.id.web_layout)
    SlidingLayout mWebLayout;
    @BindView(R.id.bg_title_find)
    TextView mBgTitleFind;
    @BindView(R.id.before_find)
    ImageButton mBeforeFind;
    @BindView(R.id.next_find)
    ImageButton mNextFind;
    @BindView(R.id.find_fragment)
    RelativeLayout mFindFragment;
    Unbinder unbinder;

    private static final String BG_BASE_URL = "http://www.bing.com";

    private int mBgFlag;
    public List<FindBg.ImagesBean> mImages;

    private List<FunctionBean> mFindList;
    public FindAdapter mFindAdapter;
    public ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    public ItemTouchHelper mItemTouchHelper;
    public Subscription mConstellationSubscription;
    public Subscription mDayJokeSubscribe;
    public Subscription mBgSubscription;
    public Subscription mChinaCalendarSubscription;

    private String mNowBgName;
    private String mNowBgUrl;

    private String mParam1;
    private String mParam2;

    Observer<Constellation> mConstellationObserver = new Observer<Constellation>() {
        @Override
        public void onCompleted() {
            AppLogMessageMgr.e("constellation-complete");
        }

        @Override
        public void onError(Throwable e) {
            AppLogMessageMgr.e(e.getMessage());
        }

        @Override
        public void onNext(Constellation constellation) {
            if (constellation.getError_code() == 0) {
                AppLogMessageMgr.e(constellation.getQFriend());
                showConstellation(constellation);
            }
        }
    };

    private void showConstellation(Constellation constellation) {
        mQfriendStarFind.setText(constellation.getQFriend());
        mYunshiStarFind.setText(constellation.getSummary());
    }

    Observer<ChinaCalendar> mObserver = new Observer<ChinaCalendar>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            AppLogMessageMgr.e(e.getMessage());
        }

        @Override
        public void onNext(ChinaCalendar chinaCalendar) {
            if (chinaCalendar.getError_code() == 0) {
                initDateView(chinaCalendar.getResult().getData());
            } else {
                mYunshiStarFind.setText("请求数据失败");
            }
        }
    };

    private void initDateView(ChinaCalendar.ResultBean.DataBean data) {
        mJieriCalendar.setText(data.getHoliday() + "");
        mNongliCalendar.setText("农历" + data.getLunar());
        mYearCalendar.setText(data.getYearmonth());
        mDayClendar.setText(data.getDate().split("-")[2] + "");
        mYearsCalendar.setText(data.getAnimalsYear() + "." + data.getLunarYear());
        mWeekCalendar.setText(data.getWeekday() + "");
        mYiCalendar.setText(data.getSuit() + "");
        mJieriCalendar.setText(data.getAvoid() + "");
    }

    Observer<DayJoke> mDayJokeObserver = new Observer<DayJoke>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(DayJoke dayJoke) {
            if (dayJoke.getError_code() == 0 && dayJoke.getResult() != null && dayJoke.getResult().getData() != null) {
                showDayJoke(dayJoke.getResult().getData().get(0));
            }
        }
    };

    private void showDayJoke(DayJoke.ResultBean.DataBean dataBean) {
        String jokeContent = dataBean.getContent();
        if (!TextUtils.isEmpty(jokeContent)) {
            mXiaohuaFind.setText(jokeContent);
        }
    }

    Observer<FindBg> mFindBgObserver = new Observer<FindBg>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(FindBg findBg) {
            if (findBg != null) {
                mImages = findBg.getImages();
            }
            setBg(mBgFlag);
        }
    };

    private void setBg(int bgFlag) {
        if (bgFlag <= 0) {
            mBeforeFind.setEnabled(false);
            if (bgFlag == 0)
                showBg(mImages.get(0));
        } else if (bgFlag > mImages.size() - 1) {
            mNextFind.setEnabled(false);
            if (bgFlag == mImages.size() - 1)
                showBg(mImages.get(mImages.size() - 1));
        } else {
            showBg(mImages.get(bgFlag));
            if (!mBeforeFind.isEnabled()) {
                mBeforeFind.setEnabled(true);
            }
            if (!mNextFind.isEnabled()) {
                mNextFind.setEnabled(true);
            }
        }
    }

    private void showBg(FindBg.ImagesBean imagesBean) {
        mNowBgUrl = BG_BASE_URL + imagesBean.getUrl();
        Glide.with(getContext())
                .load(mNowBgUrl)
                .override(PixelUtil.getWindowWidth(), PixelUtil.getWindowHeight())
                .placeholder(R.color.colorPrimaryDark)
                .error(R.color.colorPrimaryDark)
                .into(mBgFindFind);

        mNowBgName = imagesBean.getCopyright();
        mBgTitleFind.setText(mNowBgName);
    }


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
//        EventBus.getDefault().register(this);
        mBgFlag = 0;
        initBg();
//        initBottomContent();
//        initRecy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (mBgTitleFind != null) {
            if (hidden) {
                mBgTitleFind.setFitsSystemWindows(false);
            } else {
                mBgTitleFind.setFitsSystemWindows(true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                mBgTitleFind.requestApplyInsets();
            }
        }
        super.onHiddenChanged(hidden);
    }

    private void initBg() {

        mBeforeFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImages != null) {
                    setBg(--mBgFlag);
                }
            }
        });

        mNextFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImages != null) {
                    setBg(++mBgFlag);
                }
            }
        });

        mBgTitleFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mNowBgUrl)) {
                    Intent intent = new Intent(getContext(), PinImageActivity.class);
                    intent.putExtra(PinImageActivity.IMG_NAME,TextUtils.isEmpty(mNowBgName)?"":mNowBgName);
                    intent.putExtra(PinImageActivity.IMG_URL,mNowBgUrl);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(),
                            mBgFindFind,
                            getString(R.string.transition_pinchimageview)
                    );
                    ActivityCompat.startActivity((Activity) getContext(),intent,optionsCompat.toBundle());

                }
            }
        });

        requestBg();
    }

    private void requestBg() {
        unsubscribe("bg");
        mBgSubscription = Network.getFindBgApi()
                .getFlagBg("js", 0, 8)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFindBgObserver);
    }

    private void unsubscribe(String string) {
    }


    @Override
    protected void managerArguments() {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
