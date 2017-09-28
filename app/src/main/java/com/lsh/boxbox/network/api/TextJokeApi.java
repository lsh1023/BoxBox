package com.lsh.boxbox.network.api;

import com.lsh.boxbox.model.NewTextJokeBean;
import com.lsh.boxbox.model.RandomTextJoke;



import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/28.
 */

public interface TextJokeApi {

    @GET("joke/content/text.from")
    Observable<NewTextJokeBean> getNewTextJokeJoke(@Query("key") String appkey,
                                                   @Query("page") int pno,
                                                   @Query("pagesize") int ps);

    @GET("joke/randJoke.php")
    Observable<RandomTextJoke> getRandomTextJoke(@Query("key") String key);
}
