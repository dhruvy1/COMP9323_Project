package com.comp9323.RestAPI.APIInterface;

import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.Beans.EventResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by timta on 11/09/2017.
 */

public interface EventInterface {
    @GET("events/all/")
    Call<EventResponse> getEvents(@Query("page") int pageNumber);

    @POST("events")
    Call<EventBean> addEvent(@Body EventBean event);

    @GET("events/{id}")
    Call<EventBean> getEvent(@Path("id") int eventId);

    @DELETE("events/{id}")
    Call<EventBean> deleteEvent(@Path("id") int eventId);

    @PATCH("events/{id}")
    Call<EventBean> editEvent(@Path("id") int eventId, @Body EventBean event);

    @PUT("events/{id}")
    Call<EventBean> replaceEvent(@Path("id") int eventId, @Body EventBean event);
}
