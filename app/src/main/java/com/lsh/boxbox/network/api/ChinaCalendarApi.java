package com.lsh.boxbox.network.api;

import com.lsh.boxbox.model.ChinaCalendar;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LSH on 2017/9/22.
 *
 */

public interface ChinaCalendarApi {
    @GET("calendar/day")
    Observable<ChinaCalendar> getChinaCalendar(@Query("key") String key,
                                               @Query("date") String date);
}
