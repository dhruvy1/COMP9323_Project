package com.comp9323.RestAPI.APIInterface;

import com.comp9323.RestAPI.Beans.DealListPackage;
import com.comp9323.RestAPI.Beans.FoodDeal;

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
import retrofit2.http.Query;

/**
 * Created by thomas on 16/9/2017.
 */

public interface FoodDealInterface {
    @POST("/api/food_deals/")
    Call<FoodDeal> createFoodDeal(@Body FoodDeal fd);
    @GET("/api/food_deals/all/")
    Call<DealListPackage> getFoodDeals(@Query("limit") int limit, @Query("offset") int pageNumber);
    @DELETE("/api/food_deals/{id}/")
    Call<Response<Void>> deleteFoodDeal(@Path("id") int id);
    @GET("/api/food_deals/{id}/")
    Call<FoodDeal> getFoodDeal(@Path("id")int id);
    @PATCH("/api/food_deals/{id}/")
    Call<FoodDeal> editFoodDealByFields(@Path("id") int id, @Body FoodDeal fd );
    @PUT("/api/food_deals/{id}/")
    Call<FoodDeal> editFoodDeal(@Path("id") int id, @Body FoodDeal fd);
}
