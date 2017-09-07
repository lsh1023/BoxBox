package com.lsh.boxbox.network;


import com.lsh.boxbox.model.City;
import com.lsh.boxbox.network.api.CityApi;
import com.lsh.boxbox.network.api.WechatApi;
import com.lsh.boxbox.utils.AppLogMessageMgr;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LSH on 2017/8/29.
 */

public class Network {
    public static final String ROOT_URL = "http://v.juhe.cn/";

    private static WechatApi mWechatApi;
    private static CityApi sCityApi;

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static WechatApi getWechatApi() {
        if (mWechatApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            mWechatApi = retrofit.create(WechatApi.class);
        }
        AppLogMessageMgr.e("getWechatApi", "ok");
        return mWechatApi;
    }

    public static CityApi getCityApi() {
        if (sCityApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://api.heweather.com/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sCityApi = retrofit.create(CityApi.class);
        }
        return sCityApi;
    }

}
