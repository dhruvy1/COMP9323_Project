package com.comp9323.RestAPI.APIInterface;

import com.comp9323.RestAPI.Beans.EventBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by timta on 11/09/2017.
 */

public interface EventAPI {
    @GET("events/all/")
    Call<List<EventBean>> getEvents();

    @POST("events")
    Call<EventBean> addEvent(@Body EventBean event);

    @GET("events/{id}")
    Call<EventBean> getEvent(@Path("id") String eventname);

    @DELETE("events/{id}")
    Call<EventBean> deleteEvent(@Path("id") String eventname);

    @PATCH("events/{id}")
    Call<EventBean> editEvent(@Path("id") String eventname, @Body EventBean event);

    @PUT("events/{id}")
    Call<EventBean> replaceEvent(@Path("id") String eventname, @Body EventBean event);
}
