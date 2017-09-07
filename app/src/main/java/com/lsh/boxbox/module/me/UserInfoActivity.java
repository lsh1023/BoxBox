package com.lsh.boxbox.module.me;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;
import com.lsh.boxbox.utils.AppToastMgr;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Body;

/**
 * 个人信息
 */
public class UserInfoActivity extends BaseCommonActivity implements Toolbar.OnMenuItemClickListener, TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.toolbar_userinfo)
    Toolbar toolbarUserinfo;
    @BindView(R.id.userheader_image)
    CircleImageView userheaderImage;
    @BindView(R.id.userheader_select_userinfo)
    FrameLayout userheaderSelectUserinfo;
    @BindView(R.id.username_userinfo)
    TextInputEditText usernameUserinfo;
    @BindView(R.id.radiobutton_man_userinfo)
    RadioButton radiobuttonManUserinfo;
    @BindView(R.id.radiobutton_woman_userinfo)
    RadioButton radiobuttonWomanUserinfo;
    @BindView(R.id.radiogroup_sex_userinfo)
    RadioGroup radiogroupSexUserinfo;
    @BindView(R.id.user_desc_userinfo)
    TextInputEditText userDescUserinfo;
    @BindView(R.id.starspinner_userinfo)
    Spinner starspinnerUserinfo;
    @BindView(R.id.user_address_userinfo)
    TextInputEditText userAddressUserinfo;

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void initView() {
        initToolbar();
        initInfo();
    }

    private void initInfo() {

    }

    private void initToolbar() {
        toolbarUserinfo.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbarUserinfo.inflateMenu(R.menu.userinfo_menu);
        toolbarUserinfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbarUserinfo.setOnMenuItemClickListener(this);
    }

    @Override
    public void initPresenter() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void takeSuccess(TResult result) {

    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.userheader_select_userinfo, R.id.user_address_userinfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userheader_select_userinfo:
                AppToastMgr.ToastShortCenter(getContext(), "拍照");
                break;
            case R.id.user_address_userinfo:
                AppToastMgr.ToastShortCenter(getContext(), "地址选择");
                break;
            default:
                break;
        }
    }
}
