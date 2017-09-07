package com.lsh.boxbox.network.api;

import com.lsh.boxbox.model.City;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/7.
 */

public interface CityApi {
    @GET("v5/search")
    Observable<City> getCity(@Query("key") String key,
                           @Query("city") String city);

}
