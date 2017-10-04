package com.comp9323.RestAPI.APIInterface;

import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.Beans.PlaceListPackage;

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

public interface FoodPlaceInterface {
    @POST("/api/food_places/")
    Call<FoodPlace> createFoodPlace(@Body FoodPlace fp);

    @GET("/api/food_places/all/")

    Call<PlaceListPackage> getFoodPlaces(@Query("page") int pageNumber);
    @DELETE("/api/food_places/{id}/")
    Call<Response<Void>> deleteFoodPlace(@Path("id") int id);

    @GET("/api/food_places/{id}/")
    Call<FoodPlace> getFoodPlace(@Path("id") int id);

    @PATCH("/api/food_places/{id}/")
    Call<FoodPlace> editFoodPlaceByFields(@Path("id") int id, @Body FoodPlace fp );

    @PUT("/api/food_places/{id}/")
    Call<FoodPlace> editFoodPlace(@Path("id") int id, @Body FoodPlace fp );

}
