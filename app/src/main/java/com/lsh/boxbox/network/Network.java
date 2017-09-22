package com.lsh.boxbox.network;


import com.lsh.boxbox.model.Constellation;
import com.lsh.boxbox.network.api.ChinaCalendarApi;
import com.lsh.boxbox.network.api.CityApi;
import com.lsh.boxbox.network.api.ConstellationApi;
import com.lsh.boxbox.network.api.DayJokeApi;
import com.lsh.boxbox.network.api.FindBgApi;
import com.lsh.boxbox.network.api.NewsApi;
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
 * 网络访问管理类
 */

public class Network {

    public static final String ROOT_URL = "http://v.juhe.cn/";

    private static WechatApi mWechatApi;
    private static CityApi sCityApi;
    private static NewsApi sNewsApi;
    private static FindBgApi sFindBgApi;
    private static ConstellationApi sConstellationApi;
    private static ChinaCalendarApi sChinaCalendarApi;
    private static DayJokeApi sDayJokeApi;

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
        AppLogMessageMgr.e("getWechatApi");
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
        AppLogMessageMgr.e("getCityApi");
        return sCityApi;
    }

    public static NewsApi getNewsApi() {
        if (sNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sNewsApi = retrofit.create(NewsApi.class);
        }
        AppLogMessageMgr.e("getNewsApi");
        return sNewsApi;
    }

    public static FindBgApi getFindBgApi() {
        if (sFindBgApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://www.bing.com")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sFindBgApi = retrofit.create(FindBgApi.class);
        }
        AppLogMessageMgr.e("getFindBgApi");
        return sFindBgApi;
    }

    public static ConstellationApi getConstellationApi() {
        if (sConstellationApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://web.juhe.cn:8080/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sConstellationApi = retrofit.create(ConstellationApi.class);
        }
        return sConstellationApi;
    }

    public static ChinaCalendarApi getChinaCalendarApi() {
        if (sChinaCalendarApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sChinaCalendarApi = retrofit.create(ChinaCalendarApi.class);
        }
        return sChinaCalendarApi;
    }

    public static DayJokeApi getDayJokeApi() {
        if (sDayJokeApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            sDayJokeApi = retrofit.create(DayJokeApi.class);
        }
        return sDayJokeApi;
    }

}
