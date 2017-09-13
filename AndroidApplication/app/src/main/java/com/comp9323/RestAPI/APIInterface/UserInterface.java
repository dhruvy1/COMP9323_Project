package com.comp9323.RestAPI.APIInterface;

import com.comp9323.RestAPI.Beans.User;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by thomas on 10/9/2017.
 */

public interface UserInterface {
    @GET("/api/mobile_users/")
    Call<Vector<User>> getUsers();

    @POST("/api/mobile_users/create/")
    Call<User> createUser(@Body User user);

    @GET("/api/mobile_users/{id}/")
    Call<User> getUser(@Path("id") int id);

    @DELETE("/api/mobile_users/{id}/delete/")
    Call<Response<Void>> deleteUser(@Path("id") int id);

    @PATCH("/api/mobile_users/{id}/edit/")
    Call<User> editUserWithFields(@Path("id") int id, @Body User user);

    @PUT("/api/mobile_users/{id}/edit/")
    Call<User> editWholeUser(@Path("id") int id, @Body User user);
}
