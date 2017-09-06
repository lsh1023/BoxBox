package com.lsh.boxbox.network;

import com.lsh.boxbox.model.WechatItem;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/4.
 */

public interface WechatApi {
    @GET("weixin/query")
    Observable<WechatItem> getWechat(@Query("key") String appkey,
                                     @Query("pno") int pno,
                                     @Query("ps") int ps);
}
