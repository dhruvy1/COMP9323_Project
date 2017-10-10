package com.comp9323.restclient.api;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.data.beans.FoodPlaceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by thomas on 2/10/2017.
 */

public interface FoodPlaceApi {
    @GET("/api/food_places/{id}/")
    Call<FoodPlace> getFoodPlace(@Path("id") int id);

    @GET("/api/food_places/all/")
    Call<List<FoodPlace>> getFoodPlaces();

    @POST("/api/food_places/")
    Call<FoodPlace> postFoodPlace(@Body FoodPlace foodPlace);

    @DELETE("/api/food_places/{id}/")
    Call<Response<Void>> deleteFoodPlace(@Path("id") int id);

    @PATCH("/api/food_places/{id}")
    Call<FoodPlace> patchFoodPlace(@Path("id") int id, @Body FoodPlace foodPlace);

    @PUT("/api/food_places/{id}")
    Call<FoodPlace> putFoodPlace(@Path("id") int id, @Body FoodPlace foodPlace);

}
