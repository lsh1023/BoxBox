package com.lsh.boxbox.network.api;

import com.lsh.boxbox.model.FindBg;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/21.
 */

public interface FindBgApi {
    @GET("HPImageArchive.aspx")
    Observable<FindBg> getFlagBg(@Query("format") String format, @Query("idx") int idx, @Query("n") int n);
}
