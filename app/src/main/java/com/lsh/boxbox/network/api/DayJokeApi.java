package com.lsh.boxbox.network.api;

import com.lsh.boxbox.model.DayJoke;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/22.
 */

public interface DayJokeApi {
    @GET("joke/content/text.from")
    Observable<DayJoke> getDayJoke(@Query("key") String key);
}
