package com.comp9323.restclient.api;

import com.comp9323.data.beans.Event;

import java.util.List;

import java.util.List;

import io.reactivex.Observable;
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

public interface EventApi {
    @GET("events/{id}")
    Call<Event> getEvent(@Path("id") int eventId);

    @GET("events/all/")
    Call<List<Event>> getEvents();

    @POST("events/")
    Call<Event> postEvent(@Body Event event);

    @DELETE("events/{id}")
    Call<Void> deleteEvent(@Path("id") int eventId);

    @PUT("events/{id}")
    Call<Event> putEvent(@Path("id") int eventId, @Body Event event);

    @PATCH("events/{id}")
    Call<Event> patchEvent(@Path("id") int eventId, @Body Event event);
}
