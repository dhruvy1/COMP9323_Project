package com.comp9323.restclient.service;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.FoodPlaceApi;

import java.util.List;

import retrofit2.Callback;


public class FoodPlaceService {
    private final static String TAG = "FoodPlaceService";

    private static final FoodPlaceApi api = RestClient.getClient().create(FoodPlaceApi.class);

    /**
     * Get a single food place from the server
     *
     * @param id       the id of the food place
     * @param callback the callback where the response of the server will be returned
     */
    public static void getFoodPlace(int id, Callback<FoodPlace> callback) {
        api.getFoodPlace(id).enqueue(callback);
    }

    /**
     * Get all food places from the server
     *
     * @param callback the callback where the response of the server will be returned
     */
    public static void getFoodPlaces(Callback<List<FoodPlace>> callback) {
        api.getFoodPlaces().enqueue(callback);
    }

    /**
     * Post a new food place to the server
     *
     * @param foodPlace the new food place
     * @param callback  the callback where the response of the server will be returned
     */
    public static void postFoodPlace(FoodPlace foodPlace, Callback<FoodPlace> callback) {
        api.postFoodPlace(foodPlace).enqueue(callback);
    }

    /**
     * Delete a food place from the server
     *
     * @param id       the id of the food place
     * @param callback the callback where the response of the server will be returned
     */
    public static void deleteFoodPlace(int id, Callback<Void> callback) {
        api.deleteFoodPlace(id).enqueue(callback);
    }

    /**
     * Put a food place into the server
     *
     * @param id        the id of the food place
     * @param foodPlace the food place to be put
     * @param callback  the callback where the response of the server will be returned
     */
    public static void putFoodPlace(int id, FoodPlace foodPlace, Callback<FoodPlace> callback) {
        api.putFoodPlace(id, foodPlace).enqueue(callback);
    }

    /**
     * Patch an exisiting food place in the server
     *
     * @param id        the id of the food place
     * @param foodPlace the fields of the food place to patch
     * @param callback  the callback where the response of the server will be returned
     */
    public static void patchFoodPlace(int id, FoodPlace foodPlace, Callback<FoodPlace> callback) {
        api.patchFoodPlace(id, foodPlace).enqueue(callback);
    }
}
