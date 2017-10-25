package com.comp9323.restclient.service;

import com.comp9323.data.beans.FoodDeal;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.FoodDealApi;

import java.util.List;

import retrofit2.Callback;

public class FoodDealService {
    private static final String TAG = "FoodDealService";

    private static final FoodDealApi api = RestClient.getClient().create(FoodDealApi.class);

    /**
     * Get a single food deal from the server
     *
     * @param id       the id of the food deal
     * @param callback the callback where the response of the server will be returned
     */
    public static void getFoodDeal(int id, Callback<FoodDeal> callback) {
        api.getFoodDeal(id).enqueue(callback);
    }

    /**
     * Get all the food deals from the server
     *
     * @param callback the callback where the response of the server will be returned
     */
    public static void getFoodDeals(Callback<List<FoodDeal>> callback) {
        api.getFoodDeals().enqueue(callback);
    }

    /**
     * Post a new food deal to the server
     *
     * @param foodDeal the new food deal
     * @param callback the callback where the response of the server will be returned
     */
    public static void postFoodDeal(FoodDeal foodDeal, Callback<FoodDeal> callback) {
        api.postFoodDeal(foodDeal).enqueue(callback);
    }

    /**
     * Delete an existing food deal in the server
     *
     * @param id       the id of the food deal
     * @param callback the callback where the response of the server will be returned
     */
    public static void deleteFoodDeal(int id, Callback<Void> callback) {
        api.deleteFoodDeal(id).enqueue(callback);
    }

    /**
     * Put a food deal in the server
     *
     * @param id       the id of the food deal
     * @param foodDeal the food deal to be put
     * @param callback the callback where the response of the server will be returned
     */
    public static void putFoodDeal(int id, FoodDeal foodDeal, Callback<FoodDeal> callback) {
        api.putFoodDeal(id, foodDeal).enqueue(callback);
    }

    /**
     * Patch an existing food deal in the server
     *
     * @param id       the id of the food deal
     * @param foodDeal the fields of the food deal to be patched
     * @param callback the callback where the response of the server will be returned
     */
    public static void patchFoodDeal(int id, FoodDeal foodDeal, Callback<FoodDeal> callback) {
        api.patchFoodDeal(id, foodDeal).enqueue(callback);
    }
}
