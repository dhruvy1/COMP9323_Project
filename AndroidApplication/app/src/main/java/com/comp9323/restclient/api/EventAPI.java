package com.comp9323.restclient.api;

import com.comp9323.data.beans.Event;

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
    @GET("/api/events/{id}")
    Call<Event> getEvent(@Path("id") String eventName);

    @GET("/api/events/all/")
    Call<List<Event>> getEvents();

    @POST("/api/events/")
    Call<Event> postEvent(@Body Event event);

    @DELETE("/api/events/{id}")
    Call<Event> deleteEvent(@Path("id") String eventName);

    @PATCH("/api/events/{id}")
    Call<Event> patchEvent(@Path("id") String eventName, @Body Event event);

    @PUT("/api/events/{id}")
    Call<Event> putEvent(@Path("id") String eventName, @Body Event event);
}
