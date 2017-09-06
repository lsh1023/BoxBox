package com.lsh.boxbox.app;

import android.annotation.SuppressLint;

import com.lsh.boxbox.utils.SdCardUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by LSH on 2017/8/30.
 * 全局变量存储类
 */

public class G {

    /**
     * 应用程序名
     */
    public static final String APP_NAME = "FastAndroid";

    /**
     * 文件根目录
     */
    public static final String STORAGE_PATH = SdCardUtil.getNormalSDCardPath() + File.separator + APP_NAME + File.separator;

    /**
     * 自动更新文件下载路径
     */
    public static final String UPDATE_APP_SAVE_PATH = STORAGE_PATH + APP_NAME + ".apk";
    /**
     * 系统图片
     */
    public static final String APP_IMAGE = STORAGE_PATH + "img/";
    /**
     * 录音文件保存路径
     */
    public static final String APP_RECORD = STORAGE_PATH + "record/";

    /**
     * 调用拍照request code
     */
    public static final int ACTION_CAMERA = 0x01;
    /**
     * 调用相册request code
     */
    public static final int ACTION_ALBUM = 0x02;
    /**
     * 打开扫码request code
     */
    public static final int ACTION_BARCODE = 0x03;

    /**
     * 打开录音request code
     */
    public static final int ACTION_RECORDER = 0x04;

    /**
     * 打开通讯录request code
     */
    public static final int ACTION_ADDRESS_LIST = 0x05;


    @SuppressLint("SimpleDateFormat")
    public static String getPhoneCurrentTime() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        return date.format(Calendar.getInstance().getTime());
    }
}
