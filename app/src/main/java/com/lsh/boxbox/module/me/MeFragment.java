package com.lsh.boxbox.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseFragment;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.model.RefreshMeFragmentEvent;
import com.lsh.boxbox.utils.AppToastMgr;
import com.lsh.boxbox.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LSH on 2017/8/29.
 * 我的Fragment
 */

public class MeFragment extends BaseFragment {


    @BindView(R.id.homebg_me)
    ImageView homebgMe;
    @BindView(R.id.desc_me)
    TextView descMe;
    @BindView(R.id.show_me)
    FrameLayout showMe;
    @BindView(R.id.user_head_me)
    CircleImageView userHeadMe;
    @BindView(R.id.username_me)
    TextView usernameMe;
    @BindView(R.id.ll_calendar_me)
    LinearLayout llCalendarMe;
    @BindView(R.id.ll_weather_me)
    LinearLayout llWeatherMe;
    @BindView(R.id.ll_led_me)
    LinearLayout llLedMe;
    @BindView(R.id.ll_me_flashlight)
    LinearLayout llMeFlashlight;
    @BindView(R.id.ll_code_me)
    LinearLayout llCodeMe;
    @BindView(R.id.ll_setting_me)
    LinearLayout llSettingMe;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    Unbinder unbinder;

    public MeFragment() {
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        initUserInfo();
    }

    /**
     * 初始化姓名，头像，描述
     */
    private void initUserInfo() {
        String username = (String) SPUtils.get(getContext(), Const.USER_NAME, "");
        String userHeader = (String) SPUtils.get(getContext(), Const.USER_HEADER, "");
        String userDesc = (String) SPUtils.get(getContext(), Const.USER_GEYAN, "我愿做你世界里的太阳，给你温暖。");

        if (!TextUtils.isEmpty(username)) {
            usernameMe.setText(username);
        }
        if (!TextUtils.isEmpty(userHeader)) {
            Glide.with(getContext()).load(new File(userHeader)).into(userHeadMe);
        }

        if (!TextUtils.isEmpty(userDesc)) {
            descMe.setText(userDesc);
        }
    }

    @Override
    protected void managerArguments() {
    }

    @OnClick({R.id.ll_calendar_me, R.id.ll_weather_me, R.id.ll_led_me, R.id.ll_me_flashlight, R.id.ll_code_me, R.id.ll_setting_me, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_calendar_me:
                break;
            case R.id.ll_weather_me:
                break;
            case R.id.ll_led_me:
                break;
            case R.id.ll_me_flashlight:
                break;
            case R.id.ll_code_me:
                break;
            case R.id.ll_setting_me:
                break;
            case R.id.fab:
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                ActivityOptionsCompat optionCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        userHeadMe,
                        getString(R.string.transition_userhead)
                );
                ActivityCompat.startActivity(getContext(), intent, optionCompat.toBundle());

                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnRefreshUserInfo(RefreshMeFragmentEvent event) {
        if (event.getMark_code() == 0x1000) {
            initUserInfo();
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }

    @Override
    public String getUmengFragmentName() {
        return getContext().getClass().getSimpleName() + "-" + this.getClass().getSimpleName();
    }

}
