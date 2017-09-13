package com.lsh.boxbox.network.api;

import com.lsh.boxbox.model.NewsItem;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/12.
 * 新闻类的访问接口
 */

public interface NewsApi {
    @GET("toutiao/index")
    Observable<NewsItem> getNews(@Query("type") String newstype, @Query("key") String appkey);
}
