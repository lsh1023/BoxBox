package com.lsh.boxbox.module.me;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lsh.boxbox.R;
import com.lsh.boxbox.base.BaseCommonActivity;
import com.lsh.boxbox.config.Const;
import com.lsh.boxbox.model.City;
import com.lsh.boxbox.model.RefreshMeFragmentEvent;
import com.lsh.boxbox.module.me.weather.ApiManager;
import com.lsh.boxbox.utils.AppToastMgr;
import com.lsh.boxbox.utils.SPUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.id;

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


    City.HeWeather5Bean.BasicBean cityBean = null;
    public final static int mMessageFlag = 0x1010;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    public String mHeaderAbsolutePath;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case mMessageFlag:
                    Glide.with(UserInfoActivity.this)
                            .load(((File) msg.obj))
                            .into(userheaderImage);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void initContentView() {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    public void initView() {
        initToolbar();
        initInfo();
    }

    @Override
    public void initPresenter() {

    }

    private void initInfo() {
        String userName = (String) SPUtils.get(this, Const.USER_NAME, "");
        boolean userGender = (boolean) SPUtils.get(this, Const.USER_SEX, true);
        String userDesc = (String) SPUtils.get(this, Const.USER_GEYAN, "");
        String userStar = (String) SPUtils.get(this, Const.USER_STAR, "");
        String userAddress = (String) SPUtils.get(this, Const.USER_ADDRESS, "");
        String userHeader = (String) SPUtils.get(this, Const.USER_HEADER, "");

        //姓名
        if (!TextUtils.isEmpty(userName)) {
            usernameUserinfo.setText(userName);
        }
        //性别
        radiogroupSexUserinfo.check(userGender == true ? R.id.radiobutton_man_userinfo : R.id.radiobutton_woman_userinfo);

        //描述
        if (!TextUtils.isEmpty(userDesc)) {
            userDescUserinfo.setText(userDesc);
        }
        //地址信息
        if (!TextUtils.isEmpty(userAddress)) {
            userAddressUserinfo.setText(userAddress);
        }

        //星座
        if (!TextUtils.isEmpty(userStar)) {
            int indexOf = Arrays.asList(getResources().getStringArray(R.array.arrays_constellation)).indexOf(userStar);
            starspinnerUserinfo.setSelection(indexOf);
        }

        //头像
        if (!TextUtils.isEmpty(userHeader)) {
            File file = new File(userHeader);
            Glide.with(this).load(file).into(userheaderImage);
        }

        usernameUserinfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 16) {
                    usernameUserinfo.setError("已达到最大长度");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userDescUserinfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 128) {
                    userDescUserinfo.setError("已达到最大长度");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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

    /**
     * 获取星座列表的选择项
     *
     * @return
     */
    private String getStarSelect() {
        long selectItemId = starspinnerUserinfo.getSelectedItemId();
        String[] stringArray = getResources().getStringArray(R.array.arrays_constellation);
        return stringArray[(int) selectItemId];
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }


    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: 2017/9/7 地址返回的时候还是有问题
        if (requestCode == Const.REQUEST_CODE_ADDRESS && resultCode == Const.RESULTES_CODE_ADDRESS) {
            //添加地点
            cityBean = data.getParcelableExtra("data");
            Logger.e(cityBean.getProv() + "-" + cityBean.getCity());
            userAddressUserinfo.setText(cityBean.getProv() + "-" + cityBean.getCity());

        } else {
            //添加头像
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }


    /**
     * 保存按钮，点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.save_userinfo) {
            Editable textName = usernameUserinfo.getText();
            if (TextUtils.isEmpty(textName)) {
                usernameUserinfo.setError("姓名不能为空");
                return false;
            }
            if (textName.length() >= 3) {
                SPUtils.put(this, Const.USER_NAME, textName.toString());
            } else {
                usernameUserinfo.setError("长度不应小于3个字符");
                return false;
            }

            Editable textGeyan = userDescUserinfo.getText();
            if (!TextUtils.isEmpty(textGeyan)) {
                SPUtils.put(this, Const.USER_GEYAN, textGeyan.toString());
            } else {
                SPUtils.remove(this, Const.USER_GEYAN);
            }

            SPUtils.put(this, Const.USER_SEX, radiogroupSexUserinfo.getCheckedRadioButtonId()
                    == R.id.radiobutton_man_userinfo ? true : false);
            SPUtils.put(this, Const.USER_STAR, getStarSelect());

            if (!TextUtils.isEmpty(userAddressUserinfo.getText())) {
                SPUtils.put(this, Const.USER_ADDRESS, userAddressUserinfo.getText().toString());
                /**
                 * city : 北京
                 * cnty : 中国
                 * id : CN101010100
                 * lat : 39.904000
                 * lon : 116.391000
                 * prov : 直辖市
                 */
                if (cityBean != null) {
                    Logger.e("地点：" + cityBean.toString());
                    SPUtils.put(this, Const.USER_ADDRESS_CITY, cityBean.getCity());
                    SPUtils.put(this, Const.USER_ADDRESS_CNTY, cityBean.getCnty());
                    SPUtils.put(this, Const.USER_ADDRESS_ID, cityBean.getId());
                    SPUtils.put(this, Const.USER_ADDRESS_LAT, cityBean.getLat());
                    SPUtils.put(this, Const.USER_ADDRESS_LON, cityBean.getLon());
                    SPUtils.put(this, Const.USER_ADDRESS_PROV, cityBean.getProv());
                }
            } else {
                SPUtils.remove(this, Const.USER_ADDRESS);
                SPUtils.remove(this, Const.USER_ADDRESS_CNTY);
                SPUtils.remove(this, Const.USER_ADDRESS_CITY);
                SPUtils.remove(this, Const.USER_ADDRESS_ID);
                SPUtils.remove(this, Const.USER_ADDRESS_LAT);
                SPUtils.remove(this, Const.USER_ADDRESS_LON);
                SPUtils.remove(this, Const.USER_ADDRESS_PROV);
            }
            if (TextUtils.isEmpty(mHeaderAbsolutePath)) ;
            else {
                SPUtils.put(this, Const.USER_HEADER, mHeaderAbsolutePath);
            }
            EventBus.getDefault().post(new RefreshMeFragmentEvent(0x1000));
            finish();
        }
        return false;
    }

    //选取图片或者拍照成功
    @Override
    public void takeSuccess(TResult result) {
        File file = new File(result.getImages().get(0).getCompressPath());
        mHeaderAbsolutePath = file.getAbsolutePath();

        //需要返回到UI线程，刷新头像
        Message msg = mHandler.obtainMessage();
        msg.what = mMessageFlag;
        msg.obj = file;
        mHandler.sendMessage(msg);

    }

    @Override
    public void takeFail(TResult result, String msg) {
        AppToastMgr.ToastShortCenter(this, "拍照失败");
    }

    @Override
    public void takeCancel() {
        AppToastMgr.ToastShortCenter(this, "取消");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.userheader_select_userinfo, R.id.user_address_userinfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userheader_select_userinfo:
                showTakePhotoDialog();
                break;
            case R.id.user_address_userinfo:
                startActivityForResult(new Intent(UserInfoActivity.this, AddressActivity.class),
                        Const.REQUEST_CODE_ADDRESS);
                break;
            default:
                break;
        }
    }

    private void showTakePhotoDialog() {
        final String[] items = {"拍照", "相册"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择头像");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                takeOrPickPhoto(which == 0 ? true : false);
            }
        });
        builder.create().show();
    }

    /**
     * 拍照或者选择照片
     *
     * @param isTakePhoto
     */
    private void takeOrPickPhoto(boolean isTakePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), File.separator + "temp" + File.separator + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        Uri imageUri = Uri.fromFile(file);
        TakePhoto takePhoto = getTakePhoto();

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        if (isTakePhoto) {  //拍照
            if (true) {  //是否裁剪
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromCapture(imageUri);
            }
        } else {  //选取照片
            int limit = 1;//选择照片的个数
            if (limit > 1) {

                //当选择图片大于1个时候是否裁剪
                if (true) {
                    takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                } else {
                    takePhoto.onPickMultiple(limit);
                }
                return;
            } else {
                if (true) {  //是否裁剪
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromGallery();
                }

            }
        }


    }

    /**
     * 配置 裁剪选项
     *
     * @return
     */
    private CropOptions getCropOptions() {
        int height = 100;
        int width = 100;
        CropOptions.Builder builder = new CropOptions.Builder();
        //按照宽高比例裁剪
        builder.setAspectX(width).setAspectY(height);
        //是否使用TakePhoto自带的裁剪工具
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    /**
     * 拍照相关的配置
     *
     * @param takePhoto
     */
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        //是否使用takePhoto自带的相册
        if (false) {
            builder.setWithOwnGallery(true);
        }
        //纠正拍照的选择角度
        if (true) {
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());
    }

    /**
     * 配置压缩选项
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 102400;
        int width = 800;
        int height = 800;
        //是否显示，压缩进度条
        boolean showProgressBar = true;
        //压缩后是否保存原图
        boolean enableRawFile = true;
        CompressConfig config;

        if (false) {
            //使用自带的压缩工具
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            //使用开源的鲁班压缩工具
            LubanOptions options = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(options);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    /**
     * 获取takePhoto实例
     *
     * @return
     */
    private TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
}
