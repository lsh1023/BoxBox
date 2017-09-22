package com.lsh.boxbox.network.api;

import com.lsh.boxbox.model.Constellation;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/22.
 */

public interface ConstellationApi {
    @GET("constellation/getAll")
    Observable<Constellation> getConstellation(
            @Query("consName") String consName,
            @Query("type") String type,
            @Query("key") String key
    );
}
