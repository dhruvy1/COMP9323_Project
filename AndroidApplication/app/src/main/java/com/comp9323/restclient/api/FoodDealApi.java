package com.comp9323.restclient.api;

import com.comp9323.data.beans.FoodDealResponse;
import com.comp9323.data.beans.FoodDeal;

import io.reactivex.Observable;
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

public interface FoodDealApi {
    @GET("/api/food_deals/{id}/")
    Call<FoodDeal> getFoodDeal(@Path("id") int id);

    @GET("/api/food_deals/all/")
    Observable<FoodDealResponse> getFoodDeals(@Query("limit") int limit, @Query("offset") int offset);

    @POST("/api/food_deals/")
    Call<FoodDeal> postFoodDeal(@Body FoodDeal foodDeal);

    @DELETE("/api/food_deals/{id}/")
    Call<Response<Void>> deleteFoodDeal(@Path("id") int id);

    @PUT("/api/food_deals/{id}")
    Call<FoodDeal> putFoodDeal(@Path("id") int id, @Body FoodDeal foodDeal);

    @PATCH("/api/food_deals/{id}")
    Call<FoodDeal> patchFoodDeal(@Path("id") int id, @Body FoodDeal foodDeal);
}
