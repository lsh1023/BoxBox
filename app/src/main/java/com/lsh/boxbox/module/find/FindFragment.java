package com.lsh.boxbox.module.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseFragment;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.database.FunctionDao;
import com.lsh.boxbox.model.ChinaCalendar;
import com.lsh.boxbox.model.Constellation;
import com.lsh.boxbox.model.DayJoke;
import com.lsh.boxbox.model.FindBg;
import com.lsh.boxbox.model.FunctionBean;
import com.lsh.boxbox.model.RefreshFindFragmentEvent;
import com.lsh.boxbox.module.WebViewUI;
import com.lsh.boxbox.module.find.joke.JokeActivity;
import com.lsh.boxbox.network.Network;
import com.lsh.boxbox.utils.AppLogMessageMgr;
import com.lsh.boxbox.utils.AppToastMgr;
import com.lsh.boxbox.utils.DateUtils;
import com.lsh.boxbox.utils.PixelUtil;
import com.lsh.boxbox.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import lib.homhomlib.design.SlidingLayout;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by LSH on 2017/8/29.
 * 发现
 */

public class FindFragment extends BaseFragment implements View.OnClickListener {

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
        EventBus.getDefault().register(this);
        mBgFlag = 0;
        initBg();
        initBottomContent();
        initRecy();
    }

    private void initRecy() {
        mFindList = initData();
        mRecycleFind.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mFindAdapter = new FindAdapter(mFindList);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mFindAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecycleFind);

        //开启拖拽
        mFindAdapter.enableDragItem(mItemTouchHelper);
        mFindAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                FunctionDao functionDao1 = new FunctionDao(getContext().getApplicationContext());
                List<FunctionBean> data = mFindAdapter.getData();

                for (int i = 0; i < data.size(); i++) {
                    FunctionBean functionBean = data.get(i);
                    if (functionBean.getId() == i) {
                        functionBean.setId(i);
                        functionDao1.updateFunctionBean(functionBean);
                    }
                }
            }
        });

        mRecycleFind.setAdapter(mFindAdapter);
        mRecycleFind.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = ((FunctionBean) adapter.getData().get(position)).getName();
                itemActionEvent(name);
            }
        });
    }

    private void itemActionEvent(String name) {
        switch (name) {
            case "万年历":
                startActivity(new Intent(getContext(), ChinaCalendarActivity.class));
                break;
            case "快递查询":
                Intent intent = new Intent(getContext(), WebViewUI.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", "https://m.kuaidi100.com/");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case "黄金数据":
                notOpen();
                break;
            case "股票数据":
                notOpen();
                break;
            case "更多":
                startActivity(new Intent(getContext(), FindMoreActivity.class));
                break;
            case "身份证查询":
//                Intent intent_idcard = new Intent(getContext(), QueryInfoActivity.class);
//                intent_idcard.putExtra(QueryInfoActivity.QUERY_STYLE, QueryInfoActivity.QUERY_IDCARD);
//                startActivity(intent_idcard);
                break;
            case "邮编查询":
                notOpen();
                break;
            case "手机归属地":
//                Intent intent_tel = new Intent(getContext(), QueryInfoActivity.class);
//                intent_tel.putExtra(QueryInfoActivity.QUERY_STYLE, QueryInfoActivity.QUERY_TEL);
//                startActivity(intent_tel);
                break;
            case "QQ吉凶":
//                Intent intent_qq = new Intent(getContext(), QueryInfoActivity.class);
//                intent_qq.putExtra(QueryInfoActivity.QUERY_STYLE, QueryInfoActivity.QUERY_QQ);
//                startActivity(intent_qq);
                break;
            case "星座运势":
//                startActivity(new Intent(getContext(), ConstellationActivity.class));
                break;
            case "周公解梦":
                notOpen();
                break;
            case "汇率":
                notOpen();
                break;
            case "笑话大全":
                startActivity(new Intent(getContext(), JokeActivity.class));
                break;
            default:
                break;
        }
    }

    private void notOpen() {
        AppToastMgr.ToastShortCenter(getContext(), "该功能现在暂时未开放!");
    }

    private List<FunctionBean> initData() {
        FunctionDao functionDao = new FunctionDao(getContext().getApplicationContext());
        return functionDao.queryFunctionBeanListSmallNeed();
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
                    intent.putExtra(PinImageActivity.IMG_NAME, TextUtils.isEmpty(mNowBgName) ? "" : mNowBgName);
                    intent.putExtra(PinImageActivity.IMG_URL, mNowBgUrl);
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(),
                            mBgFindFind,
                            getString(R.string.transition_pinchimageview)
                    );
                    ActivityCompat.startActivity((Activity) getContext(), intent, optionsCompat.toBundle());

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

    private void initBottomContent() {
        boolean starIsOpen = (boolean) SPUtils.get(getContext(), Const.STAR_IS_OPEN, true);
        boolean jokeIsOpen = (boolean) SPUtils.get(getContext(), Const.JOKE_IS_OPEN, true);
        boolean wannianliIsOpen = (boolean) SPUtils.get(getContext(), Const.STUFF_IS_OPEN, true);

        if (!starIsOpen && jokeIsOpen && !wannianliIsOpen) {
            mTheFooterFind.setVisibility(View.GONE);
            return;
        } else {
            if (mTheFooterFind.getVisibility() == View.GONE) {
                mTheFooterFind.setVisibility(View.VISIBLE);
            }
        }

        if (starIsOpen) {
            mStarFind.setVisibility(View.VISIBLE);
            String starName = (String) SPUtils.get(getContext(), Const.STAR_NAME, "水瓶座");
            mXzStarFind.setText("-" + starName);
            mStarFind.setOnClickListener(this);
            requestStarData(starName);
        } else {
            mStarFind.setVisibility(View.GONE);
        }

        if (jokeIsOpen) {
            mJokeFind.setVisibility(View.VISIBLE);
            mJokeFind.setOnClickListener(this);
            requestJokeData();
        } else {
            mJokeFind.setVisibility(View.GONE);
        }

        if (wannianliIsOpen) {
            mWannianliFind.setVisibility(View.VISIBLE);
            mWannianliFind.setOnClickListener(this);
            requestWannianli();
        } else {
            mWannianliFind.setVisibility(View.GONE);
        }

    }

    private void requestWannianli() {
        String mDate = new StringBuffer()
                .append(DateUtils.getCurrYear()).append("-")
                .append(DateUtils.getCurrMonth()).append("-")
                .append(DateUtils.getCurrDay())
                .toString();
        unsubscribe("chinacalendar");
        mChinaCalendarSubscription = Network.getChinaCalendarApi()
                .getChinaCalendar("3f95b5d789fbc83f5d2f6d2479850e7e", mDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    private void requestJokeData() {
        unsubscribe("joke");
        mDayJokeSubscribe = Network.getDayJokeApi()
                .getDayJoke("39094c8b40b831b8e7b7a19a20654ed7")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDayJokeObserver);
    }

    private void requestStarData(String stringName) {
        unsubscribe("star");
        mConstellationSubscription = Network.getConstellationApi()
                .getConstellation(stringName, "today", "35de7ea555a8b5d58ce2d7e4f8cb7c9f")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(mConstellationObserver);
    }

    private void unsubscribe(String string) {
        switch (string) {
            case "bg":
                if (mBgSubscription != null && !mBgSubscription.isUnsubscribed()) {
                    mBgSubscription.unsubscribe();
                }
                break;
            case "joke":
                if (mDayJokeSubscribe != null && mDayJokeSubscribe.isUnsubscribed()) {
                    mDayJokeSubscribe.unsubscribe();
                }
                break;
            case "star":
                if (mConstellationSubscription != null && mConstellationSubscription.isUnsubscribed()) {
                    mConstellationSubscription.unsubscribe();
                }
            case "chinacalendar":
                if (mChinaCalendarSubscription != null && mChinaCalendarSubscription.isUnsubscribed()) {
                    mChinaCalendarSubscription.unsubscribe();
                }
                break;
            default:
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshNewsFragmentEvent(RefreshFindFragmentEvent event) {
        if (event.getFlagBig() > 0) {
            initBottomContent();
        }
        if (event.getFlagSmall() > 0) {
            mFindAdapter.setNewData(initData());
        }
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joke_find:
                startActivity(new Intent(getContext(), JokeActivity.class));
                break;
            case R.id.star_find:
                break;
            case R.id.wannianli_find:
                break;
            default:
                break;
        }
    }
}
